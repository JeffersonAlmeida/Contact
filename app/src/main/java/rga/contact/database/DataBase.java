package rga.contact.database;

import android.content.Context;

import rga.contact.application.MyApplication;

public class DataBase  {

	private static final String DATABASE_NAME = "contactsdb";
	private static final int DATABASE_VERSION = 1;
	private ContactDao contactDao;
	private DataBaseOpenHelper helper;
	private static DataBase instance;

    private DataBase(){
        Context appContext = MyApplication.getAppContext();
        this.helper = new DataBaseOpenHelper
                (DATABASE_NAME, DATABASE_VERSION, appContext);
    }

	public static synchronized DataBase getInstance (){
		if ( instance == null )
			instance = new DataBase();
		return instance;
	}

	public void closeConnection(){
		this.helper.close();
	}
	
	public synchronized ContactDao getDao(){
		if ( contactDao == null )
			contactDao = new ContactDaoSqlite(helper);
		return contactDao;
	}

    public DataBaseOpenHelper getHelper() {
        return helper;
    }
}
