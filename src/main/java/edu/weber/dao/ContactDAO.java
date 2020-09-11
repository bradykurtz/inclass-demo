package edu.weber.dao;
import java.util.*;

import edu.weber.model.Address;
import edu.weber.model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {
	
	private Connection db;
	
	 private static final String CONTACT_INSERT = "INSERT INTO CONTACT("
		        + "FIRST_NAME,"
		        + "LAST_NAME,"
		        + "PHONE_NUMBER,"
		        + "ID"
		        + ") VALUES("
		        + "?,"
		        + "?,"
		        + "?,"
		        + "?"
		        + ")";

		    private static final String CONTACT_UPDATE = "UPDATE CONTACT "
		        + "SET FIRST_NAME = ?, "
		        + "LAST_NAME = ?, "
		        + "PHONE_NUMBER = ? "
		        + "WHERE ID = ?";

		    private static final String ADDRESS_INSERT = "INSERT INTO ADDRESS("
		        + "ID,"
		        + "CONTACT_ID,"
		        + "ADDRESS_TYPE,"
		        + "ADDRESS_LINE_1,"
		        + "ADDRESS_LINE_2,"
		        + "CITY,"
		        + "STATE,"
		        + "POSTAL_CODE"
		        + ") VALUES("
		        + "?,"
		        + "?,"
		        + "?,"
		        + "?,"
		        + "?,"
		        + "?,"
		        + "?,"
		        + "?"
		        + ")";

	
	public ContactDAO () {
		try {
			db = DatabaseConnection.getDataSource().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Contact recordContact(Contact contact) {
		 try {
	            if(contact.getId() != null) {
	                PreparedStatement contactStatement = db.prepareStatement(CONTACT_UPDATE);
	                contactStatement.setString(4, contact.getId());
	                contactStatement.setString(1, contact.getFirstName());
	                contactStatement.setString(2, contact.getLastName());
	                contactStatement.setString(3, contact.getPhoneNumber());
	                contactStatement.executeUpdate();
	                contactStatement.close();
	            } else {
	                contact.setId(UUID.randomUUID().toString());
	                PreparedStatement contactStatement = db.prepareStatement(CONTACT_INSERT);
	                contactStatement.setString(4, contact.getId());
	                contactStatement.setString(1, contact.getFirstName());
	                contactStatement.setString(2, contact.getLastName());
	                contactStatement.setString(3, contact.getPhoneNumber());
	                contactStatement.executeUpdate();
	                contactStatement.close();
	            }

	            PreparedStatement addressStatement =db.prepareStatement(ADDRESS_INSERT);
	            for(int i = 0; i < contact.getAddresses().size(); i++) {
	                Address currentAddress = contact.getAddresses().get(i);
	                String addressId = UUID.randomUUID().toString();
	                addressStatement.setString(1, addressId);
	                addressStatement.setString(2, contact.getId());
	                addressStatement.setString(3, currentAddress.getType());
	                addressStatement.setString(4, currentAddress.getAddressLine1());
	                addressStatement.setString(5, currentAddress.getAddressLine2());
	                addressStatement.setString(6, currentAddress.getCity());
	                addressStatement.setString(7, currentAddress.getState());
	                addressStatement.setString(8, currentAddress.getPostalCode());

	                currentAddress.setId(addressId);

	                addressStatement.addBatch();

	                if(i % 1000 == 0 || i == contact.getAddresses().size()) {
	                    addressStatement.executeBatch();
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error Inserting Contact");
	        }

	        return contact;
	}
	
	public List<Contact> getContacts() {
		 Map<String, Contact> contactMap = new HashMap<>();
		String sql = "SELECT "
		        + "c.ID as contactId,"
		        + "c.FIRST_NAME as firstName,"
		        + "c.LAST_NAME as lastName,"
		        + "c.PHONE_NUMBER as phoneNumber,"
		        + "a.ID as addressId,"
		        + "a.ADDRESS_TYPE as addressType,"
		        + "a.ADDRESS_LINE_1 as addressLine1,"
		        + "a.ADDRESS_LINE_2 as addressLine2,"
		        + "a.CITY as city,"
		        + "a.STATE as state,"
		        + "a.POSTAL_CODE as postalCode "
		        + "FROM CONTACT c "
		        + "INNER JOIN ADDRESS a ON c.ID = a.CONTACT_ID "
		        + "WHERE 1=1";
		try {
			PreparedStatement contactStatement = db.prepareStatement(sql);
			ResultSet rs = contactStatement.executeQuery();
			 while (rs.next()) {
	                String contactId = rs.getString("contactId");
	                Contact contact = contactMap.get(contactId);
	                if(contact != null) {
	                    contact.addAddress(getAddressFromResultSet(rs));
	                } else {
	                    contact = new Contact();
	                    contact.setId(contactId);
	                    contact.setFirstName(rs.getString("firstName"));
	                    contact.setLastName(rs.getString("lastName"));
	                    contact.setPhoneNumber(rs.getString("phoneNumber"));
	                    contact.addAddress(getAddressFromResultSet(rs));
	                    contactMap.put(contactId, contact);
	                }
	            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Contact>(contactMap.values());
		
	}
	
    private Address getAddressFromResultSet(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getString("addressId"));
        address.setType(rs.getString("addressType"));
        address.setAddressLine1(rs.getString("addressLine1"));
        address.setAddressLine2(rs.getString("addressLine2"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setPostalCode(rs.getString("postalCode"));
        return address;
    }

	
	public Contact getContactByFirstName(String firstName) {
		Contact response = null;

		return response;
	}

}
