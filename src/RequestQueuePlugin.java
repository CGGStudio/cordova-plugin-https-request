package es.tmg.cordovaplugin.httpsrequest;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import es.tmg.cordovaplugin.httpsrequest.utils.PluginDefs;

/**
 * HTTPS requests Cordova Plugin
 * @author Jose Antonio Nogales Rincón
 *
 */
public class RequestQueuePlugin extends CordovaPlugin {
	//static progress dialog
	private static ProgressDialog progress;
	
	@Override
	public boolean execute(final String action, final JSONArray args,
			final CallbackContext callbackContext) throws JSONException {

		/**
		 * Function arguments at index:
		 * - 0: REST service URL
		 * - 1: params of the URL. It will be concatenated as ?param1=value1&param2=value2...
		 * - 2: Type of the request. Values: POST, GET, PUT, DELETE
		 * - 3: Header params in JSON format. {"param1": "value1", "param2", "value2"}
		 * - 4: Body params in JSON format. {"param1": "value1", "param2": "value2"}
		 * - 5 & 6: if show and hide are true, a progress dialog opens at the beginning and closes at the end of request.
		 * 			if show and hide are false, anything happens
		 * 			if show and not hide, a progress dialog opens at the beginning but not close at the end (useful if you need to make several requests and this is not the last request)
		 *    		if not show and hide, a progress dialog closes at the end but not opens at the beginning (useful if you need to make several requests and this is the last request)
		 */
		final String href = args.getString(0); 
		final String params = args.getString(1);
		final String type = args.getString(2);
		final JSONObject headerJson = getJSONObject(args, 3);
		final JSONObject bodyJson = getJSONObject(args, 4);
		final boolean showLoading = args.getBoolean(5);
		final boolean hideLoading = args.getBoolean(6);
		
		if (showLoading) {
			progress = ProgressDialog.show(cordova.getActivity(), 
				PluginDefs.EMPTY_STRING, cordova.getActivity().getResources().getString(R.string.loading), true, false);
		}

		//Makes the operation in a background thread to not block the main thread
		cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				new  RequestQueueTask(href, type, params, headerJson, bodyJson, callbackContext, cordova.getActivity(), progress, hideLoading).execute();		
			};
		});
		
		return true;
	}

	/**
	 * Method that recovers a value in a specific position
	 * @param args JSON Array with values
	 * @param index Index of the value to be recovered
	 * @return JSONObject with the recovered value
	 */
	private JSONObject getJSONObject(JSONArray args, int index) {
		JSONObject json = null;
		try {
			if (args.length() > index) {
				json = args.getJSONObject(index);
			}
		} catch(Exception e) { }
		
		return json;
	}

}