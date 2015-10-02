package rga.contact.application;

import android.app.Application;
import android.content.Context;

import rga.contact.application.util.FileManager;
import rga.contact.database.DataBase;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        FileManager.init(context);
        DataBase.getInstance();
    }

    public static Context getAppContext(){
        return MyApplication.context;
    }
}
