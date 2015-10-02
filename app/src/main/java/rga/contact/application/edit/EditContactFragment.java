package rga.contact.application.edit;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;

import rga.contact.application.util.FileManager;
import rga.contact.util.*;
import static rga.contact.util.REQUEST.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rga.contact.R;
import rga.contact.util.UIHelper;
import rga.contact.model.Contact;

import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditContactFragment extends Fragment {

    private static final String ARG_CONTACT = "contact";
    private Contact contact;

    @Bind(R.id.name) protected EditText name;
    @Bind(R.id.email) protected EditText email;
    @Bind(R.id.bio) protected EditText bio;
    @Bind(R.id.born) protected TextView born;
    @Bind(R.id.photo) protected ImageView photo;
    @Bind(R.id.about) protected TextView about;

    private String photoPath;

    public EditContactFragment() {
    }

    public static EditContactFragment newInstance(Contact contact){
        EditContactFragment editContactFragment = new EditContactFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT, contact);
        editContactFragment.setArguments(args);
        return editContactFragment;
    }

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        Bundle args = ( saved != null ) ? saved : getArguments();
        contact = (Contact) args.getSerializable(ARG_CONTACT);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        this.contact = getContactFromFields();
        outState.putSerializable(ARG_CONTACT, this.contact);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView
            (LayoutInflater inflater, ViewGroup root, Bundle saved) {

        View layout = inflater.
        inflate(R.layout.layout_fragment_edit_contact, root, false);
        ButterKnife.bind(this, layout);
        configureFieldsWithContact();
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void configureFieldsWithContact() {
        name.setText(contact.getName());
        email.setText(contact.getEmail());
        bio.setText(contact.getBio());
        born.setText(contact.getBorn());
        about.setText("About " + contact.getName());
        photoPath = contact.getPhotoPath();
        setPhotoImage();
    }

    private void setPhotoImage() {
        String photoPath = contact.getPhotoPath();
        File file = FileManager.get().openFileForReading(photoPath);
        if ( contact.hasImage() ) {
            Picasso.with(getActivity()).load(file).
                    transform(new BorderedCircleTransform()).into(photo);
        }else{
            TextDrawable textDrawable = UIHelper.getTextDrawable(contact.getName());
            photo.setImageDrawable(textDrawable);
        }
    }

    @OnClick(R.id.photo)
    public void onClickPhoto(){
        startActivityPhotoPicker();
    }

    private void startActivityPhotoPicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        if (resultCode == RESULT_OK && requestCode == CHOOSE_PICTURE) {
            try {
                uri = data.getData();
                InputStream stream = getActivity().
                        getContentResolver().openInputStream(data.getData());
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setImageWithPicasso(uri);
        }
    }

    private void setImageWithPicasso(Uri uri) {
        this.photoPath = UriPathSeeker.getPath(getActivity(), uri);
        File photo = new File(this.photoPath);
        Picasso.with(getActivity()).load(photo).
                transform(new BorderedCircleTransform()).into(this.photo);
    }

    public Contact getContactFromFields() {
        String name = this.name.getText().toString();
        String email = this.email.getText().toString();
        String bio = this.bio.getText().toString();
        String born = this.born.getText().toString();

        Contact contactFromFields = new Contact.Builder().
                id(this.contact.getId()).name(name).
                email(email).bio(bio).born(born).photoPath(this.photoPath).build();
        return contactFromFields;
    }

    @OnClick(R.id.born)
    public void bornFieldClicked(){
        showDatePickerDialog();
    }

    public void showDatePickerDialog(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public DatePickerFragment(){}

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the stringBuilder chosen by the user
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(day).append("/").append(month).append("/").append(year);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String stringMonth = "";
            try {
                Date date = dateFormat.parse(stringBuilder.toString());
                stringMonth = (String) DateFormat.format("MMMM", date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String result = stringMonth + " " + day;
            udpdateBornDate(result);
        }
    }

    private void udpdateBornDate(String date) {
       born.setText(date);
    }
}
