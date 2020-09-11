package edu.weber;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.weber.model.Address;
import edu.weber.model.Contact;

@WebServlet(name="MyFirstServlet", urlPatterns="/")
public class MyFirstServlet extends HttpServlet
{
	
	private static List<Contact> contacts = new ArrayList<Contact>();
	
	static {
		contacts.add(new Contact());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("contacts", getContacts());
		req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//GET CONTACT FROM FORM
		//VALIDATION
		String firstName = req.getParameter("fname");
		
		if(firstName == null) {
			req.setAttribute("error", "FirstName Is Empty");
		}
		
		contacts.add(new Contact());
		
		req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
	}
	
	
	protected List<Contact> getContacts() {
		return contacts;
	}
	
}