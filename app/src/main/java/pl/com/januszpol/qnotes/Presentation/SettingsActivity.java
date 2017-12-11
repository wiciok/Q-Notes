package pl.com.januszpol.qnotes.Presentation;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import pl.com.januszpol.qnotes.Model.Services.ColorService;
import pl.com.januszpol.qnotes.Model.Services.FontService;
import pl.com.januszpol.qnotes.R;

public class SettingsActivity extends AppCompatActivity {
    private ColorService colorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        colorService = new ColorService(this);

        RadioGroup colorsRadioGroup = (RadioGroup) findViewById(R.id.colorsRadioGroup);

        int radioId = colorService.RestoreColor();
        colorsRadioGroup.check(radioId);

        colorsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                colorService.changeColor(checkedId, true);
            }
        });

        RadioGroup fontRadioGroup = (RadioGroup)findViewById(R.id.fontRadioGroup);
        fontRadioGroup.check(FontService.getRadioId());

        fontRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FontService.setFont(getApplicationContext(), checkedId, true);
            }
        });
    }
}
