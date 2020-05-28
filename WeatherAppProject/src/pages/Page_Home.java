package pages;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import data.Value;
import data.Weather;
import designed_object.ImageLabel;
import designed_object.TextLabel;

public class Page_Home extends Page {
	JPanel p_today; // 좌측 컨테이너 (오늘 날씨)
	Choice ch_loc; // 지역 선택 객체
	JLabel la_date; // 현재 날짜
	JLabel la_weatherText; // 어제와의 날씨 비교 (변경?)
	JLabel la_weatherIcon; // 현재 날씨 아이콘
	JLabel la_mainTemp; // 현재 기온
	JLabel la_highLowTemp; // 최고 / 최저 기온
	JLabel la_rainPer; // 비 올 확률
	JLabel la_reh; // 습도
	JLabel la_wsd; // 풍속

	JPanel p_weekly; // 우측 상단 컨테이너 (주간 날씨)
	JPanel p_w_1; // 내일 날씨 패널
	JLabel la_w1Day; // 내일 요일
	JLabel la_w1Icon; // 내일 날씨 아이콘
	JLabel la_w1MainTemp; // 내일 평균 기온
	JLabel la_w1HLTemp; // 내일 최고 / 최저 기온

	JPanel p_w_2; // 모레 날씨 패널
	JLabel la_w2Day; // 모레 요일
	JLabel la_w2Icon; // 모레 날씨 아이콘
	JLabel la_w2MainTemp; // 모레 평균 기온
	JLabel la_w2HLTemp; // 모레 최고 / 최저 기온

	JPanel p_subBox; // 우측 하단 컨테이너 (미세먼지)

	// 기타
	Date d = new Date();
	String todayText = new SimpleDateFormat("M월 dd일").format(d); // 5월 25일
	String searchDate = new SimpleDateFormat("yyyyMMdd").format(d); // 20200525
	
