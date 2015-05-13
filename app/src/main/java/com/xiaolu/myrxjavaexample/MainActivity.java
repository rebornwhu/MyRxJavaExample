package com.xiaolu.myrxjavaexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ArrayList<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // article1Example();
        // article2Example();
        article3Example();
    }

    private void article1Example() {
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
    }

    private void article2Example() {
        /**
         * Second article
         */
        urls.add("url1");
        urls.add("url2");
        urls.add("url3");
        urls.add("url4");
        urls.add("url5");
        urls.add("url6");

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
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s != null;
                    }
                })
                .take(3)
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.w(TAG, s + " is saved");
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.w(TAG, s);
                    }
                });
    }

    private void article3Example() {
        Observable.just("Hello, world!")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        /*throw new NullPointerException("This is exception 1");*/
                        return s + " mutation 1 ";
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        throw new NullPointerException("This is exception 2");
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.w(TAG, "task finish");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
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
        else if (url.equals("url2"))
            result = "Harry Potter";
        else if (url.equals("url5"))
            result = "5 Monkeys";
        else if (url.equals("url4"))
            result = "Fantastic 4";
        else
            result = null;
        return Observable.just(result);
    }
}