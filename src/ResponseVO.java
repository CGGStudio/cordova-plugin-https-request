package es.tmg.cordovaplugin.httpsrequest.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseVO {

	//Response in a JSONObject format
	private JSONObject json;
	
	//Response in a JSONArray format
	private JSONArray array;
	
	//Indicates if the process was OK
	private boolean ok;
	
	//Location of the resource created in the service (if exists)
	private String location;
	
	/**
	 * Default constructor
	 */
	public ResponseVO() {
		super();
		this.json = null;
		this.array = null;
		this.ok = false;
		this.location = null;
	}

	/**
	 * Constructor with params
	 * @param json JSON object with response data
	 * @param ok Indicates if the process was OK
	 * @param location Location of the resource created in the service (if exists)
	 */
	public ResponseVO(JSONObject json, boolean ok, String location) {
		super();
		this.json = json;
		this.ok = ok;
		this.location = location;
	}
	
	/**
	 * Constructor with params
	 * @param json JSON array with response data
	 * @param ok Indicates if the process was OK
	 * @param location Location of the resource created in the service (if exists)
	 */
	public ResponseVO(JSONArray array, boolean ok, String location) {
		super();
		this.array = array;
		this.ok = ok;
		this.location = location;
	}

	/**
	 * JSON object getter
	 * @return JSON object
	 */
	public JSONObject getJson() {
		return json;
	}

	/**
	 * JSON object setter
	 * @param json JSON object
	 */
	public void setJson(JSONObject json) {
		this.json = json;
	}

	/**
	 * OK getter
	 * @return true if the process was OK, false if not
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * OK setter
	 * @param true if the process was OK, false if not
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * Location getter
	 * @return Location of the resource
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Location setter
	 * @return Location of the resource
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * JSON array getter
	 * @return JSON array
	 */
	public JSONArray getArray() {
		return array;
	}

	/**
	 * JSON array setter
	 * @param array JSON array
	 */
	public void setArray(JSONArray array) {
		this.array = array;
	}
	
}
