package es.tmg.cordovaplugin.httpsrequest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionUtils {

	/**
	 * Method that validate if the device is online or offline
	 * @param context App context
	 * @return true if is online, false if is offline
	 */
	public static boolean isConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
	    return !(netInfo == null || !netInfo.isConnectedOrConnecting() || !netInfo.isAvailable());
	}
	
}
