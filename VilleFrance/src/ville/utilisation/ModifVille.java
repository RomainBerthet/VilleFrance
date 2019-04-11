package ville.utilisation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


/**
 * Servlet implementation class ModifVille
 */
@WebServlet("/ModifVille")
public class ModifVille extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifVille() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("ListeVilles");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String insee = request.getParameter("insee");
		String nom = request.getParameter("nom");
		String cp = request.getParameter("cp");
		String lati = request.getParameter("lati");
		String longi = request.getParameter("longi");
		
		String url = "http://127.0.0.1:8181/post";

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("insee", insee));
		urlParameters.add(new BasicNameValuePair("nom", nom));
		urlParameters.add(new BasicNameValuePair("cp", cp));
		urlParameters.add(new BasicNameValuePair("lati", lati));
		urlParameters.add(new BasicNameValuePair("longi", longi));
		
		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		client.execute(post);
		//System.out.println("Response Code : " + response1.getStatusLine().getStatusCode());
		
		response.sendRedirect("ListeVilles");
	}

}
