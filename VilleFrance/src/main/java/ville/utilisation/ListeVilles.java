package ville.utilisation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ville.bean.Ville;

/**
 * Servlet implementation class ListeVilles
 */
@WebServlet("/ListeVilles")
public class ListeVilles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListeVilles() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = "http://127.0.0.1:8181/get";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
//			con.setRequestProperty("User-Agent", USER_AGENT);

		// int responseCode = con.getResponseCode();
//			System.out.println("\nSending 'GET' request to URL : " + url);
//			System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response1 = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response1.append(inputLine);
		}
		in.close();

		List<Ville> villes = new ArrayList<Ville>();
		String str = response1.toString().replace("}]", "");
		String[] respS = str.split("},");
		for (int i = 0; i < respS.length; i++) {
			String[] villepart = respS[i].split(",");
			Ville ville = new Ville(villepart[0].split(":")[1].replace("\"", ""),
					villepart[1].split(":")[1].replace("\"", ""), villepart[2].split(":")[1].replace("\"", ""),
					Double.parseDouble(villepart[3].split(":")[1]), Double.parseDouble(villepart[4].split(":")[1]));
			villes.add(ville);
		}
		session.setAttribute("villes", villes);

		RequestDispatcher dispat = request.getRequestDispatcher("listeVilles.jsp");
		dispat.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String insee = request.getParameter("insee");
		String nom = request.getParameter("nom");
		String cp = request.getParameter("cp");
		String lati = request.getParameter("lati");
		String longi = request.getParameter("longi");

		if (insee == null && nom == null && cp == null && lati == null && longi == null) {
			insee = "";
			nom = "";
			cp = "";
			lati = "";
			longi = "";
			request.setAttribute("insee", insee);
			request.setAttribute("nom", nom);
			request.setAttribute("cp", cp);
			request.setAttribute("lati", lati);
			request.setAttribute("longi", longi);

			RequestDispatcher dispat = request.getRequestDispatcher("ajoutVille.jsp");
			dispat.forward(request, response);
		}
		request.setAttribute("insee", insee);
		request.setAttribute("nom", nom);
		request.setAttribute("cp", cp);
		request.setAttribute("lati", lati);
		request.setAttribute("longi", longi);

		RequestDispatcher dispat = request.getRequestDispatcher("modifVille.jsp");
		dispat.forward(request, response);

	}

}
