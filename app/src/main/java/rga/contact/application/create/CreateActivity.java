package rga.contact.application.create;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import rga.contact.R;
import rga.contact.application.edit.EditContactFragment;
import rga.contact.database.ContactDao;
import rga.contact.database.DataBase;
import rga.contact.model.Contact;

public class CreateActivity extends ActionBarActivity {

    private EditContactFragment editContactFragment;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editContactFragment = buildFragment(saved);
    }

    private EditContactFragment buildFragment(Bundle saved) {
        return ( saved != null ) ? getSavedFragment(saved) : createFragment();
    }

    private EditContactFragment createFragment() {
        EditContactFragment frag = EditContactFragment.newInstance(Contact.newNull());
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
    protected void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, "fragment", this.editContactFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_create:
                createContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createContact() {
        Contact contact = this.editContactFragment.getContactFromFields();
        insertContactDB(contact);
        notifyUser();
        finish();
    }

    private void notifyUser() {
        Toast.makeText(this, "Contact Saved!", Toast.LENGTH_SHORT).show();
    }

    private void insertContactDB(Contact contact) {
        ContactDao dao = DataBase.getInstance().getDao();
        dao.insert(contact);
    }
}
