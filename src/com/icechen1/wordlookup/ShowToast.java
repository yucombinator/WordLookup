package com.icechen1.wordlookup;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class ShowToast {
	public static void show(String definition){
	    long length = 2000;
	    String sLength = MainActivity.ShowLength;
	    if (sLength.equals("Short")){
		    length = 2000;
	    }
	    
	    if (sLength.equals("Normal")){
		    length = 5000;
	    }
	    
	    if (sLength.equals("Long")){
		    length = 9000;
	    }
	    
	    if (sLength.equals("Very Long")){
		    length = 15000;
	    }
	    Log.d("WORDLOOKUP","sLength = " + sLength + " length = " + length);
    	//CharSequence text = definition;
    	int duration = Toast.LENGTH_SHORT; //TODO Option this
    	//Toast toast = Toast.makeText(MainActivity.context, text, duration);
    	final Toast toast = new Toast(MainActivity.context);
    	TextView textbox = MainActivity.textbox;
    	textbox.setText(definition);
    	toast.setGravity(Gravity.BOTTOM, 0, 0);
    	toast.setView(MainActivity.layout);
    	toast.setDuration(duration);
    	toast.show();
    	
    	new CountDownTimer(length, 1000)
    	{

    	    public void onTick(long millisUntilFinished) {toast.show();}
    	    public void onFinish() {toast.show();}

    	}.start();
	}
}
