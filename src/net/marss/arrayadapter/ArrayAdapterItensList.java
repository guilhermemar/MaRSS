package net.marss.arrayadapter;

import net.marss.R;
import net.marss.rss.FeedItem;
import net.marss.rss.FeedSource;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterItensList extends ArrayAdapter<FeedItem> {
	
	private LayoutInflater inflater;

	public ArrayAdapterItensList(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = this.inflater.inflate(R.layout.arrayadapter_itens_list, null);
		
		TextView sourceName = (TextView) v.findViewById(R.id.textSourceName);
			
		FeedItem item = this.getItem(position);
		
		sourceName.setText(item.getTitle());
		if (item.getControl_read() != 0) {
			sourceName.setTextColor(Color.parseColor("#BEBEBE"));
		}

		return v;	
	}
	

}
