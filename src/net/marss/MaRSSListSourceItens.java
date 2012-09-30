package net.marss;

import java.util.ArrayList;

import net.marss.arrayadapter.ArrayAdapterItensList;
import net.marss.arrayadapter.ArrayAdapterSourcesList;
import net.marss.database.Manager;
import net.marss.rss.FeedItem;
import net.marss.rss.FeedSource;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class MaRSSListSourceItens extends Activity {
	
	private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marss_list_source_itens);
        /*
         * verificando se foi a informação
         */
        if (!(MaRSSParameters.selectedSource instanceof FeedSource)) {
        	/*
        	 * buscar de todas as fontes
        	 */
        	AndroidTools.toast(getApplicationContext(), "Problemas ao carregar Feed");
        	finish();
        }
        /*
         * Pegando elementos necessários do leiaute
         */
        ListView listItens = (ListView) findViewById(R.id.listViewListSourceItens);
        /*
         * adicionando adapter ao listview
         */
        ArrayAdapterItensList itensList = new ArrayAdapterItensList(getApplicationContext(), android.R.layout.simple_list_item_1);
        listItens.setAdapter(itensList);
        /*
    	 * adicionando acao
    	 */
        listItens.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> listSources, View v, int index, long arg3)
			{								
				FeedItem item = (FeedItem) listSources.getAdapter().getItem(index);
				
				/*
				 * marcando como lida
				 */
				item.setControl_read(1);
				item.save(new Manager(getApplicationContext()));
				
				((TextView) v.findViewById(R.id.textSourceName)).setTextColor(Color.parseColor("#BEBEBE"));
				
				
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink())));
			}
		});
        
        FeedSource source = MaRSSParameters.selectedSource;
        ArrayList<FeedItem> itens = source.getItens(new Manager(getApplicationContext()));
        
        AndroidTools.toast(getApplicationContext(), Integer.toString(itens.size()));
        
        for (int i=0; i<itens.size(); ++i) {
        	itensList.add(itens.get(i));
        }
        
    }
    
    private void atualizar ()
    {
    	Manager m = new Manager(getApplicationContext());
    	
    	FeedSource source = MaRSSParameters.selectedSource;
    	source.loadItens(m);
    	
    	ListView listItens = (ListView) findViewById(R.id.listViewListSourceItens);
    	ArrayAdapterItensList itensList =  (ArrayAdapterItensList) listItens.getAdapter();
    	
        ArrayList<FeedItem> itens = source.getItens(m);
        
        itensList.clear();
        
        for (int i=0; i<itens.size(); ++i) {
        	itensList.add(itens.get(i));
        }
    	
    }
    
    private void excluirFonte ()
    {
    	MaRSSParameters.selectedSource.delete(new Manager(getApplicationContext()));
    	MaRSSParameters.selectedSource = null;
    	finish();
    }
    
    /*
     * OPTIONS
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
    	this.menu = menu;
        
        menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Atualizar");
        menu.add(Menu.NONE, Menu.FIRST + 2, Menu.NONE, "Excluir Fonte");
        
        /*
         * verificando se está ativo ocultar lidas
         */
        if (MaRSSParameters.hiddenReadeds) {
        	menu.add(Menu.NONE, Menu.FIRST + 3, Menu.NONE, "Mostrar Lidas");
        }
        else {
        	menu.add(Menu.NONE, Menu.FIRST + 3, Menu.NONE, "Ocultar Lidas");
        }
        
        return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item)
    {    	
    	Log.d(">", "Clicado no menu" + String.valueOf(item.getItemId()));
    	
    	switch (item.getItemId()) {
    	case (Menu.FIRST + 1):
    		this.atualizar();
    		break;
    		
    	case (Menu.FIRST + 2):
    		this.excluirFonte();
    		break;
    
	    case (Menu.FIRST + 3):
	    	/*
	    	 * atualizando parametro
	    	 */
	    	MaRSSParameters.hiddenReadeds = !MaRSSParameters.hiddenReadeds;
		    /*
	         * verificando se está ativo ocultar lidas
	         */
	        if (MaRSSParameters.hiddenReadeds) {
	        	menu.getItem(2).setTitle("Mostrar Lidas");
	        }
	        else {
	        	menu.getItem(2).setTitle("Ocultar Lidas");
	        }
	        /*
	         * atualizando
	         */
	        this.atualizar();
	        
			break;
		}
    	
    	return super.onOptionsItemSelected(item);
    }
}
