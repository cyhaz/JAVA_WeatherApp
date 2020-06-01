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

import lib.GetDate;
import lib.GetMath;

public class Api {
	String serviceUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/";
	String serviceKey = "osaxVtVoyJX00Z9XB30%2BFesbOmRxdyLka5QzNgyDa3JvSGJde0GkbFcUQDiPqCEWnNgSvo8Gr1cAwiH2Nz8dVg%3D%3D";
	
	String dataVersion; // dataVersion : getUltraSrtNcst (초단기실황) / getUltraSrtFcst (초단기예보) / getVilageFcst (동네예보) 중 택1
	String baseDate;
	String baseTime;
	int nx;
	int ny;
	String[] days= {};
	
	ArrayList<JSONObject> jsonObjects; // 받아온 jsonObject 리스트
	public Value value; // 오늘 날씨
	public Value v_2;	// 내일 날씨
	public Value v_3; // 모레 날씨

	public Api(String dataVersion, String baseDate, String baseTime, int nx, int ny) throws Exception {
		this.dataVersion=dataVersion;
		this.baseDate=baseDate;
		this.baseTime=baseTime;
		this.nx=nx;
		this.ny=ny;
		
		dataLoad();

		if(dataVersion.equals("getUltraSrtNcst")) getUN();
		else if(dataVersion.equals("getUltraSrtFcst")) getUF();
		else if(dataVersion.equals("getVilageFcst")) getVF();
	}
	
	// 초단기 실황 불러오기
	public void getUN() {
		value=new Value();		
		for(int i=0;i<jsonObjects.size();i++) {
			String category=jsonObjects.get(i).get("category").toString();
			String objVal=(String) jsonObjects.get(i).get("obsrValue");
			switch(category) {
				case "T1H" : value.setT1h((int)Double.parseDouble(objVal)); break;
				case "REH" : value.setReh(Integer.parseInt(objVal)); break;
				case "WSD" : value.setWsd(Double.parseDouble(objVal)); break;
			}
		}
	}
	
	// 초단기 예보 불러오기
	public void getUF() {
		value=new Value();
		for(int i=0;i<jsonObjects.size();i++) {
			String category=jsonObjects.get(i).get("category").toString();
			String objVal=(String) jsonObjects.get(i).get("fcstValue");
			switch(category) {
				case "PTY" : value.setPty(Integer.parseInt(objVal)); break;
				case "SKY" : value.setSky(Integer.parseInt(objVal)); break;
			}
		}
	}
	
	// 동네 예보 불러오기
	public void getVF() {
		value=new Value();
		v_2=new Value();
		v_3=new Value();
		
		int pop=0;
		int count=1;
		String tmx=null;
		String tmn=null;
		ArrayList<Integer> ptyList=new ArrayList<Integer>();
		ArrayList<Integer> skyList=new ArrayList<Integer>();
		
		for(int i=0;i<jsonObjects.size();i++) {
			String fcstDate=jsonObjects.get(i).get("fcstDate").toString();
			String category=jsonObjects.get(i).get("category").toString();
			String objVal=(String) jsonObjects.get(i).get("fcstValue");

			if(fcstDate.equals(baseDate)) {
				switch(category) {
					case "POP" : pop+=Integer.parseInt(objVal); count++; break;
					case "T3H" : 
						tmx=GetMath.getMaxValueToCompare(tmx, objVal);
						if(tmn!=null) tmn=GetMath.getMinValueToCompare(tmn, objVal);
						else tmn=objVal;
						break;
					case "TMX" : tmx=GetMath.getMaxValueToCompare(tmx, objVal); break;
					case "TMN" : tmn=GetMath.getMinValueToCompare(tmn, objVal); break;
				}
				value.setPop(pop/count);
				if(tmx!=null)value.setTmx((int)Double.parseDouble(tmx));
				if(tmn!=null)value.setTmn((int)Double.parseDouble(tmn));
			}
			
			// 내일 날짜면 내일거 v_2에 저장
			else if(fcstDate.equals(GetDate.getTomorrow_yyyyMMdd())) {
				switch(category) {
					case "POP" : pop+=Integer.parseInt(objVal); count++; break;
					case "T3H" : 
						tmx=GetMath.getMaxValueToCompare(tmx, objVal);
						if(tmn!=null) tmn=GetMath.getMinValueToCompare(tmn, objVal);
						else tmn=objVal;
						break;
					case "TMX" : tmx=GetMath.getMaxValueToCompare(tmx, objVal); break;
					case "TMN" : tmn=GetMath.getMinValueToCompare(tmn, objVal); break;
					case "PTY" : ptyList.add(Integer.parseInt(objVal)); break;
					case "SKY" : skyList.add(Integer.parseInt(objVal)); break;
				}
				v_2.setPop(pop/count);
				if(tmx!=null)v_2.setTmx((int)Double.parseDouble(tmx));
				if(tmn!=null)v_2.setTmn((int)Double.parseDouble(tmn));
				v_2.setPty(GetMath.getModeValue(ptyList));
				v_2.setSky(GetMath.getModeValue(skyList));
			}
			// 모레 날짜면 모레거 v_3에 저장
			else if(fcstDate.equals(GetDate.getAfterDay_yyyyMMdd())) {
				switch(category) {
					case "POP" : pop+=Integer.parseInt(objVal); count++; break;
					case "T3H" : 
						tmx=GetMath.getMaxValueToCompare(tmx, objVal);
						if(tmn!=null) tmn=GetMath.getMinValueToCompare(tmn, objVal);
						else tmn=objVal;
						break;
					case "TMX" : tmx=GetMath.getMaxValueToCompare(tmx, objVal); break;
					case "TMN" : tmn=GetMath.getMinValueToCompare(tmn, objVal); break;
					case "PTY" : ptyList.add(Integer.parseInt(objVal)); break;
					case "SKY" : skyList.add(Integer.parseInt(objVal)); break;
				}
				v_3.setPop(pop/count);
				if(tmx!=null)v_3.setTmx((int)Double.parseDouble(tmx));
				if(tmn!=null)v_3.setTmn((int)Double.parseDouble(tmn));
				v_3.setPty(GetMath.getModeValue(ptyList));
				v_3.setSky(GetMath.getModeValue(skyList));
			}
		}
	}

	// json parsing
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
		Thread thread;
		thread=new Thread() {
			@Override
			public void run() {
				try {
					new Api("getVilageFcst", "20200531", "0500", 60, 127);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		};
		thread.start();
	}
}