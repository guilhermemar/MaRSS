package net.marss;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class AndroidTools {
	public static void toast(Context context, CharSequence text)
	{
		AndroidTools.toast(context, text, Toast.LENGTH_SHORT);
	}
	
	public static void toast(Context context, CharSequence text, int duration)
	{
		Toast tche = Toast.makeText(context, text, duration);
		tche.show();
	}
	
	public static Boolean testConnection(Context ctx)
	{
		try {
			ConnectivityManager cm = (ConnectivityManager)
			ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
				return true;
			} else if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
				return true;
			} else  {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}


}
