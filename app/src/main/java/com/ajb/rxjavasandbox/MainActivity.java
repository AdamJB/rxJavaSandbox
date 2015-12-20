package com.ajb.rxjavasandbox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

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

        setupJustObservable();
        setupCustomObservable();
    }

    public void setupJustObservable() {
        final Observable<String> justObservable = Observable.just("First Emission", "Second Emission");

        Button justButton = (Button) findViewById(R.id.just_observable_click);

        justButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                justObservable.subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        toast("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        toast("Error");
                    }

                    @Override
                    public void onNext(String s) {
                        toast("On Next Msg: " + s);
                    }
                });
            }
        });
    }

    public void setupCustomObservable() {
        final Observable<String> customObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String x = null;
                    subscriber.onNext("first custom data");
                    subscriber.onNext("second custom data");

                    // For exception: uncomment:
//                    x.toString();

                    subscriber.onCompleted();
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        });

        Button customButton = (Button) findViewById(R.id.custom_observable_clicked);

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customObservable.subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        toast("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        toast("Error");
                    }

                    @Override
                    public void onNext(String s) {
                        toast("On Next Msg: " + s);
                    }
                });
            }
        });
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

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }
}
