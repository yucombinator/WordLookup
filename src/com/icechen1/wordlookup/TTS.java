package com.icechen1.wordlookup;

import java.util.Locale;

import android.os.AsyncTask;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;


public class TTS extends AsyncTask<String[], Integer, String> implements OnInitListener {
	public TextToSpeech mTts;
	private String definition;
	private String word;
	
	public void start(String[] d){
		

        
		word = d[0];
        definition = d[1];
        mTts = new TextToSpeech(MainActivity.context, this);
        
	}
	public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Log.i("WORDLOOKUP","Sending to handler");
    		// Handler handler=new Handler();
        	// handler.post(runOnResult);
            int result = mTts.setLanguage(Locale.US);
            
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("WORDLOOKUP", "TTS: This Language is not supported");
            } else {
                Log.i("WORDLOOKUP","Reading out definition.");
                if(MainActivity.shortTTS){
                    Log.i("WORDLOOKUP", "TTS: Reading Word: " + word);
            		mTts.speak(word, TextToSpeech.QUEUE_FLUSH, null);	
                }else{
            		mTts.speak(definition, TextToSpeech.QUEUE_FLUSH, null);             	
                }

            }

            Handler handler = new Handler();
            handler.postDelayed(StopTTS,30000);
 
        } else {
            Log.e("WORDLOOKUP", "TTS init Failed!");
        }

	}
	@Override
	protected String doInBackground(String[]... stuff) {
		start(stuff[0]);
		return null;
	}
	@Override
	protected void onPostExecute(String arg) {
		// async task finished
		Log.v("WORDLOOKUP", "Progress Finished.");
        }

	private Runnable StopTTS = new Runnable() {
		   public void run() {
	    		mTts.stop();
	    		mTts.shutdown();
	    		Log.i("WORDLOOKUP", "TTS Stopped.");
		   }
		};
}