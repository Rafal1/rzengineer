package com.elektryczny.rzengineer.android.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.elektryczny.rzengineer.android.R;
import com.elektryczny.rzengineer.android.RSA;

public class MessagesCryptoActivity extends Activity {
    private static Boolean isGenerating = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_crypto);

        TextView messageDesc = (TextView) findViewById(R.id.messagesDesc);
        messageDesc.setText("Funckcjonalność polega na wymianie wiadomości tekstowych. Jest to obecnie najpowszechnieszy sposób wykorzystania treści multimedialnych. " +
                "Dodatkowo zastosowałem szyfrowanie wiadomości z wykorzystaniem kryptografii asymetrycznej (algorytm RSA). Przechowuje klucze prywatne i publiczne i wykorzystuje je do przetwarzania danych. " +
                "Można wysłaś max. 100 wiadomości po czym lista jest czyszczona.");
        TextView messageDesc2 = (TextView) findViewById(R.id.messagesDesc2);
        messageDesc2.setText("Naciśnije Generuj klucze (publiczny do szyfrowania i pywatny do odszyfrowania wiadomości). " +
                "W systemie nastąpie wymiana kluczy publicznych z systemem (andorid powtórzy każde Twoje zdanie). " +
                "Naciśnij przycisk rozpocznij pisanie wiadomości.");

        Button generateB = (Button) findViewById(R.id.messagesGenerateKeys);
        generateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGenerating) {
                    new Thread(new Runnable() {
                        public void run() {
                            RSA alg = new RSA("privateKey.pem", "publicKey.pub");
                            alg.generateNewKeys();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast genDone = Toast.makeText(getBaseContext(), "Wygenerowano i zapisano klucze", Toast.LENGTH_SHORT);
                                    genDone.show();
                                }
                            });
                            isGenerating = false;
                        }
                    }).start();
                    isGenerating = true;
                } else {
                    Toast genDone = Toast.makeText(getBaseContext(), "Czekaj, klucze są właśnie generowane", Toast.LENGTH_SHORT);
                    genDone.show();
                }

            }
        });

        Button startConversationB = (Button) findViewById(R.id.messsagesStarConversation);
        startConversationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGenerating) {
                    Intent SoundIntent = new Intent(getBaseContext(), ConversationActivity.class);
                    startActivity(SoundIntent);
                } else {
                    Toast genDone = Toast.makeText(getBaseContext(), "Czekaj na wygenerowanie kluczy.", Toast.LENGTH_SHORT);
                    genDone.show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
