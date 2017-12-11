package pl.com.januszpol.qnotes.Model.Services;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;

import pl.com.januszpol.qnotes.R;

/**
 * Created by wk on 10.12.2017.
 */

public class ColorService {
    private AppCompatActivity context;
    private static final String SharedPreferenceName = "QNOTES_APPLICATION_COLOR";


    public ColorService(AppCompatActivity contextActivity){
        this.context=contextActivity;
    }


    public int changeColor(int checkedId, boolean changePrefs){
        ColorDrawable newColor;
        int retRadio;

        switch(checkedId){
            default:
            case R.id.radioDefault:
                newColor=new ColorDrawable(context.getColor(R.color.colorPrimary));
                retRadio=R.id.radioDefault;
                break;
            case R.id.radioGreen:
                newColor=new ColorDrawable(context.getColor(R.color.colorGreen));
                retRadio=R.id.radioGreen;
                break;
            case R.id.radioOrange:
                newColor=new ColorDrawable(context.getColor(R.color.colorOrange));
                retRadio=R.id.radioOrange;
                break;
            case R.id.radioRed:
                newColor=new ColorDrawable(context.getColor(R.color.colorRed));
                retRadio=R.id.radioRed;
                break;
        }

        if(changePrefs){
            SharedPreferences sharedPrefs = context.getSharedPreferences(SharedPreferenceName, Activity.MODE_PRIVATE);
            SharedPreferences.Editor preferencesEditor = sharedPrefs.edit();
            preferencesEditor.putString(SharedPreferenceName, Integer.toString(checkedId));
            preferencesEditor.apply();
        }
        android.support.v7.app.ActionBar bar = context.getSupportActionBar();
        bar.setBackgroundDrawable(newColor);
        return retRadio;
    }


    public int RestoreColor(){
        SharedPreferences sharedPrefs = context.getSharedPreferences(SharedPreferenceName, Activity.MODE_PRIVATE);
        String colorId = sharedPrefs.getString(SharedPreferenceName, Integer.toString(R.id.radioDefault));
        return changeColor(Integer.parseInt(colorId), false);
    }
}
