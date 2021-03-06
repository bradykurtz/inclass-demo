package edu.weber;

import java.io.IOException;

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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Contact contact = new Contact(
				"Brady", 
				"Kurtz", 
				"111-111-1111", 
				new Address("123 main", "kaysville", "utah", "84037", "USA")
		);
		
		req.setAttribute("contact", contact);
		req.getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
	}
	
}
