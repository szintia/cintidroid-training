package com.example.texttospeech;

import android.content.Intent;
import android.os.Build;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

import javax.xml.transform.Result;

public class TextToSpeechDemo extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int LANGUAGE = 103;
    private Locale lang;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        Button button = (Button) findViewById(R.id.button);
        final EditText editText = (EditText) findViewById(R.id.editText);
        textToSpeech = new TextToSpeech(this, this, "com.google.android.tts");
        lang = Locale.getDefault();

        textToSpeech.setPitch(.5f);
        textToSpeech.setSpeechRate(.8f);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if(text!=null && text.length()>0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeech.speak(editText.getText(), TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                }
            }
        });
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(lang);
        } else {
            textToSpeech.setLanguage(Locale.getDefault());
        }
    }

    protected void OnActivityResult(int requestCode, Result result, Intent data) {
        if(requestCode == LANGUAGE) {
            //install new language
            Intent installTextEngine = new Intent();
            installTextEngine.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installTextEngine);
        }
    }
}
