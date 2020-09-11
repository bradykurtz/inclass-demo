package edu.weber;

import static org.junit.Assert.assertTrue;
import java.util.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import edu.weber.dao.ContactDAO;
import edu.weber.model.Address;
import edu.weber.model.Contact;
import edu.weber.service.ContactService;
import junit.framework.Assert;

import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
/**
 * Unit test for simple App.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppTest 
{
	
    @Test
    public void shouldReturnContactWithFirstNameOfBrady()
    {
    	ContactDAO contactDao = Mockito.mock(ContactDAO.class);
    	Mockito.when(contactDao.getContactByFirstName(ArgumentMatchers.anyString())).thenReturn(createContact());
    	
    	ContactService contactService = new ContactService();
    	contactService.setContactDao(contactDao);
    	Contact contact = contactService.getContactByFirstName("asdfas");
        assertTrue( "Test".equalsIgnoreCase(contact.getFirstName()) );
    }
    
    @Test
    public void testMyServlet() {
    	MyFirstServlet firstServlet = new MyFirstServlet();
    	List<Contact> contacts = firstServlet.getContacts();
    	Assert.assertTrue(contacts.size() == 1);
    }
    
    @Test
    public void testThatPostAddsContact() throws ServletException, IOException {
    	MyFirstServlet firstServlet = new MyFirstServlet();
    	   	
    	HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class);
    	RequestDispatcher  mockedDispatcher = Mockito.mock(RequestDispatcher.class);
    	
    	Mockito.when(mockedRequest.getParameter("fname")).thenReturn(null);
    	Mockito.when(mockedRequest.getRequestDispatcher(ArgumentMatchers.anyString())).thenReturn(mockedDispatcher);
    	
    	HttpServletResponse  mockedResponse = Mockito.mock(HttpServletResponse.class);
    	ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    	
    	firstServlet.doPost(mockedRequest, mockedResponse);
    	Mockito.verify(mockedRequest).setAttribute(Mockito.eq("error"), captor.capture());
    	String errorValue = captor.getValue();
    	
    	Assert.assertEquals("FirstName Is Empty", errorValue);
    }
    
    private Contact createContact() {
    	List<Address> addresses = new ArrayList<Address>() {{
    		add(new Address("123 main", "kaysville", "utah", "84037", "USA"));
    	}};
    	return new Contact(
				"Test", 
				"Test", 
				"111-111-1111", 
				addresses);
    }
}
