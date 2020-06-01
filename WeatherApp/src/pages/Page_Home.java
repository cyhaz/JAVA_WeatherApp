package pages;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Api;
import designed_object.ImageLabel;
import designed_object.TextLabel;
import lib.GetDate;
import lib.SetStyle;
import main.MainDrive;

public class Page_Home extends Page {
	JPanel p_today; // 좌측 컨테이너 (오늘 날씨)
	Choice ch_loc; // 지역 선택 객체
	JLabel la_date; // 현재 날짜
	JLabel la_weatherText; // 어제와의 날씨 비교 (변경?)
	ImageLabel la_weatherIcon; // 현재 날씨 아이콘
	JLabel la_mainTemp; // 현재 기온
	JLabel la_highLowTemp; // 최고 / 최저 기온
	JLabel la_rainPer; // 비 올 확률
	JLabel la_reh; // 습도
	JLabel la_wsd; // 풍속

	JPanel p_weekly; // 우측 상단 컨테이너 (주간 날씨)
	JPanel p_w_1; // 내일 날씨 패널
	JLabel la_w1Day; // 내일 요일
	ImageLabel la_w1Icon; // 내일 날씨 아이콘
	JLabel la_w1Pop; // 내일 비 올 확률
	JLabel la_w1HLTemp; // 내일 최고 / 최저 기온

	JPanel p_w_2; // 모레 날씨 패널
	JLabel la_w2Day; // 모레 요일
	ImageLabel la_w2Icon; // 모레 날씨 아이콘
	JLabel la_w2Pop; // 모레 비 올 확률
	JLabel la_w2HLTemp; // 모레 최고 / 최저 기온

	JPanel p_subBox; // 우측 하단 컨테이너 (미세먼지)
	
	String iconDir = MainDrive.resDir + "./weatherIcon/";
	String[] weatherImgPaths = { iconDir + "0-1.png", iconDir + "0-3.png", iconDir + "0-4.png", iconDir + "1-1.png",
			iconDir + "1-3.png", iconDir + "1-4.png", iconDir + "2-1.png", iconDir + "2-3.png", iconDir + "2-4.png",
			iconDir + "3-1.png", iconDir + "3-3.png", iconDir + "3-4.png", iconDir + "4-1.png", iconDir + "4-3.png",
			iconDir + "4-4.png" };
	String[] location = { "강원도", "경기도", "경상남도", "경상북도", "광주광역시", "대구광역시", "대전광역시", "부산광역시", "서울특별시", "세종특별자치시", "울산광역시",
			"인천광역시", "전라남도", "전라북도", "제주도", "충청남도", "충청북도" };
	int[][] nxny = { { 73, 134 }, { 60, 120 }, { 91, 77 }, { 89, 91 }, { 58, 74 }, { 89, 90 }, { 67, 100 }, { 98, 76 },
			{ 60, 127 }, { 66, 103 }, { 102, 84 }, { 55, 124 }, { 51, 67 }, { 63, 89 }, { 52, 38 }, { 68, 100 },
			{ 69, 107 } };

	String baseDate = GetDate.getToday_yyyyMMdd();
	String baseTime = GetDate.getBefor1hBaseTime();
	String yesterday = GetDate.getYesterday_yyyyMMdd();
	String yesterdayBaseTime = GetDate.getAfter1hBaseTime();
	String compareToYesterday = null;

	int nx, ny;

	Api UN_yesterday=null;
	Api UN=null;
	Api UF=null;
	Api VF=null;
	
	int v1Icon, v2Icon, v3Icon;

