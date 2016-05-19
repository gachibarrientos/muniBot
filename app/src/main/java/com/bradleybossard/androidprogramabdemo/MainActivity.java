package com.bradleybossard.androidprogramabdemo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    String request, response;
    Chat chatSession;
    Bot bot;
    EditText txtRequest;
    TextView lblResponse;
    Button btnOk, btnReconoce;
    String botname = "munibot";
    TextToSpeech t1;
    Locale spanish = new Locale("es", "ES");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtRequest = (EditText) findViewById(R.id.txtRequest);
        lblResponse = (TextView) findViewById(R.id.lblResponse);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnReconoce = (Button) findViewById(R.id.btnReconoce);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(spanish);
                }
            }
        });

        File fileExt = new File(getExternalFilesDir(null).getAbsolutePath() + "/bots");

        if (!fileExt.exists()) {
            ZipFileExtraction extract = new ZipFileExtraction();
            try {
                extract.unZipIt(getAssets().open("bots.zip"), getExternalFilesDir(null).getAbsolutePath() + "/");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                final String path = getExternalFilesDir(null).getAbsolutePath();
                bot = new Bot(botname, path);
                chatSession = new Chat(bot);
                //Bot bot = new Bot(botname, path);
                //Chat chatSession = new Chat(bot);
                String request = "Hola";
                //   String request = "Quien sos?";
                String response = chatSession.multisentenceRespond(request);
                Log.v(TAG, "response = " + response);
                return response;

            }

            @Override
            protected void onPostExecute(String response) {
                if (response.isEmpty()) {
                    response = "There is no response";
                }
                ((TextView) findViewById(R.id.title_text))
                        .setText(response);
            }
        }.execute();
        btnReconoce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-ES");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                try {
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(), "Tu dispositivo no soporta el reconocimiento de voz", Toast.LENGTH_LONG).show();
                }
            }

        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                obtenerRespuestas();
            }
        });
        btnOk.setOnKeyListener(new View.OnKeyListener() {
            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    obtenerRespuestas();
                }
                return false;
            }
        });
    }


    public void onActivityResult(int requestcode, int resultcode, Intent datos) {
        if (resultcode == Activity.RESULT_OK && datos != null) {
            ArrayList<String> text = datos.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            final CharSequence[] items = new CharSequence[text.size()];
            for (int i = 0; i < text.size(); i++) {
                items[i] = text.get(i);
            }
            request = items.toString();
        }

    }




    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void obtenerRespuestas(){
      //  Bot bot = new Bot(botname, path);
      // Chat chatSession = new Chat(bot);

        request = txtRequest.getText().toString();
        response = chatSession.multisentenceRespond(request);
        lblResponse.setText(response);
        String toSpeak = lblResponse.getText().toString();
        txtRequest.setText("");
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }
}

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class MainActivity extends Activity {
        String botname = "alice2";
        String path = "C:/Users/Gachi/Desktop/android-program-ab-demo-master/app/src/main/assets/bots";
        Bot bot = new Bot(botname, path);


        Chat chatSession = new Chat(bot);
        String request = "Hello. Are you alive? What is your name?";
        String response = chatSession.multisentenceRespond(request);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            TextView t = new TextView(this);
            t = (TextView) findViewById(R.id.title_text);
            t.setText(response);

        }

    }




        @Override
        public boolean onCreateOptionsMenu(Menu menu)
        {
// Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }


    }
*/
