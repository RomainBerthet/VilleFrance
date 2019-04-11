package ville.utilisation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
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
 * Servlet implementation class VilleUtilisation
 */
@WebServlet("/VilleUtilisation")
public class VilleUtilisation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VilleUtilisation() {
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

			//int responseCode = con.getResponseCode();
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
				Ville ville = new Ville(villepart[0].split(":")[1].replace("\"", ""), villepart[1].split(":")[1].replace("\"", ""),
						villepart[2].split(":")[1].replace("\"", ""), Double.parseDouble(villepart[3].split(":")[1]),
						Double.parseDouble(villepart[4].split(":")[1]));
				villes.add(ville);
			}
			session.setAttribute("villes", villes);
		
		RequestDispatcher dispat = request.getRequestDispatcher("distance.jsp");
		dispat.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ville1 = request.getParameter("ville1");
		String ville2 = request.getParameter("ville2");
		System.out.println(ville1);
		
		try {
			Double latiVille1 = Double.parseDouble(ville1.split("/")[0]);
			Double longiVille1 = Double.parseDouble(ville1.split("/")[1]);
			Double latiVille2 = Double.parseDouble(ville2.split("/")[0]);
			Double longiVille2 = Double.parseDouble(ville2.split("/")[1]);
			
			String distance = distance(latiVille1, latiVille2, longiVille1, longiVille2);
			
			System.out.println(distance);
			
			request.setAttribute("distance", distance);
			request.setAttribute("ville1", ville1.split("/")[2]);
			request.setAttribute("ville2", ville2.split("/")[2]);
			
			RequestDispatcher dispat = request.getRequestDispatcher("distance.jsp");
			dispat.forward(request, response);
		} catch (NullPointerException e) {
			RequestDispatcher dispat = request.getRequestDispatcher("distance.jsp");
			dispat.forward(request, response);
		}
	}
	
	@SuppressWarnings("unused")
	private static String distance(double lat1, double lat2, double lon1,
	        double lon2) {

	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c; 
	    DecimalFormat dec = new DecimalFormat("#0.00");
	    String dis = dec.format(distance);
	    return dis;
	}

}
