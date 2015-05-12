package com.xiaolu.myrxjavaexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.just("Hello, world!")
                // Func1<T1, R> T1 represents the input type, R represents the return type
                // here T1 is String - "Hello Word", R - Integer
                .map(new Func1<String, Integer>() {
                    @Override
                    // Same here, return type is Integer which is the same as R in Func1<>
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer s) {
                        Log.w(TAG, Integer.toString(s));
                    }
                });
    }
}