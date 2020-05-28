package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Weather {
	// 오늘 날짜 설정
	String baseDate=new SimpleDateFormat("yyyyMMdd").format(new Date());
	String baseTime="0500";		// 변경 가능
	
	ArrayList<String[]> list;
	
	public Weather(int nx, int ny) {		// 위도, 경도 필요
		try {
			list=getApi(nx, ny);				// list.get(0)=={fcstDate, fcstTime, category, fcstValue};
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Value select(String date, String time) {
		Value value=new Value();
		for(int i=0;i<list.size();i++) {
			if(list.get(i)[0].equals(date) && list.get(i)[1].equals(time)) {
				value.setFcstDate(date);
				value.setFcstTime(time);
				switch(list.get(i)[2]) {
					case "POP" : value.setPop(Integer.parseInt(list.get(i)[3])); break;
					case "PTY" : value.setPty(Integer.parseInt(list.get(i)[3])); break;
					case "REH" : value.setReh(Integer.parseInt(list.get(i)[3])); break;
					case "SKY" : value.setSky(Integer.parseInt(list.get(i)[3])); break;
					case "T3H" : value.setT3h(Integer.parseInt(list.get(i)[3])); break;
					case "TMN" : value.setTmn(Double.parseDouble(list.get(i)[3])); break;
					case "TMX" : value.setTmx(Double.parseDouble(list.get(i)[3])); break;
					case "WSD" : value.setWsd(Double.parseDouble(list.get(i)[3])); break;
				}
			}
		}
		return value;
	}

	public ArrayList<String[]> getApi(int nx, int ny) throws IOException, ParseException {
		//  serviceKey (by. 초연)
		String serviceKey="osaxVtVoyJX00Z9XB30%2BFesbOmRxdyLka5QzNgyDa3JvSGJde0GkbFcUQDiPqCEWnNgSvo8Gr1cAwiH2Nz8dVg%3D%3D";
		
		// url 설정
		StringBuilder urlBuilder=new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/");
		urlBuilder.append("getVilageFcst");		// getUltraSrtNcst / getUltraSrtFcst / getVilageFcst / getFcstVersion 중 택1
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /* 공공데이터포털에서 받은 인증키 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("300", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /* 요청자료형식(XML/JSON)Default: XML */
		urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 발표일 (오늘 날짜) */
		urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /* 08시 발표(정시단위) */
		urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(nx), "UTF-8")); /* 예보지점 X 좌표 */
		urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(ny), "UTF-8")); /* 예보지점 Y 좌표 */

		URL url=new URL(urlBuilder.toString());		// 위의 총합결과 url
//		System.out.println(url);
		
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		int code=conn.getResponseCode();					// 200
		
		BufferedReader rd;
		if(code>=200 && code<=300) rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		else rd=new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		
		StringBuilder sb=new StringBuilder();
		String line;
		while((line=rd.readLine())!=null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		
		//  최종 결과값
		String result=sb.toString();
		
		// JSON parser으로 문자열 데이터 객체화
		JSONParser parser=new JSONParser();
		JSONObject obj=(JSONObject) parser.parse(result);
		JSONObject parse_response=(JSONObject)obj.get("response");				// response키로 데이터 parsing
		JSONObject parse_body=(JSONObject)parse_response.get("body");		// response로부터 body 찾기
		JSONObject parse_items=(JSONObject)parse_body.get("items");			// body로부터 items 찾기
		JSONArray parse_item=(JSONArray)parse_items.get("item");				// items로부터  item list 받기
		
		ArrayList<String[]> list=new ArrayList<String[]>();
		
		for(int i=0;i<parse_item.size();i++) {
			JSONObject json=(JSONObject)parse_item.get(i);
			
			String[] json_list=new String[4];
			json_list[0]=json.get("fcstDate").toString();
			json_list[1]=json.get("fcstTime").toString();
			json_list[2]=json.get("category").toString();
			json_list[3]=json.get("fcstValue").toString();
			
			list.add(json_list);
		}
		return list;
	}

//	public static void main(String[] args) {
//		Weather seoul=new Weather(60, 127);
//		Value weather=seoul.select("20200527", "1500");
//		System.out.println(weather.getT3h()+"℃");
//	}
}