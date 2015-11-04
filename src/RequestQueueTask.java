package es.tmg.cordovaplugin.httpsrequest;

import org.apache.cordova.CallbackContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import es.tmg.cordovaplugin.httpsrequest.model.ResponseVO;
import es.tmg.cordovaplugin.httpsrequest.utils.ConnectionUtils;
import es.tmg.cordovaplugin.httpsrequest.utils.PluginDefs;
import es.tmg.cordovaplugin.httpsrequest.utils.RequestUtils;

public class RequestQueueTask extends AsyncTask<Void, Void, ResponseVO>{
	//URL of the web service
	private String href;
	
	//URL params to be concatenated
	private String paramsUrl;
	
	//Header params
	private JSONObject headerJson;
	
	//Body params
	private JSONObject bodyJson;
	
	//App context
	private Context context;
	
	//Type of request: POST, GET, PUT or DELETE
	private String type;

	//Javascript callback function
	private CallbackContext callbackContext;
	
	//Loader
	private ProgressDialog progress;
	
	//Close loader
	private boolean hideLoading;
	
	
	/**
	 * Default constructor
	 * @param href URL of the web service
	 * @param type Type of request: POST, GET, PUT or DELETE
	 * @param paramsUrl URL params to be concatenated
	 * @param headerJson Header params
	 * @param bodyJson Body params
	 * @param callbackContext Javascript callback function
	 * @param context App context
	 * @param progress Loader
	 * @param hideLoading Close loader
	 */
	public RequestQueueTask(String href, String type, String paramsUrl, JSONObject headerJson, JSONObject bodyJson, CallbackContext callbackContext, Context context, ProgressDialog progress, boolean hideLoading) {
		super();
		
		this.href = href;
		this.type = type;
		this.paramsUrl = paramsUrl;
		this.headerJson = headerJson;
		this.bodyJson = bodyJson;
		this.context = context;
		this.callbackContext = callbackContext;
		this.progress = progress;
		this.hideLoading = hideLoading;
	}

	@Override
	protected void onPreExecute() {		
		super.onPreExecute();
	}
	
	@Override
	protected ResponseVO doInBackground(Void... params) {
		ResponseVO response = null;
		boolean isConnected = ConnectionUtils.isConnected(context);
		
		//Set REST service API version. if it is not versioned, set PluginDefs.REST_SERVICE_VERSION = -1
		setVersionAPI();

		//If the device is online
		if (isConnected) {
			//POST request
			if (PluginDefs.TYPE_REQUEST_POST.equals(type)) {
				response = RequestUtils.post(href, paramsUrl, headerJson, bodyJson, context);
			} 
			//GET request
			else if (PluginDefs.TYPE_REQUEST_GET.equals(type)) {
				response = RequestUtils.get(href, paramsUrl, headerJson, context);
			} 
			//PUT request
			else if (PluginDefs.TYPE_REQUEST_PUT.equals(type)) {
				response = RequestUtils.put(href, paramsUrl, headerJson, bodyJson, context);
			} 
			//DELETE request
			else if (PluginDefs.TYPE_REQUEST_DELETE.equals(type)) {
				response = RequestUtils.delete(href, paramsUrl, headerJson, context);
			}
		} else {
			//Response with an error. No conection error code = 999
			JSONObject json = RequestUtils.getErrorJSON(PluginDefs.CODE_NO_CONNECTION,
						getText(R.string.message_mandatory),
						getText(R.string.description_mandatory)								
					);
			response = new ResponseVO(json, false, null);
		}
		
		return response;
	}
	
	
	protected void onPostExecute(ResponseVO result) {
		if (hideLoading) {
			if (progress != null) {
				progress.cancel();
			}
		}
		
		//Return the response to Javascript callback function
		if (callbackContext != null) {
			if (result != null) {
				if (result.isOk()) {
					callbackContext.success(getJsonResponse(result));
				} else {
					if (progress != null) {
						progress.cancel();
					}
					callbackContext.error(getJsonResponse(result));
				}
			} else {
				if (progress != null) {
					progress.cancel();
				}
				callbackContext.error(getJsonResponse(new ResponseVO()));
			}
		}
	}

	/**
	 * Recovers a text from string.xml
	 * @param resource Id os the resource
	 * @return Text
	 */
	private String getText(int resource) {
		return context.getResources().getString(resource);
	}
	
	/**
	 * Method that converts a ResponseVO object to JSONObject
	 * @param response Response of the REST service
	 * @return JSON with response data
	 */
	private JSONObject getJsonResponse(ResponseVO response) {
		JSONObject json = new JSONObject();
		try {
			json.put("ok", response.isOk());
			if (response.getJson() != null) {
				json.put("response", response.getJson());
			} else {
				json.put("response", response.getArray());
			}
		} catch(JSONException e) { }
		return json;
	}
	
	/**
	 * Method that establish the REST service API version 
	 */
	private void setVersionAPI() {
		try {
			if (headerJson == null) {
				headerJson = new JSONObject();
			}
			if (PluginDefs.REST_SERVICE_VERSION > -1) {
				headerJson.put(PluginDefs.VERSION_HEADER_PARAM, PluginDefs.REST_SERVICE_VERSION);
			}
		} catch(JSONException e) { }
	}
}
