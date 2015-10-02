package rga.contact.application.edit;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

import rga.contact.R;
import rga.contact.database.ContactDao;
import rga.contact.database.DataBase;
import rga.contact.model.Contact;

public class EditContactActivity extends ActionBarActivity {

    private EditContactFragment editContactFragment;
    private Contact contact;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_edit_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle args = ( saved != null ) ? saved : getIntent().getExtras();
        this.contact = (Contact) args.getSerializable("contact");
        this.editContactFragment = buildFragment(saved);
    }

    private EditContactFragment buildFragment(Bundle saved) {
        return ( saved != null ) ? getSavedFragment(saved) : createFragment();
    }

    private EditContactFragment createFragment() {
        EditContactFragment frag = EditContactFragment.newInstance(contact);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, frag);
        fragmentTransaction.commit();
        return frag;
    }

    private EditContactFragment getSavedFragment(Bundle saved) {
        return (EditContactFragment)
                getFragmentManager().getFragment(saved, "fragment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_update:
                updateContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, "fragment", this.editContactFragment);
        super.onSaveInstanceState(outState);
    }

    private void showToast() {
        String message = getResources().getString(R.string.updated);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void updateContact() {
        updateContactDB();
        showToast();
        finishActivity();
    }

    private void finishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void updateContactDB() {
        ContactDao dao = DataBase.getInstance().getDao();
        this.contact = editContactFragment.getContactFromFields();
        dao.edit(this.contact);
    }
}