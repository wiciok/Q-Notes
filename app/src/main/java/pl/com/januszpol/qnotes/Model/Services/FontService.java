package pl.com.januszpol.qnotes.Model.Services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;

import pl.com.januszpol.qnotes.R;

/**
 * Created by wk on 10.12.2017.
 */

public class FontService {
    private static Typeface currentFont = Typeface.DEFAULT;
    private static final String SharedPreferenceName = "QNOTES_APPLICATION_FONT";
    private static int radioButtonId;

    public static void setFont(Context context, int radioId, boolean savePrefs){
            currentFont=getFontViaRadioButtonId(radioId);
            if(savePrefs){
                SharedPreferences sharedPrefs = context.getSharedPreferences(SharedPreferenceName, Activity.MODE_PRIVATE);
                SharedPreferences.Editor preferencesEditor = sharedPrefs.edit();
                preferencesEditor.putString(SharedPreferenceName, Integer.toString(radioId));
                preferencesEditor.apply();
            }
    }

    public static void initFont(Activity context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(SharedPreferenceName, Activity.MODE_PRIVATE);
        String radioId = sharedPrefs.getString(SharedPreferenceName, Integer.toString(R.id.radioFontDefault));
        radioButtonId=Integer.parseInt(radioId);
        setFont(context, Integer.parseInt(radioId), true);
    }

    public static Typeface getFont(){
        return currentFont;
    }

    public static int getRadioId(){
        return radioButtonId;
    }

    private static Typeface getFontViaRadioButtonId(int checkedId){
        switch(checkedId){
            default:
            case R.id.radioFontDefault:
                return Typeface.DEFAULT;
            case R.id.radioFontMonospace:
                return Typeface.MONOSPACE;
            case R.id.radioFontSerif:
                return Typeface.SERIF;
        }
    }
}

