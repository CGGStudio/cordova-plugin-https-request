package es.tmg.cordovaplugin.httpsrequest.utils;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.tmg.cordovaplugin.httpsrequest.R;
import es.tmg.cordovaplugin.httpsrequest.model.ResponseVO;
import android.content.Context;
import android.text.TextUtils;

public class RequestUtils {
	
	/**
	 * Method that execute a POST request
	 * @param href URL of the web service
	 * @param params URL params to be concatenated
	 * @param headerJson Header params
	 * @param bodyjson Body params
	 * @param context App context
	 * @return The response of the request
	 */
	public static ResponseVO post(String href, String params, JSONObject headerJson, JSONObject bodyjson, Context context) {
		ResponseVO retorno = null;
		String url = getUrl(href, params, context);
		
		try {
			HttpClient client = getHttpsClient(new DefaultHttpClient(), context);
			HttpPost post = new HttpPost(url);
			setHeaderParams(post, headerJson);
			if (bodyjson != null) {
				StringEntity entity = new StringEntity(bodyjson.toString(), HTTP.UTF_8);
				entity.setContentType(PluginDefs.JSON_CONTENT_TYPE);
				post.setEntity(entity);
			}	
			HttpResponse response = client.execute(post);
			retorno = processResponse(response, PluginDefs.POST_STATUS_CODE1, PluginDefs.POST_STATUS_CODE2, context);
		} catch (Exception e) { 
			if (e instanceof CertificateException || e instanceof IllegalArgumentException || e instanceof SSLHandshakeException) {
				retorno = new ResponseVO(
						getErrorJSON(PluginDefs.CODE_HTTPS, context.getResources().getString(R.string.communication_error), e.getMessage()), 
						false, null);
			} else {
				retorno = new ResponseVO(
					getErrorJSON(PluginDefs.CODE_EXCEPTION, context.getResources().getString(R.string.unexpected_error), e.getMessage()), 
					false, null);
			}
		}
		return retorno;
	}
	
	/**
	 * Method that execute a GET request
	 * @param href URL of the web service
	 * @param params URL params to be concatenated
	 * @param headerJson Header params
	 * @param context App context
	 * @return The response of the request
	 */
	public static ResponseVO get(String href, String params, JSONObject headerJson, Context context) {
		ResponseVO retorno = null;
		String url = getUrl(href, params, context);
		
		try {
			HttpClient client = getHttpsClient(new DefaultHttpClient(), context);
			HttpGet get = new HttpGet(url);
			setHeaderParams(get, headerJson);
			HttpResponse response = client.execute(get);
			retorno = processResponse(response, PluginDefs.GET_STATUS_CODE, context);
		} catch (Exception e) {
			if (e instanceof CertificateException || e instanceof IllegalArgumentException || e instanceof SSLHandshakeException) {
				retorno = new ResponseVO(
						getErrorJSON(PluginDefs.CODE_HTTPS, context.getResources().getString(R.string.communication_error), e.getMessage()), 
						false, null);
			} else {
				retorno = new ResponseVO(
					getErrorJSON(PluginDefs.CODE_EXCEPTION, context.getResources().getString(R.string.unexpected_error), e.getMessage()), 
					false, null);
			}
		}
		
		return retorno;
	}
	
	/**
	 * Method that execute a PUT request
	 * @param href URL of the web service
	 * @param params URL params to be concatenated
	 * @param headerJson Header params
	 * @param bodyjson Body params
	 * @param context App context
	 * @return The response of the request
	 */
	public static ResponseVO put(String href, String params, JSONObject headerJson, JSONObject bodyjson, Context context) {
		ResponseVO retorno = null;
		String url = getUrl(href, params, context);
		
		try {
			HttpClient client = getHttpsClient(new DefaultHttpClient(), context);
			HttpPut put = new HttpPut(url);
			setHeaderParams(put, headerJson);
			if (bodyjson != null) {
				StringEntity entity = new StringEntity(bodyjson.toString(), HTTP.UTF_8);
				entity.setContentType(PluginDefs.JSON_CONTENT_TYPE);
				put.setEntity(entity);
			}	
			HttpResponse response = client.execute(put);
			retorno = processResponse(response, PluginDefs.PUT_STATUS_CODE, context);
		} catch (Exception e) { 
			if (e instanceof CertificateException || e instanceof IllegalArgumentException || e instanceof SSLHandshakeException) {
				retorno = new ResponseVO(
						getErrorJSON(PluginDefs.CODE_HTTPS, context.getResources().getString(R.string.communication_error), e.getMessage()), 
						false, null);
			} else {
				retorno = new ResponseVO(
					getErrorJSON(PluginDefs.CODE_EXCEPTION, context.getResources().getString(R.string.unexpected_error), e.getMessage()), 
					false, null);
			}
		}
		
		return retorno;
	}
	
