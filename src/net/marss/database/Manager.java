package net.marss.database;

import java.util.ArrayList;

import net.marss.rss.FeedItem;
import net.marss.rss.FeedSource;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Manager extends SQLiteOpenHelper {
	
	public Manager(Context context) {
		super(context, "MaRSS", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		 * Criando tabela feed_source
		 */
		db.execSQL("" +
			"CREATE TABLE FEED_SOURCES" +
			"(" +
			"	ID_FEED_SOURCE INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT," +
			"	FEED_LINK TEXT NOT NULL UNIQUE," +
			"	TITLE TEXT NOT NULL," +
			"	LINK TEXT NOT NULL," +
			"	DESCRIPTION TEXT," +
			"	IMAGE_URL TEXT" +
			");"
		);
		/*
		 * Criando tabela feed_itens
		 */
		db.execSQL("" +
			"CREATE TABLE FEED_ITENS" +
			"(" +
			"	ID_FEED_ITEM INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT," +
			"	ID_FEED_SOURCE INTEGER NOT NULL," +
			"	TITLE TEXT NOT NULL," +
			"	LINK TEXT UNIQUE," +
			"	DESCRIPTION TEXT," +
			"	PUB_DATE TEXT," +
			"	CONTROL_READ INTEGER DEFAULT 0 NOT NULL" +
			");"
		);
		
		Log.d(">", "Banco de dados criado");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub		
	}
		
	public Long insert(FeedSource source) throws ManagerException
	{
		Long retorno = -1l;
		
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("title", source.getTitle());
		values.put("link", source.getLink());
		values.put("description", source.getDescription());
		values.put("image_url", source.getImage_url());
		
		try{
			retorno = db.insertOrThrow("FEED_SOURCES", null, values);
		}
		catch(SQLiteConstraintException ex){
			throw new ManagerException(
				ManagerException.SOURCE_ALREADY_REGISTERED,
				ManagerException.SOURCE_ALREADY_REGISTERED_MESSAGE	
			);
		}
		
		if (retorno > 0) {
			source.setId_feed_source(retorno);
			return retorno;
		} 
		
		throw new ManagerException(
			ManagerException.ERROR_UNDEFINED,
			ManagerException.ERROR_UNDEFINED_MESSAGE	
		);
	}
	
	public boolean update(FeedSource source)
	{
		Integer retorno = -1;
		String  where;
		
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("title", source.getTitle());
		values.put("link", source.getLink());
		values.put("description", source.getDescription());
		values.put("image_url", source.getImage_url());
		
		where = "id_feed_source = " + source.getId_feed_source();
		
		retorno = db.update("FEED_SOURCES", values, where, null);
		
		return true;
	}
	
	public boolean insert(FeedItem item) throws ManagerException
	{
		Long retorno = -1l;
		
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("id_feed_source", item.getId_feed_source());
		values.put("title", item.getTitle());
		values.put("link", item.getLink());
		values.put("description", item.getDescription());
		values.put("pub_date", item.getPub_date());
		values.put("control_read", item.getControl_read());
		
		try{
			retorno = db.insertOrThrow("FEED_ITENS", null, values);
		}
		catch(SQLiteConstraintException ex){
			throw new ManagerException(
				ManagerException.SOURCE_ALREADY_REGISTERED,
				ManagerException.SOURCE_ALREADY_REGISTERED_MESSAGE	
			);
		}
		
		if (retorno > 0) {
			item.setId_feed_item(retorno);
			return true;
		} 
		
		throw new ManagerException(
			ManagerException.ERROR_UNDEFINED,
			ManagerException.ERROR_UNDEFINED_MESSAGE	
		);
	}
	
	public boolean update(FeedItem item)
	{
		Integer retorno;
		String  where;
		
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("id_feed_source", item.getId_feed_source());
		values.put("title", item.getTitle());
		values.put("link", item.getLink());
		values.put("description", item.getDescription());
		values.put("pub_date", item.getPub_date());
		values.put("control_read", item.getControl_read());
		
		where = "id_feed_item = " + item.getId_feed_item();
		
		retorno = db.update("FEED_ITENS", values, where, null);
		
		return true;
	}
	
	public ArrayList<FeedSource> getSources()
	{
		Log.d(">", "óóó, chegamos aqui");
		
		ArrayList<FeedSource> sources = new ArrayList<FeedSource>();
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("" +
			"SELECT" +
			"	id_feed_source," +
			"	feed_link," +
			"	title," +
			"	link," +
			"	description," +
			"	image_url  " +
			"FROM" +
			"	feed_sources",
			null
		);
		
		/*
		 * Percorrendo itens
		 */
		while (c.moveToNext()) {
			sources.add(new FeedSource(
				c.getLong(0),
				c.getString(1),
				c.getString(2),
				c.getString(3),
				c.getString(4),
				c.getString(5)
			));
		}

		Log.d(">", "óóó, chegamos aqui também");
		return sources;
	}
}
