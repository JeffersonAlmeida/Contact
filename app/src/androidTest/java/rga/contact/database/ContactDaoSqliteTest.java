package rga.contact.database;

import android.provider.ContactsContract;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rga.contact.model.Contact;

public class ContactDaoSqliteTest extends AndroidTestCase {

    private static final String DATABASE_NAME = "testcontactsdb";
    private static final int DATABASE_VERSION = 1;

    private ContactDao contactDao;
    private DataBaseOpenHelper helper;

    @Before
    public void setUp() throws Exception {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        helper = new DataBaseOpenHelper(DATABASE_NAME,DATABASE_VERSION, context );
        contactDao = new ContactDaoSqlite(helper);
    }

    @After
    public void tearDown() throws Exception {
        helper.close();
    }

    @Test
    public void testLoad() throws Exception {
        Contact contact = new Contact.Builder().
                name("Jefferson").build();
        long id = contactDao.insert(contact);
        Contact contactLoaded = contactDao.load(id);
        assertNotNull(contactLoaded);
        assertEquals("Jefferson", contactLoaded.getName());
    }

    @Test
    public void testInsert() throws Exception {
        Contact contact = new Contact.Builder().
                name("Jefferson").build();
        long id = contactDao.insert(contact);
        assertNotSame(id, -1);
    }

    @Test
    public void testEdit() throws Exception {
        Contact contact = new Contact.Builder().
                name("Jefferson").build();
        long id = contactDao.insert(contact);

        contact.setId(id);
        contact.setName("Maria dos Santos");

        int affected = contactDao.edit(contact);
        assertEquals(1, affected);

        Contact contactUpdated = contactDao.load(id);

        assertEquals("Maria dos Santos", contactUpdated.getName());

    }

    @Test
    public void testRemove() throws Exception {

        Contact contact = new Contact.Builder().
                name("Jefferson").build();

        long id = contactDao.insert(contact);
        contact.setId(id);

        int affected = contactDao.remove(contact);
        assertEquals(1, affected);
    }

    @Test
    public void testList() throws Exception {
        Contact c1 = new Contact.Builder().
                name("Jefferson").build();

        Contact c2 = new Contact.Builder().
                name("Maria").build();

        contactDao.insert(c1);
        contactDao.insert(c2);

        List<Contact> list = contactDao.list();
        assertEquals(2, list.size());

        assertEquals("Jefferson", list.get(0).getName());
        assertEquals("Maria", list.get(1).getName());

    }
}