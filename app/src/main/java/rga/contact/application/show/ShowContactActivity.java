package rga.contact.application.show;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import rga.contact.R;
import rga.contact.application.edit.EditContactActivity;
import rga.contact.database.ContactDao;
import rga.contact.database.DataBase;
import rga.contact.model.Contact;
import rga.contact.util.REQUEST;

public class ShowContactActivity extends ActionBarActivity {

    private ShowContactFragment showContactFragment;
    private Contact contact;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_show_contact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();
        contact = (Contact) extras.getSerializable("contact");
        setTitle(contact.getName());
        showContactFragment = buildFragment(saved);
    }

    private ShowContactFragment buildFragment(Bundle saved) {
        return ( saved != null ) ? getSavedFragment(saved) : createFragment();
    }

    private ShowContactFragment createFragment() {
        ShowContactFragment frag = ShowContactFragment.newInstance(contact);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, frag);
        fragmentTransaction.commit();
        return frag;
    }

    private ShowContactFragment getSavedFragment(Bundle saved) {
        return (ShowContactFragment)
                getFragmentManager().getFragment(saved, "fragment");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == REQUEST.EDIT_CONTACT ){
            if ( resultCode == RESULT_OK )
               update();
        }
    }

    private void update(){
        updateContact();
        updateFragment();
    }

    private void updateFragment() {
        showContactFragment.update(this.contact);
    }

    private void updateContact(){
        ContactDao dao = DataBase.getInstance().getDao();
        this.contact = dao.load(this.contact.getId());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getFragmentManager().putFragment(outState, "fragment", this.showContactFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_show_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edit:
                editContact();
                return true;
            case R.id.action_delete:
                deleteContact();
                return true;
            case R.id.action_favorite:
                makeContactFavorite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void makeContactFavorite() {
        Toast.makeText(this, "Todo", Toast.LENGTH_SHORT).show();
    }

    private void editContact() {
        startEditActivity(this.contact);
    }

    private void startEditActivity(Contact contact) {
        Intent intent = new Intent(this, EditContactActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("contact", contact);
        intent.putExtras(args);
        startActivityForResult(intent, REQUEST.EDIT_CONTACT);
    }

    private void deleteContact() {
        ContactDao dao = DataBase.getInstance().getDao();
        dao.remove(this.contact);
        finish();
        Toast.makeText(this, " Contact Deleted! ", Toast.LENGTH_SHORT).show();
    }

}
