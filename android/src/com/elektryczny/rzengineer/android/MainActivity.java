package com.elektryczny.rzengineer.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elektryczny.rzengineer.android.messages.MessagesCryptoActivity;
import com.elektryczny.rzengineer.android.multimediasolid.MultimediaSolidActivity;
import com.elektryczny.rzengineer.android.picture.PictureActivity;
import com.elektryczny.rzengineer.android.sound.SoundCaptureActivity;
import com.elektryczny.rzengineer.android.video.VideoActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        TextView title = (TextView) findViewById(R.id.textView);
        title.setText("CommMethods");
        TextView desc1 = (TextView) findViewById(R.id.textView2);
        desc1.setText("Jest to aplikacja mająca na celu przedstawienie metod komunikacji wykorzystując do tego celu treści multimedialne obsługiwane przez system Android. Bazuje na obserwacjach aktualnych trendów przez autora.");
        TextView desc2 = (TextView) findViewById(R.id.textView3);
        desc2.setText("Proszę wybrać metodę z poniższej listy.");

        Button soundB = (Button) findViewById(R.id.soundButton);
        soundB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SoundIntent = new Intent(getBaseContext(), SoundCaptureActivity.class);
                startActivity(SoundIntent);
            }
        });

        Button messageB = (Button) findViewById(R.id.textButton);
        messageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getBaseContext(), MessagesCryptoActivity.class);
                startActivity(newIntent);
            }
        });

        Button videoB = (Button) findViewById(R.id.videoButton);
        videoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getBaseContext(), VideoActivity.class);
                startActivity(newIntent);
            }
        });

        Button pictureB = (Button) findViewById(R.id.pictureButton);
        pictureB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getBaseContext(), PictureActivity.class);
                startActivity(newIntent);
            }
        });

        Button solidB = (Button) findViewById(R.id.solidButton);
        solidB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Basic3D solid = new Basic3D();
//                solid.create();
                Intent newIntent = new Intent(getBaseContext(), MultimediaSolidActivity.class);
                startActivity(newIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
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