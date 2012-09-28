package net.marss;

import java.util.ArrayList;

import net.marss.database.Manager;
import net.marss.rss.FeedSource;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MaRSS extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marss_main);
        
        /*
         * iniciando bando de dados
         */
        Manager m = new Manager(getApplicationContext());
        /*
         * Iniciando lista para receber os feeds
         */
        ArrayList<FeedSource> sources;
        /*
         * Buscando feeds cadastrados
         */
        sources = m.getSources();
        
        if (sources.size() == 0) {
        	AndroidTools.toast(getApplicationContext(), "Nenhum Feed Cadastrado");
        }
        /*try {
	        
	        
	         * cadastrando um feed para teste
	         
	        FeedSource source = new FeedSource("http://www.inovacaotecnologica.com.br/boletim/rss.xml");
	        
	        Log.d(">", source.getFeed_link());
	        Log.d(">", source.getTitle());
	        Log.d(">", source.getLink());
	        Log.d(">", source.getDescription());
	        Log.d(">", source.getImage_url());
	        
	        //source.save(m);
	        //Log.d(">", source.getId_feed_source().toString());
	        
	        
	       source.loadItens(m);
	        
	        
        }
        catch(Exception ex) {
        	Log.d(">", "Fuuuu -> " + ex.getMessage());
        }*/
        
        Log.d(">", "Encerrado método onCreate");        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
