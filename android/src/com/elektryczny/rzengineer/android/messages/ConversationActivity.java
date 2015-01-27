package com.elektryczny.rzengineer.android.messages;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.elektryczny.rzengineer.android.R;
import com.elektryczny.rzengineer.android.RSA;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ConversationActivity extends Activity {
    private static ListView msgList = null;
    private EditText editText;
    private static ConversationAdapter ad;
    private ArrayList<Message> listOfMessage = new ArrayList<Message>();
    public static final String ENCRYPTED_MESSAGE_EXTRAS = "Encrypted";
    public static final String RECEIVER_MESSAGE_EXTRAS = "Receiver";
    private static final Integer MAX_MESSAGES_NUMBER = 100;

    @Override
    protected void onResume() {
        super.onResume();
        ad.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ad.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        editText = (EditText) findViewById(R.id.new_message);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                    handled = true;
                }
                return handled;
            }
        });
        this.createMessageList();
    }

    private void createMessageList() {
        msgList = (ListView) findViewById(R.id.messages_listView);
        msgList.setAdapter(ad = new ConversationAdapter(listOfMessage, this));
        msgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent encView = new Intent(getBaseContext(), MessageDetailsActivity.class);
                encView.putExtra(ENCRYPTED_MESSAGE_EXTRAS, listOfMessage.get(position).getRSAEncrypted());
                encView.putExtra(RECEIVER_MESSAGE_EXTRAS, listOfMessage.get(position).getTo());
                startActivity(encView);
            }
        });
    }

    public void sendMessage() { //method for button aside soft keyboard
        String msgContent = editText.getText().toString();
        RSA client = new RSA("privateKey.pem", "publicKeyFromSomebody.pub");//phone have own private and public from sb it message for
        RSA simulatedClient = new RSA("privateKeyFromSomebody.pem", "publicKey.pub");
        String EncryptedMsgContent = null;
        String DecryptedMsgContent = null;
        String EncryptedMsg2Content = null;
        String DecryptedMsg2Content = null;
        try {
            EncryptedMsgContent = client.Encrypt(msgContent);
            EncryptedMsg2Content = simulatedClient.Encrypt(msgContent);

            DecryptedMsgContent = simulatedClient.Decrypt(EncryptedMsgContent);
            DecryptedMsg2Content = client.Decrypt(EncryptedMsg2Content);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        Message msg = createNewMessage(DecryptedMsgContent, "Ja");
        msg.setRSAEncrypted(EncryptedMsgContent);
        Message msg2 = createNewMessage(DecryptedMsg2Content, "Android");
        msg2.setRSAEncrypted(EncryptedMsg2Content);
        if (listOfMessage.size() >= MAX_MESSAGES_NUMBER) {
            listOfMessage = new ArrayList<Message>();
        }
        listOfMessage.add(msg);
        listOfMessage.add(msg2);
        editText.setText("");
//        editText.clearFocus();
        hideKeyboard();
        ad.notifyDataSetChanged();
    }

    public void sendMessage(View view) { //method for normal button
        String msgContent = editText.getText().toString();
        RSA client = new RSA("privateKey.pem", "publicKeyFromSomebody.pub");//phone have own private and public from sb it message for
        RSA simulatedClient = new RSA("privateKeyFromSomebody.pem", "publicKey.pub");
        String EncryptedMsgContent = null;
        String DecryptedMsgContent = null;
        String EncryptedMsg2Content = null;
        String DecryptedMsg2Content = null;

        try {
            EncryptedMsgContent = client.Encrypt(msgContent);
            EncryptedMsg2Content = simulatedClient.Encrypt(msgContent);

            DecryptedMsgContent = simulatedClient.Decrypt(EncryptedMsgContent);
            DecryptedMsg2Content = client.Decrypt(EncryptedMsg2Content);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        Message msg = createNewMessage(DecryptedMsgContent, "Ja");
        msg.setRSAEncrypted(EncryptedMsgContent);
        Message msg2 = createNewMessage(DecryptedMsg2Content, "Android");
        msg2.setRSAEncrypted(EncryptedMsg2Content);
        if (listOfMessage.size() >= MAX_MESSAGES_NUMBER) {
            listOfMessage = new ArrayList<Message>();
        }
        listOfMessage.add(msg);
        listOfMessage.add(msg2);
        editText.setText("");
//        editText.clearFocus();
        hideKeyboard();
        ad.notifyDataSetChanged();
    }

    private Message createNewMessage(String content, String from) {
        Message newMessage = new Message();
        newMessage.setFrom(from);
        if (from.equals("Ja")) {
            newMessage.setTo("Android");
        } else if (from.equals("Android")) {
            newMessage.setTo("Ja");
        } else {
            newMessage.setTo("undefined");
        }
        newMessage.setContent(content);
        Date currentDate = Calendar.getInstance().getTime();
        newMessage.setDateSent(currentDate);
        return newMessage;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("d", listOfMessage);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            listOfMessage = (ArrayList<Message>) savedInstanceState.getSerializable("d");
            ad = new ConversationAdapter(listOfMessage, this);
            ListView listView = (ListView) findViewById(R.id.messages_listView);
            listView.setAdapter(ad);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
}