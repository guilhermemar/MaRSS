package net.marss.rss;

import java.util.ArrayList;

import net.marss.database.Manager;
import net.marss.database.ManagerException;
import android.util.Log;

public class FeedSource {
	private Long   id_feed_source;
	private String feed_link;
	private String title;
	private String link;
	private String description;
	private String image_url;
		
	private ArrayList<FeedItem> itens = new ArrayList<FeedItem>();
	private Integer totalUnread = 0;
	
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
	
	public void clearList()
	{
		this.itens.clear();
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

	public ArrayList<FeedItem> getItens(Manager m) {
		
		if (this.itens.size() == 0) {
			Log.d(">","itens está nulo, buscando itens");
			this.getItensDatabase(m);
			//this.loadItens(m);
		}
		return itens;
	}
	
	private void getItensDatabase(Manager m)
	{
		Log.d(">", "chamando metodo de busca no banco");
		ArrayList<FeedItem> itens = new ArrayList<FeedItem>();
		
		try {
			itens = m.getItens(this);
			
			for (int i=0; i<itens.size(); ++i) {
				this.itens.add(itens.get(i));
			}
			
		}
		catch (Exception ex) {
			Log.d(">", "Problemas ao buscar itens");
		}
		
		this.totalUnread = m.getTotalUnead(this);
		
	}
	
	public Integer setTotalUnread (Integer total)
	{
		return this.totalUnread = total;
	}
	
	public Integer getTotalUnread ()
	{
		return this.totalUnread;
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
			Log.d(">", "ocorreu uma exeção : " + ex.getMessage());
		}
		
		return false;
	}
	
	public boolean loadSource()
	{
		return RSS.loadSource(this);
	}
	
	public boolean loadSource(String feed_link)
	{
		this.feed_link = feed_link;
		
		return this.loadSource();
	}
	
	public boolean loadItens(Manager m)
	{
		Log.d(">","FeedSource.loadItens - atualizando");
		
		ArrayList<FeedItem> itens;
		
		itens = RSS.loadItens(this);
		/*
		 * salvando
		 */
		for (int i=0; i<itens.size(); ++i) {
			try {
				//Log.d(">", "salvando item : " + itens.get(i).getTitle());
				itens.get(i).save(m);
			}
			catch(Exception e)
			{}
		}
		
		this.itens.clear();
		
		return true;
	}
	
	public boolean delete(Manager m)
	{
		Boolean retorno = m.delete(this);
		
		if (retorno) {
			this.itens.clear();
		}
		
		return retorno; 
	}
	
	public static ArrayList<FeedItem> getAllItens (Manager m)
	{
		return m.getAllItens();
	}
	
	
}
