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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		RequestDispatcher dispat = request.getRequestDispatcher("infos.jsp");
		dispat.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ville = request.getParameter("ville");
		Double lati = null;
		Double longi = null;
		try {
			lati = Double.parseDouble(ville.split("/")[0]);
			longi = Double.parseDouble(ville.split("/")[1]);
		}catch (NullPointerException e) {
			RequestDispatcher dispat = request.getRequestDispatcher("infos.jsp");
			dispat.forward(request, response);
		}
		
		String key="0f6cb7d7f2e802a31dd94c0277ea3e89";
		
		String url = "https://api.openweathermap.org/data/2.5/weather?lat="+lati+"&lon="+longi+"&units=metric&lang=fr&APPID="+key;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
//		con.setRequestProperty("User-Agent", USER_AGENT);

		//int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response1 = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response1.append(inputLine);
		}
		in.close();
		
		
		Map<String, Object> allMap = map(response1.toString());
		List<Map<String, Object>> weatherMap = null;
		Map<String, Object> wMap = null;
		try {
			weatherMap = new ArrayList<Map<String, Object>>();
			String[] wm = allMap.get("weather").toString().replace("[", "").replace("]", "").split("},");
			for(int i=0; i<wm.length; i++) {
				System.out.println("weatherMap: "+wm[i].toString().replace("=", ":").replace("id", "\"id\"").replace("main:", "\"main\":\"").replace(", description:", "\",\"description\":\"").replace(", icon", "\",\"icon\"").replace(", ", ","));
				weatherMap.add(map(wm[i].toString().replace("=", ":").replace("id", "\"id\"").replace("main:", "\"main\":\"").replace(", description:", "\",\"description\":\"").replace(", icon", "\",\"icon\"").replace(", ", ",")));
			}
		} catch(Exception e) {
			System.out.println("wMap: "+allMap.get("weather").toString().replace("[", "").replace("]", "").replace("=", ":").replace("id", "\"id\"").replace("main:", "\"main\":\"").replace(", description:", "\",\"description\":\"").replace(", icon", "\",\"icon\"").replace(", ", ","));
			wMap = map(allMap.get("weather").toString().replace("[", "").replace("]", "").replace("=", ":").replace("id", "\"id\"").replace("main:", "\"main\":\"").replace(", description:", "\",\"description\":\"").replace(", icon", "\",\"icon\"").replace(", ", ","));
		}
		
		Map<String, Object> mainMap = map(allMap.get("main").toString());
		
		if(weatherMap!=null) {
			request.setAttribute("w_main", weatherMap.get(0).get("main"));
			request.setAttribute("w_description", weatherMap.get(0).get("description"));
			request.setAttribute("w_icon", weatherMap.get(0).get("icon"));
			
		} else if(wMap!=null) {
		request.setAttribute("w_main", wMap.get("main"));
		request.setAttribute("w_description", wMap.get("description"));
		request.setAttribute("w_icon", wMap.get("icon"));
		}
//		
		request.setAttribute("ville", ville.split("/")[2]);
		request.setAttribute("temp", mainMap.get("temp"));
		request.setAttribute("press", mainMap.get("pressure"));
		request.setAttribute("hum", mainMap.get("humidity"));
		request.setAttribute("min", mainMap.get("temp_min"));
		request.setAttribute("max", mainMap.get("temp_max"));
		request.setAttribute("lati", lati);
		request.setAttribute("longi", longi);
		
		String codeINSEE = ville.split("/")[3];
		String population = "Inconnu";
		
		try {
			String url2 = "https://geo.api.gouv.fr/communes/"+codeINSEE+"?fields=population";
			URL obj2 = new URL(url2);
			HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();
			con2.setRequestMethod("GET");
			BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
			String inputLine2;
			StringBuffer response2 = new StringBuffer();

			while ((inputLine2 = in2.readLine()) != null) {
				response2.append(inputLine2);
			}
			in.close();
			population = response2.toString().split(",")[0].replace("{\"population\":", "");
		} catch(Exception e) {
			
		}
		
		request.setAttribute("population", population);
		
		RequestDispatcher dispat = request.getRequestDispatcher("infos.jsp");
		dispat.forward(request, response);
		
	}
	
	@SuppressWarnings("unused")
	private static Map<String, Object> map(String str){
		Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>(){}.getType());
		return map;
	}

}
