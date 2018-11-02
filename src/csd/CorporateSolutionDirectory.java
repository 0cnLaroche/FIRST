package csd;
import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class CorporateSolutionDirectory {
	
	/*Quelques URL
	 * LISTE DES COLLECTIONS
	 * http://dialogue/proj/ITCD-RMTI/APM-GPA/_vti_bin/listData.svc
	 * 
	 * http://dialogue/proj/ITCD-RMTI/APM-GPA/_vti_bin/listData.svc/$metadata
	 * 
	 * http://dialogue/proj/ITCD-RMTI/APM-GPA/_vti_bin/listData.svc/CAD_Solutions(3)
	 */
	
	private HttpURLConnection conn;
	final String site = "http://dialogue/proj/ITCD-RMTI/APM-GPA/_vti_bin/listData.svc";
	final String charset = java.nio.charset.StandardCharsets.UTF_8.name();	
	
	
	public CorporateSolutionDirectory() {
		
	}

	public Solution getSolution(int id) throws IOException {
		
		JSONObject obj = (JSONObject) requestJSON("/CAD_Solutions?$filter=Id%20eq%20" + id + "&$select=Solution");
		JSONArray arr = (JSONArray) obj.get("results");
		JSONObject res = (JSONObject) arr.get(0);
		
		Solution s = new Solution();
		s.setId(id);
		s.setName(res.get("Solution").toString());
		
		return s;

	}
	public Object requestJSON(String query) throws IOException {

		JSONObject obj = new JSONObject();;
		
			try {
				conn = (HttpURLConnection) new URL(site + query).openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.connect();
				
			    if (conn.getResponseCode() != 200) {
			         throw new IOException(conn.getResponseCode() + " - " +  conn.getResponseMessage());
			       }
			   
			    JSONParser jsonParser = new JSONParser();
			    JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(conn.getInputStream()));
			    
			    obj = (JSONObject)jsonObject.get("d");
				
			    System.out.println(obj);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			return obj;
	}
	public void openInBrowser(int id) {
		
		if (Desktop.isDesktopSupported()) {
			
			try {
				URI uri = new URI("http://dialogue/proj/ITCD-RMTI/APM-GPA/Site%20Pages%20Library/CSD_SolutionView.aspx?SolutionID=" + id);
				Desktop.getDesktop().browse(uri);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		

		
	}
}