	/**
	 * Method that execute a DELETE request
	 * @param href URL of the web service
	 * @param params URL params to be concatenated
	 * @param headerJson Header params
	 * @param context App context
	 * @return The response of the request
	 */
	public static ResponseVO delete(String href, String params, JSONObject headerJson, Context context) {
		ResponseVO retorno = null;
		String url = getUrl(href, params, context);
		
		try {
			HttpClient client = getHttpsClient(new DefaultHttpClient(), context);
			HttpDelete delete = new HttpDelete(url);
			setHeaderParams(delete, headerJson);
			HttpResponse response = client.execute(delete);
			retorno = processResponse(response, PluginDefs.DELETE_STATUS_CODE, context);
		} catch (Exception e) { 
			if (e instanceof CertificateException || e instanceof IllegalArgumentException || e instanceof SSLHandshakeException) {
				retorno = new ResponseVO(
						getErrorJSON(PluginDefs.CODE_HTTPS, context.getResources().getString(R.string.communication_error), e.getMessage()), 
						false, null);
			} else {
				retorno = new ResponseVO(
					getErrorJSON(PluginDefs.CODE_EXCEPTION, context.getResources().getString(R.string.unexpected_error), e.getMessage()), 
					false, null);
			}
		}
		
		return retorno;
	}
	
	/**
	 * Method that process the response of the request
	 * @param response Response of the request
	 * @param statusCode Valid status exit code for the request
	 * @param context App context
	 * @return Response object
	 */
	private static ResponseVO processResponse(HttpResponse response, int statusCode, Context context) {
		return processResponse(response, statusCode, -1, context);
	}
	
	/**
	 * Method that process the response of the request
	 * @param response Response of the request
	 * @param statusCode1 First of the two valid status exit code for the request
	 * @param statusCode2 Second od the two Valid status exit code for the request
	 * @param context App context
	 * @return Response object
	 */
	private static ResponseVO processResponse(HttpResponse response, int statusCode1, int statusCode2, Context context) {
		ResponseVO retorno = null;
		try {
			String location = response.getLastHeader("Location") != null ? response.getLastHeader("Location").getValue() : null;
			String strJson = null;
			try {
				strJson = EntityUtils.toString(response.getEntity(), "UTF-8");
			} catch(Exception e) { }
			if (TextUtils.isEmpty(strJson)) {
				strJson = PluginDefs.EMPTY_JSON;
			}

			if (response.getStatusLine().getStatusCode() == statusCode1 || response.getStatusLine().getStatusCode() == statusCode2) {
				try {
					JSONObject json = new JSONObject(strJson);
					retorno = new ResponseVO(
							json,
							response.getStatusLine().getStatusCode() == statusCode1 || response.getStatusLine().getStatusCode() == statusCode2,
							location);
				} catch (JSONException jsone) {
					JSONArray array = new JSONArray(strJson);
					retorno = new ResponseVO(
							array,
							response.getStatusLine().getStatusCode() == statusCode1 || response.getStatusLine().getStatusCode() == statusCode2,
							location);
				}
			} else {
				if (response.getStatusLine().getStatusCode() == 500) {
					JSONObject json = new JSONObject(strJson);
					retorno = new ResponseVO(
							json,
							response.getStatusLine().getStatusCode() == statusCode1 || response.getStatusLine().getStatusCode() == statusCode2,
							location);
				} else {
					retorno = new ResponseVO(
							getErrorJSON(Integer.toString(response.getStatusLine().getStatusCode()), context.getResources().getString(R.string.error_inesperado), response.getStatusLine().getReasonPhrase()), 
							false, null);
				}
			}
		} catch(Exception e) {
			retorno = new ResponseVO(
					getErrorJSON(PluginDefs.CODE_EXCEPTION, context.getResources().getString(R.string.error_inesperado), e.getMessage()), 
					false, null);
		}
		return retorno;
	}
	

