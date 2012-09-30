package net.marss;

import java.util.ArrayList;

import net.marss.arrayadapter.ArrayAdapterSourcesList;
import net.marss.database.Manager;
import net.marss.rss.FeedSource;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MaRSS extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marss_main);
        /*
         * Pegando elementos necessários do leiaute
         */
        ListView listSources = (ListView) findViewById(R.id.listViewListSources);
        /*
         * adicionando adapter ao listview
         */
        listSources.setAdapter(
        	new ArrayAdapterSourcesList(
        		getApplicationContext(),
        		android.R.layout.simple_list_item_1
        	)
        );
        /*
    	 * adicionando acao
    	 */
    	listSources.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> listSources, View arg1, int index, long arg3)
			{								
				FeedSource source = (FeedSource) listSources.getAdapter().getItem(index);
				
				MaRSSParameters.selectedSource = source;

				startActivityForResult(new Intent(getApplicationContext(), MaRSSListSourceItens.class), 100);
			}
		});
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
        	AndroidTools.toast(getApplicationContext(), "Nenhum Feed Cadastrado", Toast.LENGTH_SHORT);
        	startActivityForResult(new Intent(getApplicationContext(), MaRSSNewSource.class), 101);
        }
        
        /*
         * exibindo lista de feeds
         */
        this.atualizarListaSources(sources);
        
        Log.d(">", "Encerrado método onCreate");        
    }
    
    private void atualizarSources ()
    {
    	this.atualizarSources(false);
    }
    private void atualizarSources (Boolean load)
    {
    	Manager m = new Manager(getApplicationContext());
    	ArrayList<FeedSource> sources = m.getSources();
    	
    	Log.d(">", "encontrado " + Integer.toString(sources.size()) + " feed(s)");
    	
    	/*
    	 * verificando se não foi excluido todos itens
    	 */
    	if (sources.size() == 0) {
    		AndroidTools.toast(getApplicationContext(), "Nenhum Feed Cadastrado", Toast.LENGTH_SHORT);
        	startActivityForResult(new Intent(getApplicationContext(), MaRSSNewSource.class), 101);
    	}
    	
    	if (load) {
    		/*
    		 * testando conexão com a internet
    		 */
    		if (AndroidTools.testConnection(getApplicationContext())) {
    			AndroidTools.toast(getApplicationContext(), "Iniciada atualização de Feeds", Toast.LENGTH_SHORT);
    			
        		for (int i=0; i < sources.size(); ++i) {
        			Log.d(">", "Atualizando feed " + sources.get(i).getTitle());
        			sources.get(i).loadItens(m);
        		}
        		
        		AndroidTools.toast(getApplicationContext(), "Finalizada atualização de Feeds", Toast.LENGTH_SHORT);
    		}
    		else {
    			AndroidTools.toast(getApplicationContext(), "Sem conexão com a internet!");
    		}
    		
    	}
    	
    	this.atualizarListaSources(sources);
    	
    }
    
    private void atualizarListaSources (ArrayList<FeedSource> sources) {
    	ListView                listSources  = (ListView) findViewById(R.id.listViewListSources);
    	ArrayAdapterSourcesList adapter      = (ArrayAdapterSourcesList) listSources.getAdapter();
    	Integer total = sources.size();
    	
    	/*
    	 * limpando atual
    	 */
    	adapter.clear();
    	
    	for (int i=0; i<total; ++i) {
    		adapter.add(sources.get(i));
    	}
    }

    /*
     * OPTIONS
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        
        menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Novo");
        menu.add(Menu.NONE, Menu.FIRST + 2, Menu.NONE, "Atualizar");
        menu.add(Menu.NONE, Menu.FIRST + 3, Menu.NONE, "Ver todos");
        menu.add(Menu.NONE, Menu.FIRST + 4, Menu.NONE, "Sobre");
        
        return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {    	
    	Log.d(">", "Clicado no menu" + String.valueOf(item.getItemId()));
    	
    	switch (item.getItemId()) {
    	case (Menu.FIRST + 1):
    		startActivityForResult(new Intent(getApplicationContext(), MaRSSNewSource.class), 101);
    		break;
    		
    	case (Menu.FIRST + 2) :
    		this.atualizarSources(true);
    		break;
    	
	    case (Menu.FIRST + 3) :
	    	MaRSSParameters.selectedSource = null;
	    	startActivityForResult(new Intent(getApplicationContext(), MaRSSListSourceItens.class), 100);
			break;
		
	    case (Menu.FIRST + 4) :
	    	startActivity(new Intent(getApplicationContext(), MaRSSSobre.class));
			break;
		}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	switch (requestCode) {
    	case 100 :
    	case 101 :
    		this.atualizarSources();
    		break;
    	}
	}
}
