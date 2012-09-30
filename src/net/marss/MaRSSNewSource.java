package net.marss;

import java.util.ArrayList;

import net.marss.arrayadapter.ArrayAdapterSourcesAdd;
import net.marss.database.Manager;
import net.marss.rss.FeedSource;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MaRSSNewSource extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marss_new_source);
        
        /*
         * Capturando objetos da tela
         */
        Button   sourceAdd   = (Button)   findViewById(R.id.buttonSourceAdd);
        Button   saveSources = (Button)   findViewById(R.id.buttonSaveSources);
        Button   cancel      = (Button)   findViewById(R.id.buttonCancel);
        ListView sourceAdded = (ListView) findViewById(R.id.listViewSourceAdded);
        
        sourceAdded.setAdapter(
        	new ArrayAdapterSourcesAdd(
        		getApplicationContext(),
        		android.R.layout.simple_list_item_1
        	)
        );
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *  AÇÕES DOS BOTÕES
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        sourceAdd.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				TextView   sourceUrl = (TextView) findViewById(R.id.textSourceUrl);
				ListView sourceAdded = (ListView) findViewById(R.id.listViewSourceAdded);
				
				FeedSource source = new FeedSource();
				
				Log.d(">", "tentando carregar : " + sourceUrl.getText().toString());
				
				if (source.loadSource(sourceUrl.getText().toString())) {
					AndroidTools.toast(getApplicationContext(), "Carregou");
					
					ArrayAdapterSourcesAdd adapter = (ArrayAdapterSourcesAdd) sourceAdded.getAdapter();
					adapter.add(source);
					
				} else {
					AndroidTools.toast(getApplicationContext(), "Erro ao carregar o Feed");
				}
				
				/* * * * * * * * * 
		         *  CHAMADA DO MÉTODO PARA FACILITAR A APRESENTAÇÃO
		         */
		        getFeedLink();
				
			}
		});
        
        saveSources.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Manager m = new Manager(getApplicationContext());
				
				ListView sourceAdded = (ListView) findViewById(R.id.listViewSourceAdded);
				ArrayAdapterSourcesAdd sources = (ArrayAdapterSourcesAdd) sourceAdded.getAdapter();
				
				int total = sources.getCount();
				
				if (total == 0) {
					AndroidTools.toast(getApplicationContext(), "Nada para salvar");
					return;
				}
				
				for (int i=0; i<total; ++i) {
					sources.getItem(i).save(m);
					sources.getItem(i).loadItens(m);
				}
				
				finish();
			}
		});
        
        cancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});
        
        
        
        
        /* * * * * * * * * 
         *  CHAMADA DO MÉTODO PARA FACILITAR A APRESENTAÇÃO
         */
        this.getFeedLink();
    }

    /*
     * OPTIONS
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        
        menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Cancelar");
        menu.add(Menu.NONE, Menu.FIRST + 2, Menu.NONE, "Sobre");
        
        return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {    	
    	Log.d(">", "Clicado no menu" + String.valueOf(item.getItemId()));
    	
    	switch (item.getItemId()) {
    	case (Menu.FIRST + 1):
    		finish();
    		break;
    		
    	case (Menu.FIRST + 2) :
	    	startActivity(new Intent(getApplicationContext(), MaRSSSobre.class));
			break;
		}
    	
    	
    	return super.onOptionsItemSelected(item);
    }
    
    /*
     * Classe auxiliar apenas para facilitar a apresentação
     */
    public void getFeedLink ()
    {
    	ArrayList<String> s = new ArrayList<String>();
    	
    	s.add("http://www.inovacaotecnologica.com.br/boletim/rss.xml"); 
    	s.add("http://rss.terra.com.br/0,,EI1,00.xml");
    	s.add("http://rss.terra.com.br/0,,EI4795,00.xml");
    	s.add("http://carroonline.terra.com.br/rss.asp"); 
    	s.add("http://rss.terra.com.br/0,,EI1267,00.xml");
    	s.add("http://rss.terra.com.br/0,,EI1497,00.xml"); 
    	s.add("http://www2.portoalegre.rs.gov.br/cs/rss.php");
    	s.add("http://www.praquempedala.com.br/blog/feed/");
    	s.add("http://cerejadeneve.com/feed/");
    	s.add("http://feeds.feedburner.com/tutorial9"); 
    	
    	TextView   sourceUrl = (TextView) findViewById(R.id.textSourceUrl);
 		sourceUrl.setText(s.get( (int)(Math.random() * (s.size())) ));
    }
}