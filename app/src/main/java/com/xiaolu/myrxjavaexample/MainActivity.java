package com.xiaolu.myrxjavaexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );

        /**
         * When the subscription is made, myObservable calls the subscriber's onNext() and
         * onComplete() methods. As a result, mySubscriber outputs "Hello, world!" then terminates.
         */
        myObservable.subscribe(mySubscriber);
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
}
