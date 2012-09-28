package net.marss;

import android.content.Context;
import android.widget.Toast;

public class AndroidTools {
	public static void toast(Context context, CharSequence text)
	{
		AndroidTools.toast(context, text, Toast.LENGTH_LONG);
	}
	
	public static void toast(Context context, CharSequence text, int duration)
	{
		Toast tche = Toast.makeText(context, text, duration);
		tche.show();
	}
}
