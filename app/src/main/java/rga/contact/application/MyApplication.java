package rga.contact.application;

import android.app.Application;
import android.content.Context;

import rga.contact.application.util.FileManager;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        FileManager.init(context);
    }

    public static Context getAppContext(){
        return MyApplication.context;
    }
}
