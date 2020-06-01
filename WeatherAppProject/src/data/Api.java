package data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Api {
	String serviceUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/";
	String serviceKey = "osaxVtVoyJX00Z9XB30%2BFesbOmRxdyLka5QzNgyDa3JvSGJde0GkbFcUQDiPqCEWnNgSvo8Gr1cAwiH2Nz8dVg%3D%3D";
	
	String dataVersion; // dataVersion : getUltraSrtNcst (초단기실황) / getUltraSrtFcst (초단기예보) / getVilageFcst (동네예보) 중 택1
	String baseDate=null;
	String baseTime=null;
	int nx=0;
	int ny=0;
	ArrayList<JSONObject> jsonObjects; // 받아온 jsonObject 리스트
	
	Thread thread;

	public Api(String dataVersion, String baseDate, String baseTime, int nx, int ny) throws Exception {
		this.dataVersion=dataVersion;
		this.baseDate=baseDate;
		this.baseTime=baseTime;
		this.nx=nx;
		this.ny=ny;
		
		thread=new Thread() {
			public void run() {
				try {
					dataLoad();
					System.out.println(jsonObjects);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

	public void dataLoad() throws Exception {
		
		jsonObjects=new ArrayList<JSONObject>();

		StringBuilder urlBuilder = new StringBuilder(serviceUrl);
		urlBuilder.append(dataVersion);
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("300", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(nx), "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(ny), "UTF-8"));
		
		URL url=new URL(urlBuilder.toString());
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		
		int responseCode=conn.getResponseCode();
		BufferedReader rd;
		if(responseCode>=200 && responseCode<=300) rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		else rd=new BufferedReader(new InputStreamReader(conn.getErrorStream()));

		StringBuilder resultBuilder=new StringBuilder();
		String line;
		while((line=rd.readLine())!=null) resultBuilder.append(line);
		rd.close();
		conn.disconnect();
		
		String result=resultBuilder.toString(); // 최종 json 전체 문자열
		
		JSONParser parser=new JSONParser();
		JSONObject obj=(JSONObject) parser.parse(result);
		JSONObject parse_response=(JSONObject)obj.get("response");
		JSONObject parse_body=(JSONObject)parse_response.get("body");
		JSONObject parse_items=(JSONObject)parse_body.get("items");
		JSONArray parse_item=(JSONArray)parse_items.get("item");
		
		for(int i=0;i<parse_item.size();i++) {
			JSONObject jsonObject=(JSONObject)parse_item.get(i);
			jsonObjects.add(jsonObject);
		}
	}

	public static void main(String[] args) throws Exception {
		new Api("getVilageFcst", "20200529", "0800", 60, 127);
	}
}