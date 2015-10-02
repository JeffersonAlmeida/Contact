package rga.contact.application.show;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import rga.contact.R;
import rga.contact.application.util.FileManager;
import rga.contact.util.BorderedCircleTransform;
import rga.contact.util.UIHelper;
import rga.contact.model.Contact;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShowContactFragment extends Fragment {

    @Bind(R.id.photo) protected ImageView photo;
    @Bind(R.id.born) protected TextView born;
    @Bind(R.id.email) protected EditText email;
    @Bind(R.id.about) protected TextView about;
    @Bind(R.id.bio) protected EditText bio;

    private Contact contact;

    public ShowContactFragment() {
    }

    public static ShowContactFragment newInstance(Contact contact){
        ShowContactFragment showContactFragment = new ShowContactFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact", contact);
        showContactFragment.setArguments(bundle);
        return showContactFragment;
    }

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        Bundle args = ( saved != null ) ? saved : getArguments();
        contact = (Contact) args.getSerializable("contact");
    }

    @Override
    public View onCreateView
            (LayoutInflater inflater, ViewGroup root, Bundle saved) {
        View layout = inflater.inflate(R.layout.fragment_show_contact, root, false);
        ButterKnife.bind(this, layout);
        configureContactFields();
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("contact", this.contact);
        super.onSaveInstanceState(outState);
    }

    private void configureContactFields() {
        makeFieldsNotEditable();
        setFieldsWithContactValues();
    }

    private void setFieldsWithContactValues() {
        email.setText(contact.getEmail());
        born.setText(contact.getBorn());
        bio.setText(contact.getBio());
        about.setText("About " + contact.getName());
        setPhotoImage();
    }

    private void makeFieldsNotEditable() {
        email.setEnabled(false);
        bio.setEnabled(false);
        born.setEnabled(false);
    }

    private void setPhotoImage() {
        if ( contact.hasImage() ) {
            String photoPath = contact.getPhotoPath();
            File file = FileManager.get().openFileForReading(photoPath);
            Picasso.with(getActivity()).load(file).
                    transform(new BorderedCircleTransform()).into(photo);
        }else{
            TextDrawable textDrawable = UIHelper.getTextDrawable(contact.getName());
            photo.setImageDrawable(textDrawable);
        }
    }

    public void update(Contact contact) {
        this.contact = contact;
        configureContactFields();
    }
}
