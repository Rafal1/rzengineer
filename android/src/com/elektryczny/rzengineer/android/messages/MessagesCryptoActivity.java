package com.elektryczny.rzengineer.android.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.elektryczny.rzengineer.android.R;
import com.elektryczny.rzengineer.android.RSA;

public class MessagesCryptoActivity extends Activity {

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

                RSA alg = new RSA("privateKey.pem", "publicKey.pub");
                alg.generateNewKeys();
                Toast genDone = Toast.makeText(getBaseContext(), "Wygenerowano i zapisano klucze", Toast.LENGTH_SHORT);
                genDone.show();
            }
        });

        Button startConversationB = (Button) findViewById(R.id.messsagesStarConversation);
        startConversationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SoundIntent = new Intent(getBaseContext(), ConversationActivity.class);
                startActivity(SoundIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.messages_crypto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
