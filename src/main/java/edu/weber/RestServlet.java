package edu.weber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.weber.model.Address;
import edu.weber.model.Contact;
import edu.weber.service.ContactService;

@WebServlet(name="RestServlet", urlPatterns="/rest")
public class RestServlet extends HttpServlet {
	
	private ObjectMapper mapper;
	private ContactService contactService;
	
	public RestServlet() {
		this.mapper = new ObjectMapper();
		this.contactService = new ContactService();
	}

	@Override
	protected void doGet(HttpServletRequest req	, HttpServletResponse response) throws ServletException, IOException {
		
		List<Contact> contacts = contactService.getContacts();
		
		ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(contacts));
        out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String body = getRequestBodyAsString(req);
		Contact requestContact = mapper.readValue(body, Contact.class);
		Contact dbContact = contactService.recordContact(requestContact);
		writeObjectToJsonResponse(dbContact, resp);
	}
	
	private void writeObjectToJsonResponse(Object obj, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {
			out.print(mapper.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        out.flush();
	}
	
    private String getRequestBodyAsString(HttpServletRequest request) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            bodyBuilder.append(line);
        }

        return bodyBuilder.toString();
    }
	
	
	
}
