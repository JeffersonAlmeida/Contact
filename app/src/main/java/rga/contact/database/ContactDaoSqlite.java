package rga.contact.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rga.contact.model.Contact;

public class ContactDaoSqlite implements ContactDao {

    private static String TAG = "DATABASE";


    private DataBaseOpenHelper helper;
    public static final String TABLE_CONTACT = "contact";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_BORN = "born";
    public static final String COLUMN_BIO = "bio";
    public static final String COLUMN_PHOTO = "photoPath";


    public static final String CREATE_TABLE_CONTACT =
            "CREATE TABLE "
                    + TABLE_CONTACT + "("
                    + COLUMN_ID  + " integer primary key autoincrement, "
                    + COLUMN_NAME + " text, "
                    + COLUMN_EMAIL	+ " text, "
                    + COLUMN_BORN + " text, "
                    + COLUMN_BIO + " text, "
                    + COLUMN_PHOTO	+ " text );";

    public ContactDaoSqlite(DataBaseOpenHelper helper) {
        this.helper = helper;
    }

    public DataBaseOpenHelper getHelper() {
        return helper;
    }

    @Override
    public Contact load(Long id) {
        return ( id != null ) ? searchContact(id) : null;
    }

    private Contact searchContact(Long id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = makeQuery(id);
        return buildContact(cursor);
    }

    private Cursor makeQuery(Long id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACT, null,
                COLUMN_ID + " = " + id, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    @Override
    public long insert(Contact contact) {
        long id = -1;
        SQLiteDatabase db = this.helper.getWritableDatabase();
        // It's a good idea to wrap our insert in a transaction.
        // This helps with performance and ensures consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, contact.getId());
            values.put(COLUMN_NAME, contact.getName());
            values.put(COLUMN_EMAIL, contact.getEmail());
            values.put(COLUMN_BORN, contact.getBorn());
            values.put(COLUMN_BIO, contact.getBio());
            values.put(COLUMN_PHOTO, contact.getPhotoPath());
            id = db.insertOrThrow(TABLE_CONTACT, null, values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(TAG, "Error while trying to add contact to database");
        }finally {
            db.endTransaction();
        }
        return id;
    }

    @Override
    public int edit(Contact contact) {
        int affected = 0;
        SQLiteDatabase db = this.helper.getWritableDatabase();
        if ( contact!=null && contact.getId() != null && contact.getId() > 0 ) {
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                values.put(COLUMN_ID, contact.getId());
                values.put(COLUMN_NAME, contact.getName());
                values.put(COLUMN_EMAIL, contact.getEmail());
                values.put(COLUMN_BORN, contact.getBorn());
                values.put(COLUMN_BIO, contact.getBio());
                values.put(COLUMN_PHOTO, contact.getPhotoPath());
                affected = db.update(TABLE_CONTACT, values, COLUMN_ID + "=" + contact.getId(), null);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.d(TAG, "Error while trying to update contact");
            } finally {
                db.endTransaction();
            }
        }
        return affected;
    }

    @Override
    public int remove(Contact contact) {
        int affected = 0;
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try{
            affected = db.delete(TABLE_CONTACT, COLUMN_ID + "=" + contact.getId(), null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(TAG, "Error while trying to delete all contacts");
        }finally {
            db.endTransaction();
        }
        return affected;
    }

    @Override
    public List<Contact> list() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACT, null, null, null, null, null, null);
        return buildContactsList(cursor);
    }

    private List<Contact> buildContactsList(Cursor cursor) {
        List<Contact> contacts = new ArrayList<Contact>();
        if ( cursor != null && cursor.moveToFirst() ){
            do {
                contacts.add(buildContact(cursor));
            }while( cursor.moveToNext() );
        }
        ((ArrayList<Contact>) contacts).trimToSize();
        return contacts;
    }

    private Contact buildContact(Cursor c) {
        if ( c != null ){
            Long id = c.getLong(c.getColumnIndex(COLUMN_ID));
            String name = c.getString(c.getColumnIndex(COLUMN_NAME));
            String email = c.getString(c.getColumnIndex(COLUMN_EMAIL));
            String born = c.getString(c.getColumnIndex(COLUMN_BORN));
            String bio = c.getString(c.getColumnIndex(COLUMN_BIO));
            String photo = c.getString(c.getColumnIndex(COLUMN_PHOTO));
            return new Contact.Builder().id(id).name(name).email(email).
                    born(born).bio(bio).photoPath(photo).build();
        }
        return null;
    }
}