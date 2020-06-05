package data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import lib.GetDate;
import lib.GetMath;

public class Api {
	String serviceUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/";
	String serviceKey = "osaxVtVoyJX00Z9XB30%2BFesbOmRxdyLka5QzNgyDa3JvSGJde0GkbFcUQDiPqCEWnNgSvo8Gr1cAwiH2Nz8dVg%3D%3D";
	
	String d_serviceUrl="http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureLIst";
	String d_serviceKey="8kL6j5npaYfEumELz%2FZOSev5k7Q2fITc0%2BbY8lATUjNzDgxyjALsK9kDn0TBs%2BNK6IfviiDBlUgIxNrAB1YvyQ%3D%3D";
	
	String dataVersion; // dataVersion : getUltraSrtNcst (초단기실황) / getUltraSrtFcst (초단기예보) / getVilageFcst (동네예보) 중 택1
	String baseDate;
	String baseTime;
	int nx;
	int ny;
	
	ArrayList<JSONObject> jsonObjects; // 받아온 jsonObject 리스트
	public Value value; // 오늘 날씨
	public Value v_2;	// 내일 날씨
	public Value v_3; // 모레 날씨
	public Dust dust; // 오늘의 지역별 미세먼지 농도

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
			else if(fcstDate.equals(GetDate.date_tomorrow)) {
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
			else if(fcstDate.equals(GetDate.date_afterDay)) {
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
	
	// 미세먼지 api load
	public ArrayList<Integer> d_dataLoad() throws Exception {
		StringBuilder urlBuilder = new StringBuilder(d_serviceUrl);
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + d_serviceKey);
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("searchCondition", "UTF-8") + "=" + URLEncoder.encode("WEEK", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("dataGubun", "UTF-8") + "=" + URLEncoder.encode("DAILY", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("itemCode", "UTF-8") + "=" + URLEncoder.encode("PM10", "UTF-8"));
		
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
		
		// xml document 생성
		InputSource is = new InputSource(new StringReader(resultBuilder.toString()));
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		XPath xpath = XPathFactory.newInstance().newXPath();

		// NodeList 가져오기
		dust=new Dust();
		NodeList seoul = (NodeList) xpath.evaluate("/response/body/items/item/seoul", document, XPathConstants.NODESET);
		dust.setSeoul(Integer.parseInt(seoul.item(0).getTextContent()));
		NodeList busan = (NodeList) xpath.evaluate("/response/body/items/item/busan", document, XPathConstants.NODESET);
		dust.setBusan(Integer.parseInt(busan.item(0).getTextContent()));
		NodeList daegu = (NodeList) xpath.evaluate("/response/body/items/item/daegu", document, XPathConstants.NODESET);
		dust.setDaegu(Integer.parseInt(daegu.item(0).getTextContent()));
		NodeList incheon = (NodeList) xpath.evaluate("/response/body/items/item/incheon", document, XPathConstants.NODESET);
		dust.setIncheon(Integer.parseInt(incheon.item(0).getTextContent()));
		NodeList gwangju = (NodeList) xpath.evaluate("/response/body/items/item/gwangju", document, XPathConstants.NODESET);
		dust.setGwangju(Integer.parseInt(gwangju.item(0).getTextContent()));
		NodeList daejeon = (NodeList) xpath.evaluate("/response/body/items/item/daejeon", document, XPathConstants.NODESET);
		dust.setDaejeon(Integer.parseInt(daejeon.item(0).getTextContent()));
		NodeList ulsan = (NodeList) xpath.evaluate("/response/body/items/item/ulsan", document, XPathConstants.NODESET);
		dust.setUlsan(Integer.parseInt(ulsan.item(0).getTextContent()));
		NodeList gyeonggi = (NodeList) xpath.evaluate("/response/body/items/item/gyeonggi", document, XPathConstants.NODESET);
		dust.setGyeonggi(Integer.parseInt(gyeonggi.item(0).getTextContent()));
		NodeList gangwon = (NodeList) xpath.evaluate("/response/body/items/item/gangwon", document, XPathConstants.NODESET);
		dust.setGangwon(Integer.parseInt(gangwon.item(0).getTextContent()));
		NodeList chungbuk = (NodeList) xpath.evaluate("/response/body/items/item/chungbuk", document, XPathConstants.NODESET);
		dust.setChungbuk(Integer.parseInt(chungbuk.item(0).getTextContent()));
		NodeList chungnam = (NodeList) xpath.evaluate("/response/body/items/item/chungnam", document, XPathConstants.NODESET);
		dust.setChungnam(Integer.parseInt(chungnam.item(0).getTextContent()));
		NodeList jeonbuk = (NodeList) xpath.evaluate("/response/body/items/item/jeonbuk", document, XPathConstants.NODESET);
		dust.setJeonbuk(Integer.parseInt(jeonbuk.item(0).getTextContent()));
		NodeList jeonnam = (NodeList) xpath.evaluate("/response/body/items/item/jeonnam", document, XPathConstants.NODESET);
		dust.setJeonnam(Integer.parseInt(jeonnam.item(0).getTextContent()));
		NodeList gyeongbuk = (NodeList) xpath.evaluate("/response/body/items/item/gyeongbuk", document, XPathConstants.NODESET);
		dust.setGyeongbuk(Integer.parseInt(gyeongbuk.item(0).getTextContent()));
		NodeList gyeongnam = (NodeList) xpath.evaluate("/response/body/items/item/gyeongnam", document, XPathConstants.NODESET);
		dust.setGyeongnam(Integer.parseInt(gyeongnam.item(0).getTextContent()));
		NodeList jeju = (NodeList) xpath.evaluate("/response/body/items/item/jeju", document, XPathConstants.NODESET);
		dust.setJeju(Integer.parseInt(jeju.item(0).getTextContent()));
		NodeList sejong = (NodeList) xpath.evaluate("/response/body/items/item/sejong", document, XPathConstants.NODESET);
		dust.setSejong(Integer.parseInt(sejong.item(0).getTextContent()));
		
		ArrayList<Integer> localValue = new ArrayList<Integer>();
		localValue.add(dust.getGangwon());
		localValue.add(dust.getGyeonggi());
		localValue.add(dust.getGyeongnam());
		localValue.add(dust.getGyeongbuk());
		localValue.add(dust.getGwangju());
		localValue.add(dust.getDaegu());
		localValue.add(dust.getDaejeon());
		localValue.add(dust.getBusan());
		localValue.add(dust.getSeoul());
		localValue.add(dust.getSejong());
		localValue.add(dust.getUlsan());
		localValue.add(dust.getIncheon());
		localValue.add(dust.getJeonnam());
		localValue.add(dust.getJeonbuk());
		localValue.add(dust.getJeju());
		localValue.add(dust.getChungnam());
		localValue.add(dust.getChungbuk());
		
		return localValue;
	}
	
//	public static void main(String[] args) throws Exception {
//		Api api=new Api("getUltraSrtNcst", "20200605", "0500", 60, 127);
//		System.out.println(api.d_dataLoad());
//	}
}