package es.tmg.cordovaplugin.httpsrequest.utils;

public class PluginDefs {
	//Types of requests: POST, GET, PUT, DELETE
	public static final String TYPE_REQUEST_POST = "POST";
	public static final String TYPE_REQUEST_GET = "GET";
	public static final String TYPE_REQUEST_PUT = "PUT";
	public static final String TYPE_REQUEST_DELETE = "DELETE";
	
	//REST service API version param and value
	public static final String VERSION_HEADER_PARAM = "Version";
	public static final int REST_SERVICE_VERSION = 1;
	
	//No connection error code
	public static final String CODE_NO_CONNECTION = "999";
	
	//Communication error code
	public static final String CODE_HTTPS = "998";
	
	//Unexpected error code
	public static final String CODE_EXCEPTION = "997";
	
	//Valid status codes for the requests
	public static final int POST_STATUS_CODE1 = 200;
	public static final int POST_STATUS_CODE2 = 201;
	public static final int GET_STATUS_CODE = 200;
	public static final int DELETE_STATUS_CODE = 204;
	public static final int PUT_STATUS_CODE = 202;
	
	//JSON error attributes
	public static final String JSON_ERROR_CODE = "code";
	public static final String JSON_ERROR_MESSAGE = "message";
	public static final String JSON_ERROR_DETAILS = "details";
	
	//Request content type
	public static final String JSON_CONTENT_TYPE = "application/json";
	
	//Empty JSON content
	public static final String EMPTY_JSON = "{}";
	
	//Empty string content
	public static final String EMPTY_STRING = "";
	
}
