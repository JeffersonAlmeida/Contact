package rga.contact.database;

import java.util.List;
import rga.contact.model.Contact;

public interface ContactDao {
	
	public Contact load(Long id);
	
	public long insert(Contact contact);

	public int edit(Contact contact);
	
	public int remove(Contact contact);
	
	public List<Contact> list();

}
