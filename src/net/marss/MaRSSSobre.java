package net.marss;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MaRSSSobre extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marss_sobre);
        
        Log.d(">", "Encerrado método onCreate");        
    }
    
   
    /*
     * OPTIONS
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Voltar");
        
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
