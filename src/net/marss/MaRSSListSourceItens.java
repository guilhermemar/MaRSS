package net.marss;

import java.util.ArrayList;

import net.marss.arrayadapter.ArrayAdapterItensList;
import net.marss.database.Manager;
import net.marss.rss.FeedItem;
import net.marss.rss.FeedSource;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class MaRSSListSourceItens extends Activity {
	
	private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marss_list_source_itens);
        
        /*
         * Pegando elementos necessários do leiaute
         */
        ListView listItens = (ListView) findViewById(R.id.listViewListSourceItens);
        TextView sourceName = (TextView) findViewById(R.id.textViewSourceName);
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
        ArrayList<FeedItem> itens;
        
        if (source == null) {
        	itens = FeedSource.getAllItens(new Manager(getApplicationContext()));
        	sourceName.setText("Todos Feeds");
        } else {
        	sourceName.setText(source.getTitle());
        	itens = source.getItens(new Manager(getApplicationContext()));
        }
        
        for (int i=0; i<itens.size(); ++i) {
        	itensList.add(itens.get(i));
        }
        
    }
    
    private void atualizar ()
    {
    	if (AndroidTools.testConnection(getApplicationContext())) {
    		
    		Manager m = new Manager(getApplicationContext());
    		
	    	AndroidTools.toast(getApplicationContext(), "Iniciada atualização de Feeds", Toast.LENGTH_SHORT);
	    	FeedSource source = MaRSSParameters.selectedSource;
	    	
	    	if (source == null) {
	    		ArrayList<FeedSource> sources = m.getSources();
	    		for (int i=0; sources.size()<i; ++i) {
	    			sources.get(i).loadItens(m);
	    		}
	    		
	    	} else {
	    		source.loadItens(m);
	    	}
	    	
	    	AndroidTools.toast(getApplicationContext(), "Finalizada atualização de Feeds", Toast.LENGTH_SHORT);
	    	this.atualizarLista();
    	}
		else {
			AndroidTools.toast(getApplicationContext(), "Sem conexão com a internet!");
		}
    	
    }
    
    private void atualizarLista () 
    {
    	Manager m = new Manager(getApplicationContext());
    	FeedSource source = MaRSSParameters.selectedSource;
    	
    	ArrayList<FeedItem> itens;
    	if (source == null) {
    		itens = FeedSource.getAllItens(m);
    	} else {
    		source.clearList();
    		itens = source.getItens(m);
    	}
    	
    	
    	ListView listItens = (ListView) findViewById(R.id.listViewListSourceItens);
    	ArrayAdapterItensList itensList =  (ArrayAdapterItensList) listItens.getAdapter();
    	
        itensList.clear();
        
        for (int i=0; i<itens.size(); ++i) {
        	itensList.add(itens.get(i));
        }
    }
    
    
    private void excluirFonte ()
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Deseja mesmo ecluir esta fonte?")
	           .setCancelable(false)
	           .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id)
	               {
	            	   MaRSSParameters.selectedSource.delete(new Manager(getApplicationContext()));
	            	   MaRSSParameters.selectedSource = null;
	            	   finish();
	               }
	           })
	           .setNegativeButton("Não", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id)
	               {}
	           });
	    AlertDialog alert = builder.create();
	    alert.show();	
    }
    
    /*
     * OPTIONS
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
    	this.menu = menu;
        
        menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Atualizar");
        
        if (MaRSSParameters.selectedSource != null) {
        	menu.add(Menu.NONE, Menu.FIRST + 2, Menu.NONE, "Excluir Fonte");
        }
        
        /*
         * verificando se está ativo ocultar lidas
         */
        if (MaRSSParameters.hiddenReadeds) {
        	menu.add(Menu.NONE, Menu.FIRST + 3, Menu.NONE, "Mostrar Lidas");
        }
        else {
        	menu.add(Menu.NONE, Menu.FIRST + 3, Menu.NONE, "Ocultar Lidas");
        }
        
        menu.add(Menu.NONE, Menu.FIRST + 4, Menu.NONE, "Sobre");
        
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
	        this.atualizarLista();
	        
			break;
	
	    case (Menu.FIRST + 4) :
	    	startActivity(new Intent(getApplicationContext(), MaRSSSobre.class));
			break;
		}
    	
    	return super.onOptionsItemSelected(item);
    }
}
