package com.somitsolutions.android.example.statepatterninservice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static MainActivity mMainActivity;
    private static final int CONNECTING = 1;
    private static final int CONNECTED = 2;
    private static final int DOWNLOADSTARTED = 3;
    private static final int DOWNLOADFINISHED = 4;

    Button startButton;
    private MessageHandler handler;


    public Messenger mMessenger = new Messenger(new MessageHandler(this));

    @Override
    public void onClick(View v) {
        Intent serv = new Intent(MainActivity.this, LongRunningService.class);
        startService(serv);
    }

    private class MessageHandler extends Handler {
        private Context c;

        MessageHandler(Context c) {
            this.c = c;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONNECTING:
                    Toast.makeText(getApplicationContext(), "Connecting", Toast.LENGTH_LONG).show();
                    break;
                case CONNECTED:
                    Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
                    break;
                case DOWNLOADSTARTED:
                    Toast.makeText(getApplicationContext(), "Download Started", Toast.LENGTH_LONG).show();
                    break;
                case DOWNLOADFINISHED:
                    Toast.makeText(getApplicationContext(), "Download Finished", Toast.LENGTH_LONG).show();
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });


            startButton = (Button) findViewById(R.id.button1);

            startButton.setOnClickListener(this);

            mMainActivity = this;
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

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

        public static MainActivity getMainActivity(){
            return mMainActivity;
        }
    }

