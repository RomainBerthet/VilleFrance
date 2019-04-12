package ville.utilisation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ville.bean.Ville;

/**
 * Servlet implementation class MeteoVilles
 */
@WebServlet("/InfosVilles")
public class InfosVilles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InfosVilles() {
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
		
		con.setRequestMethod("GET");

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
		RequestDispatcher dispat = request.getRequestDispatcher("infos.jsp");
		dispat.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String ville = request.getParameter("ville");
		Double lati = null;
		Double longi = null;
		try {
			lati = Double.parseDouble(ville.split("/")[0]);
			longi = Double.parseDouble(ville.split("/")[1]);
		} catch (NullPointerException e) {
			RequestDispatcher dispat = request.getRequestDispatcher("infos.jsp");
			dispat.forward(request, response);
		}

		String key = "0f6cb7d7f2e802a31dd94c0277ea3e89";

		String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lati + "&lon=" + longi
				+ "&units=metric&lang=fr&APPID=" + key;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		;

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response1 = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response1.append(inputLine);
		}
		in.close();

		Map<String, Object> allMap = map(response1.toString());
		Map<String, Object> wMap = null;

//		System.out.println("wMap: " + allMap.get("weather").toString().replace("[", "").replace("]", "")
//				.replace("=", ":").replace("id", "\"id\"").replace("main:", "\"main\":\"")
//				.replace(", description:", "\",\"description\":\"").replace(", icon", "\",\"icon\"").replace(", ", ",")
//				.split("}")[0].concat("}"));
		wMap = map(allMap.get("weather").toString().replace("[", "").replace("]", "").replace("=", ":")
				.replace("id", "\"id\"").replace("main:", "\"main\":\"")
				.replace(", description:", "\",\"description\":\"").replace(", icon", "\",\"icon\"").replace(", ", ",")
				.split("}")[0].concat("}"));

		Map<String, Object> mainMap = map(allMap.get("main").toString());

		request.setAttribute("w_main", wMap.get("main"));
		request.setAttribute("w_description", wMap.get("description"));
		request.setAttribute("w_icon", wMap.get("icon"));

		request.setAttribute("ville", ville.split("/")[2]);
		request.setAttribute("temp", mainMap.get("temp"));
		request.setAttribute("press", mainMap.get("pressure"));
		request.setAttribute("hum", mainMap.get("humidity"));
		request.setAttribute("min", mainMap.get("temp_min"));
		request.setAttribute("max", mainMap.get("temp_max"));
		request.setAttribute("lati", lati);
		request.setAttribute("longi", longi);
		request.setAttribute("villes", session.getAttribute("villes"));

		String codeINSEE = ville.split("/")[3];
		String population = "Inconnu";

		try {
			String url2 = "https://geo.api.gouv.fr/communes/" + codeINSEE + "?fields=population";
			URL obj2 = new URL(url2);
			HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();
			con2.setRequestMethod("GET");
			BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
			String inputLine2;
			StringBuffer response2 = new StringBuffer();

			while ((inputLine2 = in2.readLine()) != null) {
				response2.append(inputLine2);
			}
			in2.close();
			population = response2.toString().split(",")[0].replace("{\"population\":", "");
		} catch (Exception e) {

		}

		request.setAttribute("population", population);

		String url4 = "https://global.mapit.mysociety.org/point/4326/" + longi + "," + lati + "?type=O08";
		URL obj4 = new URL(url4);
		HttpURLConnection con4 = (HttpURLConnection) obj4.openConnection();
		con4.setRequestMethod("GET");
		BufferedReader in4 = new BufferedReader(new InputStreamReader(con4.getInputStream()));
		String inputLine4;
		StringBuffer response4 = new StringBuffer();

		while ((inputLine4 = in4.readLine()) != null) {
			response4.append(inputLine4);
		}
		in4.close();
		String sJson = "{" + response4.toString().substring(17, response4.toString().length() - 1);
		Map<String, Object> villeinfos = map(sJson);
		Map<String, Object> villeOSM = map(villeinfos.get("codes").toString());
		String codeOSMString = String.valueOf(villeOSM.get("osm_rel"));
		String code = codeOSMString.substring(0, codeOSMString.length() - 2);

		String url3 = "http://polygons.openstreetmap.fr/get_geojson.py?id=" + code;
		URL obj3 = new URL(url3);
		HttpURLConnection con3 = (HttpURLConnection) obj3.openConnection();
		con3.setRequestMethod("GET");
		BufferedReader in3 = new BufferedReader(new InputStreamReader(con3.getInputStream()));
		String inputLine3;
		StringBuffer response3 = new StringBuffer();

		while ((inputLine3 = in3.readLine()) != null) {
			response3.append(inputLine3);
		}
		in3.close();
		Map<String, Object> allGeo = map(response3.toString());
		Map<String, Object> map1 = map(allGeo.get("geometries").toString().replace("[{", "{").replace("}]", "}"));
		String coordinates = map1.get("coordinates").toString();
		request.setAttribute("poly", coordinates);

		RequestDispatcher dispat = request.getRequestDispatcher("infos.jsp");
		dispat.forward(request, response);

	}

	@SuppressWarnings("unused")
	private static Map<String, Object> map(String str) {
		Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {
		}.getType());
		return map;
	}

}
