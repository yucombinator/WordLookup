package com.icechen1.wordlookup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
    /** Called when the activity is first created.
     * TODO
     * text to speech
	 * big and toast mode
	 * translate
	 * other sources
	 * ads
	 * multiple languages
	 * longer times
	 * offline
	 */
	public static Context context;
	public static View layout;
	public static TextView textbox;
	
	public static String NumOfDef;
	public static String ShowLength;
	public static boolean toggleTTS;
	public static boolean shortTTS;
	
    private int TTS_CHECK_CODE = 0;
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	 try{
    		 if (requestCode == TTS_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                Log.i("WORDLOOKUP","TTS Data found.");
            }
            else {
            	if(toggleTTS){
                    Log.i("WORDLOOKUP","Installing TTS Data.");
            		//no data - install it now
            		Intent installTTSIntent = new Intent();
            		installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            		startActivity(installTTSIntent);
            	}
            }
        }     
    }
    catch(Exception e){
		e.printStackTrace();
    }
}
    
	@Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //check for TTS data
        try{
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, TTS_CHECK_CODE);
        }
        catch(Exception e){
			e.printStackTrace();
        }
        
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        //Set a context
        context = getApplicationContext(); 
        
        /*
         * Loads Custom Toast Layout
         */
    	LayoutInflater inflater = getLayoutInflater();
    	
    	layout = inflater.inflate(R.layout.toast,
    	                               (ViewGroup) findViewById(R.id.toast_layout_root));
    	textbox = (TextView) layout.findViewById(R.id.text);
        /*
         * Loading... Toast
         */
    	CharSequence text = "Loading...";
    	int duration = Toast.LENGTH_SHORT;	
    	final Toast toast = new Toast(MainActivity.context);
    	TextView textbox = MainActivity.textbox;
    	textbox.setText(text);
    	toast.setGravity(Gravity.BOTTOM, 0, 0);
    	toast.setView(MainActivity.layout);
    	toast.setDuration(duration);
    	toast.show();
        

    	/*
    	 * Loads Settings
    	 */
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	    NumOfDef = settings.getString("NumOfDef", "2");
	    ShowLength = settings.getString("DisplayLength", "Normal");
	    toggleTTS = settings.getBoolean("toggleTTS", false);	
	    shortTTS = settings.getBoolean("shortTTS", false);	
	    
	    Log.d("WORDLOOKUP","Settings: \nNumber of Definitions: " + NumOfDef +"\nDisplay Length: " + ShowLength
	    		+"\nTTS: " + toggleTTS +"\nShort TTS: " + shortTTS);   
	    
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                try {
					handleSendText(intent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Handle text being sent
        } else {
            // Handle other intents, such as being started from the home screen
        	
            finish();
        }
        }
    }

    void handleSendText(Intent intent) throws Exception {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        API mAPI = new API();
        mAPI.execute(sharedText);
        finish();
    }
}