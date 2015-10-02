package rga.contact.application.list;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import rga.contact.R;
import rga.contact.application.create.CreateActivity;
import rga.contact.application.edit.EditContactActivity;
import rga.contact.application.show.ShowContactActivity;
import rga.contact.database.AndroidDatabaseManager;
import rga.contact.database.ContactDao;
import rga.contact.database.DataBase;
import rga.contact.json.FromJson;
import rga.contact.json.JsonDownloader;
import rga.contact.model.Contact;
import rga.contact.util.REQUEST;

public class ContactsListActivity
extends ActionBarActivity implements ContactListFragment.OnClickContactListener,
        JsonDownloader.OnTaskCompleted,
        ContactListFragment.OnClickFloatingButtonListener {

    private FloatingActionButton fab;

    private SharedPreferences prefs;
    private ContactListFragment contactFragment;
    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);

        prefs = getSharedPreferences("rga.contact.Contact", MODE_PRIVATE);
        if ( prefs.getBoolean("firstrun", true) )
            getContactsFromJson();
        else
            getContactsFromDatabase();

    }


    private void getContactsFromDatabase() {
        ContactDao dao = DataBase.getInstance().getDao();
        this.contacts = dao.list();
        createFragment(this.contacts);
    }

    private void getContactsFromJson() {
        JsonDownloader downloader = new JsonDownloader(this, this);
        downloader.execute();
        prefs.edit().putBoolean("firstrun", false).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_create:
                createContact();
                return true;
            case R.id.action_Database:
                showDatabase();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDatabase() {
        Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }

    private void createContact() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST.EDIT_CONTACT ){
            if ( resultCode == RESULT_OK )
                this.contactFragment.updatedListView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( this.contactFragment != null )
            this.contactFragment.updatedListView();
    }

    private void createFragment(List<Contact> contacts) {
        this.contactFragment = ContactListFragment.newInstance(contacts);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, contactFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onJsonDownloaded(String json) {
        convertJson(json);
    }

    private void convertJson(String json){
        this.contacts = FromJson.toContacts(json);
        insertContactsInDatabase(this.contacts);
        createFragment(this.contacts);
    }

    private void insertContactsInDatabase(List<Contact> contacts) {
        ContactDao dao = DataBase.getInstance().getDao();
        for (int i = 0; i < contacts.size(); i++){
            Contact contact = contacts.get(i);
            contact.setId(Long.valueOf(i+1));
            dao.insert(contact);
        }
    }

    @Override
    public void onClickContact(Contact contact) {
        startShowContactActivity(contact);
    }

    private void startEditActivity(Contact contact) {
        Intent intent = new Intent(this, EditContactActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("contact", contact);
        intent.putExtras(args);
        startActivityForResult(intent, REQUEST.EDIT_CONTACT);
    }

    private void startShowContactActivity(Contact contact) {
        Intent intent = new Intent(this, ShowContactActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("contact", contact);
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public void onClickFloatingButton() {
        createContact();
    }
}
