package com.xiaolu.myrxjavaexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> myObservable = Observable.just("Hello, world!");

        /**
         * When the subscription is made, myObservable calls the subscriber's onNext() and
         * onComplete() methods. As a result, mySubscriber outputs "Hello, world!" then terminates.
         */
        myObservable.subscribe(onNextAction);
    }

    Subscriber<String> mySubscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.w(TAG, s);
        }

        @Override
        public void onCompleted() {
            Log.w(TAG, "Task completed");
        }

        @Override
        public void onError(Throwable e) { }
    };

    Action1<String> onNextAction = new Action1<String>() {
        @Override
        public void call(String s) {
            Log.w(TAG, s);
        }
    };
}
