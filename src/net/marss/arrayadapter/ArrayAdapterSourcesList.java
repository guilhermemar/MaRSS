package net.marss.arrayadapter;

import net.marss.R;
import net.marss.rss.FeedSource;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterSourcesList extends ArrayAdapter<FeedSource> {
	
	private LayoutInflater inflater;

	public ArrayAdapterSourcesList(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = this.inflater.inflate(R.layout.arrayadapter_sources_list, null);
		
		TextView sourceName = (TextView) v.findViewById(R.id.textSourceName);
		
		FeedSource source = this.getItem(position);
		
		sourceName.setText(source.getTitle());

		return v;	
	}
	

}
