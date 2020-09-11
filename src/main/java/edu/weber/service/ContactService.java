package edu.weber.service;
import java.util.*;

import edu.weber.dao.ContactDAO;
import edu.weber.model.Address;
import edu.weber.model.Contact;

public class ContactService {

	private ContactDAO contactDao = null;
	
	public ContactService() {
		this.contactDao = new ContactDAO();
	}
	
	public List<Contact> getContacts() {
		return contactDao.getContacts();
	}
	
	public Contact recordContact(Contact contact) {
		return contactDao.recordContact(contact);
	}
	
	public Contact getContactByFirstName(String firstName) {
		
		
		return contactDao.getContactByFirstName(firstName);
	}

	public ContactDAO getContactDao() {
		return contactDao;
	}

	public void setContactDao(ContactDAO contactDao) {
		this.contactDao = contactDao;
	}
	
	
	
}
