package edu.weber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.weber.model.Address;
import edu.weber.model.Contact;

@WebServlet(name="RestServlet", urlPatterns="/rest")
public class RestServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req	, HttpServletResponse response) throws ServletException, IOException {
		
		Contact contact = new Contact(
				"Brady", 
				"Kurtz", 
				"111-111-1111", 
				new Address("123 main", "kaysville", "utah", "84037", "USA")
		);
		
		ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(contact));
        out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String body = getRequestBodyAsString(req);
		ObjectMapper mapper = new ObjectMapper();
		Contact contact = mapper.readValue(body, Contact.class);
		System.out.println("Debug Me");
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
