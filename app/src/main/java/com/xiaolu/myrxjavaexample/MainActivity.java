package com.xiaolu.myrxjavaexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ArrayList<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * First article
         */
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
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer i) {
                        return i.toString();
                    }
                })
                // When susbscribe, we can see the return type is String
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.w(TAG, s);
                    }
                });

        /**
         * Second article
         */
        urls.add("url1");
        urls.add("url2");
        urls.add("url3");

        query("Hello, world!")
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> urls) {
                        return Observable.from(urls);
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String url) {
                        return getTitle(url);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.w(TAG, s);
                    }
                });
    }

    // This method mimic an API call
    Observable<ArrayList<String>> query(String s) {
        // just() can create an Observable from any object AS IT IS
        return Observable.just(urls);
    }

    // This method mimic an API call
    Observable<String> getTitle(String url) {
        String result;
        if (url.equals("url3"))
            result = "Do or die";
        else if (url.equals("url1"))
            result = "2 Fast 2 Furious";
        else
            result = "Fast 7";
        return Observable.just(result);
    }
}