	/**
	 * Method that establish header params
	 * @param request Request object
	 * @param headerJson Params to be included in the header
	 */
	private static void setHeaderParams(HttpRequestBase request, JSONObject headerJson) {
		if (headerJson != null) {
			String key = null;
			Iterator<String> it = headerJson.keys();
			while(it.hasNext()) {
				key = it.next();
				try {
					request.setHeader(key, headerJson.getString(key));
				} catch(JSONException e) { }
			}
		}
	}
	
	/**
	 * Method that converts a string into a JSON object
	 * @param value Value to be converted
	 * @return JSON object with the value
	 */
	public static JSONObject asJSON(String value) {
		JSONObject json = null;
		if (!TextUtils.isEmpty(value)) {
			try {
				json = new JSONObject(value);
			} catch(Exception e) { }
		}
		return json;
	}
	
	/**
	 * Method that concatenates the URL with te URL params
	 * @param href URL of the REST web service
	 * @param params URL params
	 * @param context App context
	 * @return Complete URL
	 */
	private static String getUrl(String href, String params, Context context) {
		return href + (!TextUtils.isEmpty(params) ? ("?" + params) : PluginDefs.EMPTY_STRING);
	}
	
	/**
	 * Method that generates an error response
	 * @param code Code of the error
	 * @param message Message of the error
	 * @param description Long description of the error 
	 * @return A response error
	 */
	public static JSONObject getErrorJSON(String code, String message, String description) {
		JSONObject json = null;
		try {
			json = new JSONObject();
			json.put(PluginDefs.JSON_ERROR_CODE, code);
			json.put(PluginDefs.JSON_ERROR_MESSAGE, message);
			json.put(PluginDefs.JSON_ERROR_DETAILS, description);
		} catch(JSONException e) { }
		return json;
	}

	/**
	 * Method that gets a HTTPS client to make the request. If you need to validate
	 * the server certificate to prevent attacks (like Man-In-The-Middle), please 
	 * uncomment the two blocks and adapt to your case.
	 * @param client HttpClient object
	 * @param context App context
	 * @return A HTTPS client
	 */
	public static HttpClient getHttpsClient(HttpClient client, final Context context) {
	     try{
	    	//UNCOMMENT1 If you need to validate the server certificate to avoid, for example, Man-In-The-Middle attack
	    	 /*CertificateFactory cf = CertificateFactory.getInstance("X.509");   
	    	 InputStream clientTruststoreIs = context.getResources().openRawResource(R.raw.server_ca);
	    	 InputStream clientTruststoreIs1 = context.getResources().openRawResource(R.raw.server_subca);
	    	 final X509Certificate caCertificate = (X509Certificate)cf.generateCertificate(clientTruststoreIs);
	    	 final X509Certificate caCertificate1 = (X509Certificate)cf.generateCertificate(clientTruststoreIs1);*/  

	    	 X509TrustManager x509TrustManager = new X509TrustManager() { 	           
				@Override
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}
 
				@Override
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					
					//UNCOMMENT2 If you need to validate the server certificate to avoid, for example, Man-In-The-Middle attack
					 /*if (chain == null || chain.length == 0) {  
						 throw new IllegalArgumentException(context.getResources().getString(R.string.cannot_validate_error));  
					 }  

					 if (TextUtils.isEmpty(authType)) {  
						 throw new IllegalArgumentException(context.getResources().getString(R.string.cannot_validate_error));  
					 }  

					 for (int i=0; i<chain.length; i++) {
						 if(!chain[i].equals(caCertificate) && !chain[i].equals(caCertificate1)){
							 try {
								 chain[i].verify(caCertificate.getPublicKey());
							 } catch(Exception e) {   
								 throw new CertificateException(context.getResources().getString(R.string.trusted_error),e);
							 }
							 
							 try {
								 chain[i].checkValidity();
							 } catch(Exception e){
								 throw new CertificateException(context.getResources().getString(R.string.not_valid_error),e);
							 }
						 }
					 }*/
				}
 
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
	        };
		        
	        SSLContext sslContext = SSLContext.getInstance("TLS");
	        sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
	        SSLSocketFactory sslSocketFactory = new ExSSLSocketFactory(sslContext);
	        sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	        ClientConnectionManager clientConnectionManager = client.getConnectionManager();
	        SchemeRegistry schemeRegistry = clientConnectionManager.getSchemeRegistry();
	        schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
	        return new DefaultHttpClient(clientConnectionManager, client.getParams());
	    } catch (Exception ex) {
	        return null;
	    }
	}
}
