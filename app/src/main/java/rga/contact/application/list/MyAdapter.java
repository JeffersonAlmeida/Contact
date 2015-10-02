package rga.contact.application.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rga.contact.R;
import rga.contact.application.util.FileManager;
import rga.contact.util.BorderedCircleTransform;
import rga.contact.util.UIHelper;
import rga.contact.model.Contact;

public class MyAdapter extends ArrayAdapter<Contact>{

    private Context context;
    private List<Contact> contacts;

    public MyAdapter(Context context, List<Contact> contacts) {
        super(context, R.layout.list_contact_item, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if ( convertView != null ){
            viewHolder = (ViewHolder) convertView.getTag();
        }else {
            convertView = inflater.
                    inflate(R.layout.list_contact_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        Contact contact = this.contacts.get(position);
        configureFieldsWithContact(viewHolder, contact);
        return convertView;
    }

    private void configureFieldsWithContact(ViewHolder viewHolder, Contact contact) {
        setPhotoImage(contact, viewHolder.imageView);
        String suitableName = UIHelper.getSuitableName(contact.getName());
        viewHolder.name.setText(suitableName);
    }

    private void setPhotoImage(Contact contact, ImageView imageView) {
        String photoPath = contact.getPhotoPath();
        File file = FileManager.get().openFileForReading(photoPath);
        if ( contact.hasImage() ) {
            Picasso.with(this.context).load(file).
                    transform(new BorderedCircleTransform()).into(imageView);
        }else{
            TextDrawable textDrawable = UIHelper.getTextDrawable(contact.getName());
            imageView.setImageDrawable(textDrawable);
        }
    }


    static class ViewHolder{

        @Bind(R.id.image_view) protected ImageView imageView;
        @Bind(R.id.name) protected TextView name;

        public ViewHolder (View view){
            ButterKnife.bind(this, view);
        }
    }
}
