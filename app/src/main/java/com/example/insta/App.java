package com.example.insta;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("JyDZG0gqeM8HTtBnBtx0yX5qXBA32Gi356dgnBK8")
                // if defined
                .clientKey("jtDpj6okovy20ixTQZREzualiPnzH70YLt3CZVYU")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