	public Page_Home(MainDrive main, String title, String bgPath, boolean showFlag) throws Exception {
		super(main, title, bgPath, showFlag);
		
		getApi("서울특별시");
		setAnother();

		// on memory
		p_today = new JPanel(); // 좌측 컨테이너
		ch_loc = new Choice(); // 지역 선택
		la_date = new TextLabel(GetDate.getTodayText(), 350, 50, 40); // 5월 24일
		la_weatherText = new TextLabel(compareToYesterday, 200, 30, 15); // 어제보다 추워요
		la_weatherIcon = new ImageLabel(weatherImgPaths[v1Icon], 250, 250); // 현재 날씨에 해당하는 이미지 아이콘
		la_mainTemp = new TextLabel(UN.value.getT1h() + " ℃", 350, 50, 30); // 현재 기온
		la_highLowTemp = new TextLabel(String.format("%d ℃ / %d ℃", VF.value.getTmx(), VF.value.getTmn()), 350, 20, 15);
		la_rainPer = new TextLabel("비 올 확률 " + VF.value.getPop() + "%", 350, 80, 20); // 비 올 확률 0%
		la_reh = new TextLabel("습도 " + UN.value.getReh() + "%", 175, 20, 15); // 습도 0%
		la_wsd = new TextLabel("풍속 " + UN.value.getWsd() + "m/s", 175, 20, 15); // 풍속 0m/s

		p_weekly = new JPanel(null); // 우측 상단 컨테이너 (주간 날씨)
		p_w_1 = new JPanel(); // 내일 날씨 패널
		la_w1Day = new TextLabel(GetDate.getDay(0), 240, 60, 23); // 내일 요일
		la_w1Icon = new ImageLabel(weatherImgPaths[v2Icon], 110, 110); // 내일 날씨 아이콘
		la_w1HLTemp = new TextLabel(String.format("%d ℃ / %d ℃", VF.v_2.getTmx(), VF.v_2.getTmn()), 240, 30, 20);
		la_w1Pop = new TextLabel("비 올 확률 " + VF.v_2.getPop() + "%", 240, 30, 15); // 비 올 확률 0%

		p_w_2 = new JPanel(); // 모레 날씨 패널
		la_w2Day = new TextLabel(GetDate.getDay(1), 240, 60, 23); // 모레 요일
		la_w2Icon = new ImageLabel(weatherImgPaths[v3Icon], 110, 110); // 모레 날씨 아이콘
		la_w2HLTemp = new TextLabel(String.format("%d ℃ / %d ℃", VF.v_3.getTmx(), VF.v_3.getTmn()), 240, 30, 20);
		la_w2Pop = new TextLabel("비 올 확률 " + VF.v_3.getPop() + "%", 240, 30, 15); // 비 올 확률 0%

		p_subBox = new JPanel(); // 우측 하단 컨테이너 (미세 먼지)

		// 3 main panel style
		SetStyle.setPanelStyle(p_today, 20, 20, 400, 650);
		SetStyle.setPanelStyle(p_weekly, 440, 20, 540, 315);
		SetStyle.setPanelStyle(p_w_1, 20, 20, 240, 275);
		SetStyle.setPanelStyle(p_w_2, 275, 20, 240, 275);
		SetStyle.setPanelStyle(p_subBox, 440, 355, 540, 315);

		// add choice location
		ch_loc.setPreferredSize(new Dimension(350, 70));
		for (int i = 0; i < location.length; i++) {
			ch_loc.add(location[i]);
		}
		ch_loc.select("서울특별시");

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
		p_w_1.add(la_w1HLTemp);
		p_w_1.add(la_w1Pop);
		p_w_2.add(la_w2Day);
		p_w_2.add(la_w2Icon);
		p_w_2.add(la_w2HLTemp);
		p_w_2.add(la_w2Pop);
		p_weekly.add(p_w_1);
		p_weekly.add(p_w_2);

		// 페이지 패널이 가진 라벨에 붙이기 (메인 컨테이너 3개)
		this.label.add(p_today);
		this.label.add(p_weekly);
		this.label.add(p_subBox);

		ch_loc.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				getApi(e.getItem().toString());
				setAnother();
				resetLabel();
			}
		});
	}
	
	public void getApi(String space) {
		for (int i = 0; i < location.length; i++) {
			if (location[i].equals(space)) {
				nx = nxny[i][0];
				ny = nxny[i][1];
				try {
					if (Integer.parseInt(yesterdayBaseTime)<100) yesterday=baseDate;

					UN_yesterday = new Api("getUltraSrtNcst", yesterday, yesterdayBaseTime, nx, ny);
					UN = new Api("getUltraSrtNcst", baseDate, baseTime, nx, ny);
					UF = new Api("getUltraSrtFcst", baseDate, baseTime, nx, ny);
					VF = new Api("getVilageFcst", baseDate, "0500", nx, ny);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	// 날씨아이콘 세팅 및 compareToYesterday 세팅
	public void setAnother() {
		v1Icon=getIconNum(VF.value.getPty(), VF.value.getSky());
		v2Icon=getIconNum(VF.v_2.getPty(), VF.v_2.getSky());
		v3Icon=getIconNum(VF.v_3.getPty(), VF.v_3.getSky());
		
		if (UN_yesterday.value.getT1h() < UN.value.getT1h())
			compareToYesterday = "어제보다 더워요";
		else if (UN_yesterday.value.getT1h() == UN.value.getT1h())
			compareToYesterday = "어제랑 비슷해요";
		else if (UN_yesterday.value.getT1h() > UN.value.getT1h())
			compareToYesterday = "어제보다 추워요";
	}

	public void resetLabel() {		
		la_weatherText.setText(compareToYesterday);
		la_weatherIcon.setImage(weatherImgPaths[v1Icon]);
		la_mainTemp.setText(UN.value.getT1h() + " ℃");
		la_highLowTemp.setText(String.format("%d ℃ / %d ℃", VF.value.getTmx(), VF.value.getTmn()));
		la_rainPer.setText("비 올 확률 " + VF.value.getPop() + "%");
		la_reh.setText("습도 " + UN.value.getReh() + "%");
		la_wsd.setText("풍속 " + UN.value.getWsd() + "m/s");
		
		la_w1Icon.setImage(weatherImgPaths[v2Icon]);
		la_w1HLTemp.setText(String.format("%d ℃ / %d ℃", VF.v_2.getTmx(), VF.v_2.getTmn()));
		la_w1Pop.setText("비 올 확률 " + VF.v_2.getPop() + "%");

		la_w2Icon.setImage(weatherImgPaths[v3Icon]);
		la_w2HLTemp.setText(String.format("%d ℃ / %d ℃", VF.v_3.getTmx(), VF.v_3.getTmn()));
		la_w2Pop.setText("비 올 확률 " + VF.v_3.getPop() + "%");
		
		this.updateUI();
	}

	public int getIconNum(int pty, int sky) {
		int iconNum = 0;
		if (pty == 0 && sky == 1) iconNum = 0;
		else if (pty == 0 && sky == 3) iconNum = 1;
		else if (pty == 0 && sky == 4) iconNum = 2;
		else if (pty == 1 && sky == 1) iconNum = 3;
		else if (pty == 1 && sky == 3) iconNum = 4;
		else if (pty == 1 && sky == 4) iconNum = 5;
		else if (pty == 2 && sky == 1) iconNum = 6;
		else if (pty == 2 && sky == 3) iconNum = 7;
		else if (pty == 2 && sky == 4) iconNum = 8;
		else if (pty == 3 && sky == 1) iconNum = 9;
		else if (pty == 3 && sky == 3) iconNum = 10;
		else if (pty == 3 && sky == 4) iconNum = 11;
		else if (pty == 4 && sky == 1) iconNum = 12;
		else if (pty == 4 && sky == 3) iconNum = 13;
		else if (pty == 4 && sky == 4) iconNum = 14;	
		return iconNum;
	}
}