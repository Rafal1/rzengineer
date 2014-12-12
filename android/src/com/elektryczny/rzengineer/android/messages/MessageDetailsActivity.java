package com.elektryczny.rzengineer.android.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elektryczny.rzengineer.android.R;

public class MessageDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        Intent extrasGet = getIntent();
        String messReceiver = extrasGet.getExtras().getString(ConversationActivity.RECEIVER_MESSAGE_EXTRAS);
        String encryptedMess = extrasGet.getExtras().getString(ConversationActivity.ENCRYPTED_MESSAGE_EXTRAS); //getStringExtra(ConversationActivity.ENCRYPTED_MESSAGE_EXTRAS);
        if (encryptedMess == null || messReceiver == null) {
            encryptedMess = "";
            messReceiver = "";
        }
        TextView showMessTitile = (TextView) findViewById(R.id.titleMesssageDetailsView);
        showMessTitile.setText("Wiadomość zaszyfrowana kluczem publicznym użytkownika " + messReceiver + ".");
        TextView showEncryptedMess = (TextView) findViewById(R.id.encryptedListedMessage);
        showEncryptedMess.setText(encryptedMess);

        Button b = (Button) findViewById(R.id.return_to_message_list_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
