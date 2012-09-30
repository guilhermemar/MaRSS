package net.marss.rss;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

public class RSS {
	
	public static boolean loadSource(FeedSource source)
	{
		Log.d(">", "Carregando feed");
		
		try {
			Element properties = RSS.getSource(source.getFeed_link());
			Element properties_image = (Element) properties.getElementsByTagName("image").item(0);
			
			source.setTitle(properties.getElementsByTagName("title").item(0).getTextContent());
			source.setLink(properties.getElementsByTagName("link").item(0).getTextContent());
			source.setDescription(properties.getElementsByTagName("description").item(0).getTextContent());
			source.setImage_url(properties_image.getElementsByTagName("link").item(0).getTextContent());
			
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<FeedItem> loadItens(FeedSource source) 
	{
		Log.d(">", "loadItens Carregando link " + source.getFeed_link());
		
		ArrayList<FeedItem> itens = new ArrayList<FeedItem>();
		NodeList rssItens;
		
		/*
		 * Buscando itens do feed
		 */
		rssItens = RSS.getRSSItens(source.getFeed_link());
		
		if (rssItens != null) {
			for (int i = 1; i < rssItens.getLength(); i++) {
				
				Element e = (Element) rssItens.item(i);
				/*
				Log.d(">", "################################");
				Log.d(">", "Item " + i);
				Log.d(">", " TITLE ++> " + e.getElementsByTagName("title").item(0).getTextContent());
				Log.d(">", " LINK ++> " + e.getElementsByTagName("link").item(0).getTextContent());
				Log.d(">", " DESCRIPTION ++> " + e.getElementsByTagName("description").item(0).getTextContent());
				Log.d(">", " PUB_DATA ++> " + e.getElementsByTagName("pubDate").item(0).getTextContent());
				Log.d(">", "################################");
				*/
				itens.add(new FeedItem(
					source.getId_feed_source(),
					e.getElementsByTagName("title").item(0).getTextContent(),
					e.getElementsByTagName("link").item(0).getTextContent(),
					e.getElementsByTagName("description").item(0).getTextContent(),
					e.getElementsByTagName("pubDate").item(0).getTextContent(),
					0
				));
				
			}
		}
		
		return itens;
	}
	
	private static Element getSource(String link)
	{
		Log.d(">", "getSource Carregando link " + link);
		try {
			Document doc = RSS.getDocument(link);
			
			return (Element) doc.getElementsByTagName("channel").item(0);
		}
		catch(Exception ex) {
			Log.d(">", ex.getMessage());
		}
		
		return null;
	}
	
	private static NodeList getRSSItens(String link)
	{
		try {
			Document doc = RSS.getDocument(link);
			
			return doc.getElementsByTagName("item");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
		
	}
	
	private static Document getDocument (String link) throws SAXException, IOException, ParserConfigurationException
	{
		Log.d(">", "getDocument Carregando link " + link);
		URL u = new URL(link);
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		return builder.parse(u.openStream());
	}
	
}