	String[] days= {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Satday"};

	String dir = "C:/naver/NaverCloud/Another/YH Development/java_workspace/TeamProject/src/res/icon/";
	String[] weatherImgPaths = { dir + "0-1.png", dir + "0-3.png", dir + "0-4.png", dir + "1-1.png", dir + "1-3.png",
			dir + "1-4.png", dir + "2-1.png", dir + "2-3.png", dir + "2-4.png", dir + "3-1.png", dir + "3-3.png",
			dir + "3-4.png", dir + "4-1.png", dir + "4-3.png", dir + "4-4.png" };

	String[] location = { "강원도", "경기도", "경상남도", "경상북도", "광주광역시", "대구광역시", "대전광역시", "부산광역시", "서울특별시", "세종특별자치시", "울산광역시",
			"인천광역시", "전라남도", "전라북도", "제주도", "충청남도", "충청북도" };

	int today = Integer.parseInt(searchDate);
	String[] date_list = { Integer.toString(today), Integer.toString(today + 1), Integer.toString(today + 2) };
	String[] time_list = { "0600", "0900", "1200", "1500", "1800", "2100", "0000", "0300" };
	
	// 요일 String
	Calendar calendar=Calendar.getInstance();
	int thisDay=calendar.get(Calendar.DAY_OF_WEEK)-1;
	String day1, day2;
	
	// 생성자
	public Page_Home(MainDriver mainDriver, String title, String bg_path, boolean showFlag) {
		super(mainDriver, title, bg_path, showFlag);
		
		// 요일
		if(thisDay==5) {
			day1=days[6];
			day2=days[0];
		} else if(thisDay==6) {
			day1=days[0];
			day2=days[1];
		} else {
			day1=days[thisDay+1];
			day2=days[thisDay+2];
		}

		// weather api
		Weather w = new Weather(60, 127);
		Value v_today = w.select(date_list[0], "1800");
		double today_tmn=w.select(date_list[0], "2100").getT3h();
		double today_tmx=w.select(date_list[0], "1500").getTmx();
		Value v_day1=w.select(date_list[1], "1800");
		double day1_tmn=w.select(date_list[1], "0600").getTmn();
		double day1_tmx=w.select(date_list[1], "1500").getTmx();
		Value v_day2=w.select(date_list[2], "1800");
		double day2_tmn=w.select(date_list[2], "0600").getTmn();
		double day2_tmx=w.select(date_list[2], "1500").getTmx();

		// on memory
		p_today = new JPanel(); // 좌측 컨테이너
		ch_loc = new Choice(); // 지역 선택
		la_date = new TextLabel(todayText, 350, 50, 40); // 5월 24일
		la_weatherText = new TextLabel("어제와의 날씨 비교", 200, 30, 15); // 어제보다 추워요
		la_weatherIcon = new ImageLabel(weatherImgPaths[0], 250, 250); // 현재 날씨에 해당하는 이미지 아이콘
		la_mainTemp = new TextLabel(v_today.getT3h() + " ℃", 350, 50, 30); // 현재 기온
		la_highLowTemp = new TextLabel(String.format("%.1f℃ / %.1f℃", today_tmx, today_tmn), 350, 20, 15); // 최고기온 / 최저기온
		la_rainPer = new TextLabel("비올 확률 " + v_today.getPop()+"%", 350, 80, 20); // 비 올 확률 0%
		la_reh = new TextLabel("습도 "+v_today.getReh()+"%", 175, 20, 15); // 습도 0%
		la_wsd = new TextLabel("풍속 "+v_today.getWsd()+"m/s", 175, 20, 15); // 풍속 0m/s

		p_weekly = new JPanel(null); // 우측 상단 컨테이너 (주간 날씨)
		p_w_1 = new JPanel(); // 내일 날씨 패널
		la_w1Day = new TextLabel(day1, 240, 60, 23); // 내일 요일
		la_w1Icon = new ImageLabel(weatherImgPaths[1], 110, 110); // 내일 날씨 아이콘
		la_w1MainTemp = new TextLabel(v_day1.getT3h() + " ℃", 240, 30, 20); // 내일 평균 기온
		la_w1HLTemp = new TextLabel(String.format("%.1f℃ / %.1f℃", day1_tmx, day1_tmn), 240, 30, 15); // 내일 최고 / 최저 기온

		p_w_2 = new JPanel(); // 모레 날씨 패널
		la_w2Day = new TextLabel(day2, 240, 60, 23); // 모레 요일
		la_w2Icon = new ImageLabel(weatherImgPaths[2], 110, 110); // 모레 날씨 아이콘
		la_w2MainTemp = new TextLabel(v_day2.getT3h() + " ℃", 240, 30, 20); // 모레 평균 기온
		la_w2HLTemp = new TextLabel(String.format("%.1f℃ / %.1f℃", day2_tmx, day2_tmn), 240, 30, 15); // 모레 최고 / 최저 기온

		p_subBox = new JPanel(); // 우측 하단 컨테이너 (미세 먼지)

		// 3 main panel style
		setPanelStyle(p_today, 20, 20, 400, 650);
		setPanelStyle(p_weekly, 440, 20, 540, 315);
		setPanelStyle(p_w_1, 20, 20, 240, 275);
		setPanelStyle(p_w_2, 275, 20, 240, 275);
		setPanelStyle(p_subBox, 440, 355, 540, 315);

		// add choice location
		ch_loc.setPreferredSize(new Dimension(350, 70));
		for (int i = 0; i < location.length; i++) {
			ch_loc.add(location[i]);
		}

		// add
		p_today.add(ch_loc);
		p_today.add(la_date);
		p_today.add(la_weatherText);
		p_today.add(la_weatherIcon);
		p_today.add(la_mainTemp);
		p_today.add(la_highLowTemp);
		p_today.add(la_rainPer);
		p_today.add(la_reh);
		p_today.add(la_wsd);

		p_w_1.add(la_w1Day);
		p_w_1.add(la_w1Icon);
		p_w_1.add(la_w1MainTemp);
		p_w_1.add(la_w1HLTemp);
		p_w_2.add(la_w2Day);
		p_w_2.add(la_w2Icon);
		p_w_2.add(la_w2MainTemp);
		p_w_2.add(la_w2HLTemp);
		p_weekly.add(p_w_1);
		p_weekly.add(p_w_2);

		// 페이지 패널이 가진 라벨에 붙이기 (메인 컨테이너 3개)
		this.label.add(p_today);
		this.label.add(p_weekly);
		this.label.add(p_subBox);
	}

	// 큰 투명 패널 스타일 적용 메서드
	public void setPanelStyle(JPanel panel, int x, int y, int width, int height) {
		panel.setBounds(x, y, width, height);
		panel.setBackground(new Color(255, 255, 255, 100));
		Border border = BorderFactory.createRaisedBevelBorder();
		panel.setBorder(border);
	}
}