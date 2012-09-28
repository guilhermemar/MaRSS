package net.marss.rss;

import java.util.ArrayList;

import net.marss.database.Manager;
import net.marss.database.ManagerException;
import android.R.bool;
import android.content.Context;
import android.test.IsolatedContext;
import android.util.Log;

public class FeedSource {
	private Long   id_feed_source;
	private String feed_link;
	private String title;
	private String link;
	private String description;
	private String image_url;
	
	private ArrayList<FeedItem> itens = null;
	
	public FeedSource () 
	{}
	
	
	
	public FeedSource(Long id_feed_source, String feed_link, String title, String link, String description, String image_url) {
		this.id_feed_source = id_feed_source;
		this.feed_link = feed_link;
		this.title = title;
		this.link = link;
		this.description = description;
		this.image_url = image_url;
	}



	public FeedSource (String feed_link)
	{
		this.feed_link = feed_link;
		
		this.loadSource();
	}
	
	public Long getId_feed_source()
	{
		return id_feed_source;
	}
	
	public void setId_feed_source(Long id_feed_source)
	{
		this.id_feed_source = id_feed_source;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public void setLink(String link)
	{
		this.link = link;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getImage_url()
	{
		return image_url;
	}
	
	public void setImage_url(String image_url)
	{
		this.image_url = image_url;
	}
	
	public String getFeed_link() {
		return feed_link;
	}

	public void setFeed_link(String feed_link) {
		this.feed_link = feed_link;
	}

	public ArrayList<FeedItem> getItens() {
		return itens;
	}
	
	public boolean save(Manager m)
	{
		try{
			if (this.id_feed_source == null) {
				m.insert(this);
			} else {
				m.update(this);
			}
		}
		catch(ManagerException ex) {
			//TODO inserir mensagem de erro ao cadastrar
		}
		
		return false;
	}
	
	public boolean loadSource()
	{
		return RSS.loadSource(this);
	}
	
	public boolean loadItens(Manager m)
	{
		ArrayList<FeedItem> itens = new ArrayList<FeedItem>();
		
		itens = RSS.loadItens(this);
		
		for (int i= 0; i < itens.size(); ++i) {
			try{
				//itens.get(i).save(m);
				Log.d(">", "################################");
				Log.d(">", itens.get(i).getTitle());
				Log.d(">", itens.get(i).getLink());
				Log.d(">", "################################");
			}
			catch (Exception ex){}
		}
		
		return true;
	}
	
	
}
