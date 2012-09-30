package net.marss;

import java.util.ArrayList;

import net.marss.arrayadapter.ArrayAdapterSourcesAdd;
import net.marss.database.Manager;
import net.marss.rss.FeedSource;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
        ListView sourceAdded = (ListView) findViewById(R.id.listViewSourceAdded);
        
        /*
		 * para testes
		 */
        TextView   sourceUrl = (TextView) findViewById(R.id.textSourceUrl);
		sourceUrl.setText("http://rss.terra.com.br/0,,EI238,00.xml");
        
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
				
			}
		});
        
        saveSources.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Manager m = new Manager(getApplicationContext());
				
				ListView sourceAdded = (ListView) findViewById(R.id.listViewSourceAdded);
				ArrayAdapterSourcesAdd sources = (ArrayAdapterSourcesAdd) sourceAdded.getAdapter();
				
				int total = sources.getCount();
				
				for (int i=0; i<total; ++i) {
					sources.getItem(i).save(m);
					sources.getItem(i).loadItens(m);
				}
				
				finish();
			}
		});
                
    }

    /*
     * OPTIONS
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        
        menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Cancelar");
        
        return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {    	
    	Log.d(">", "Clicado no menu" + String.valueOf(item.getItemId()));
    	
    	switch (item.getItemId()) {
    	case (Menu.FIRST + 1):
    		finish();
    		break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
}