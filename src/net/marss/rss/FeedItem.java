package net.marss.rss;

import net.marss.database.Manager;
import net.marss.database.ManagerException;

public class FeedItem {
	private Long    id_feed_item;
	private Long    id_feed_source;
	private String  title;
	private String  link;
	private String  description;
	private String  pub_date;
	private Integer control_read = 0;
	
	public FeedItem(Long id_feed_source, String title, String link, String description, String pub_date, Integer control_read)
	{
		this.id_feed_source = id_feed_source;
		this.title = title;
		this.link = link;
		this.description = description;
		this.pub_date = pub_date;
		this.control_read = control_read;
	}
	
	public FeedItem(Long id_feed_item, Long id_feed_source, String title, String link, String description, String pub_date, Integer control_read)
	{
		this.id_feed_item = id_feed_item;
		this.id_feed_source = id_feed_source;
		this.title = title;
		this.link = link;
		this.description = description;
		this.pub_date = pub_date;
		this.control_read = control_read;
	}
	
	public Long getId_feed_item() {
		return id_feed_item;
	}
	public void setId_feed_item(Long id_feed_item) {
		this.id_feed_item = id_feed_item;
	}
	public Long getId_feed_source() {
		return id_feed_source;
	}
	public void setId_feed_source(Long id_feed_source) {
		this.id_feed_source = id_feed_source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPub_date() {
		return pub_date;
	}
	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}
	public Integer getControl_read() {
		return control_read;
	}
	public void setControl_read(Integer control_read) {
		this.control_read = control_read;
	}
	
	public boolean save(Manager m)
	{
		try {
			if (this.id_feed_item == null) {
				m.insert(this);
			} else {
				m.update(this);
			}
		}
		catch(ManagerException ex) {
			//TODO fazer mensagem de não foi possivel inserir
		}
		
		return false;
	}
	
}
