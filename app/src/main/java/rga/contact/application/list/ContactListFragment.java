package rga.contact.application.list;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import rga.contact.R;
import rga.contact.database.ContactDao;
import rga.contact.database.DataBase;
import rga.contact.model.Contact;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnClickContactListener}
 * interface.
 */
public class ContactListFragment extends Fragment implements AbsListView.OnItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "contacts";

    private List<Contact> contacts;

    private OnClickContactListener mListener;

    private OnClickFloatingButtonListener floatingButtonListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private MyAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static ContactListFragment newInstance(List<Contact> contacts) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) contacts);
        fragment.setArguments(args);
        return fragment;
    }

    public void updatedListView(){
        this.contacts.clear();
        ContactDao dao = DataBase.getInstance().getDao();
        this.contacts.addAll(dao.list());
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contacts = (List<Contact>) getArguments().getSerializable(ARG_PARAM1);
        mAdapter = new MyAdapter(getActivity(), contacts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(FloatingButtonClickListener());

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    private View.OnClickListener FloatingButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingButtonListener.onClickFloatingButton();
            }
        };
    }

    public interface OnClickFloatingButtonListener {
        public void onClickFloatingButton();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnClickContactListener) activity;
            floatingButtonListener = (OnClickFloatingButtonListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnClickContactListener & OnClickFloatingButtonListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onClickContact(this.contacts.get(position));
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnClickContactListener {
        public void onClickContact(Contact contact);
    }
}
