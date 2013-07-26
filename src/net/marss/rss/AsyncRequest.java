package net.marss.rss;

import net.marss.AndroidTools;
import android.os.AsyncTask;

public class AsyncRequest
{
	private AsyncRequestMethods instance;
	
	public AsyncRequest (AsyncRequestMethods instance) 
	{
		this.instance = instance;
	}
	
	public void execute ()
	{
		AR ar = new AR();
		ar.execute("");
	}
	
	
	class AR extends AsyncTask<String, Void, String>{
	
		@Override
		protected String doInBackground(String... arg0)
		{
			instance.AsyncRequestExecute();
			return null;
		}
		
		protected void onPostExecute(String result)
		{
			instance.onPostExecute();
		}
	}
	
	
}
