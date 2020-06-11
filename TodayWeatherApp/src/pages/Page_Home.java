package pages;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Api;
import data.Value;
import lib.FilePath;
import lib.GetDate;
import lib.SetStyle;
import main.MainDrive;
import objects.ImageLabel;
import objects.TextLabel;

public class Page_Home extends Page {
	JPanel p_today; // ���� �����̳� (���� ����)
	Choice ch_loc; // ���� ���� ��ü
	JLabel la_date; // ���� ��¥
	JLabel la_weatherText; // �������� ���� �� (����?)
	ImageLabel la_weatherIcon; // ���� ���� ������
	JLabel la_mainTemp; // ���� ���
	JLabel la_highLowTemp; // �ְ� / ���� ���
	JLabel la_rainPer; // �� �� Ȯ��
	JLabel la_reh; // ����
	JLabel la_wsd; // ǳ��

	JPanel p_weekly; // ���� ��� �����̳� (�ְ� ����)
	JPanel p_w_1; // ���� ���� �г�
	JLabel la_w1Day; // ���� ����
	ImageLabel la_w1Icon; // ���� ���� ������
	JLabel la_w1Pop; // ���� �� �� Ȯ��
	JLabel la_w1HLTemp; // ���� �ְ� / ���� ���

	JPanel p_w_2; // �� ���� �г�
	JLabel la_w2Day; // �� ����
	ImageLabel la_w2Icon; // �� ���� ������
	JLabel la_w2Pop; // �� �� �� Ȯ��
	JLabel la_w2HLTemp; // �� �ְ� / ���� ���

	JPanel p_subBox; // ���� �ϴ� �����̳� (�̼�����)
	JLabel la_dustTitle; // "������ �̼�����"
	JLabel la_empty;
	ImageLabel la_dustImg; // �̼����� �̹���
	JLabel la_dustData; // �̼����� ��ġ (50 ��/m��)
	JLabel la_dustContent; // ��ġ�� ��õ text
	

	String baseDate = GetDate.date_today;
	String baseTime = GetDate.time_before1h;
	String yesterday = GetDate.date_yesterday;
	String yesterdayBaseTime = GetDate.time_after1h;

	String[] location = { "������", "��⵵", "��󳲵�", "���ϵ�", "���ֱ�����", "�뱸������", "����������", "�λ걤����", "����Ư����", "����Ư����ġ��", "��걤����",
			"��õ������", "���󳲵�", "����ϵ�", "����Ư����ġ��", "��û����", "��û�ϵ�" };
	ArrayList<Integer> dustList;
	int[][] nxny = { { 73, 134 }, { 60, 120 }, { 91, 77 }, { 89, 91 }, { 58, 74 }, { 89, 90 }, { 67, 100 }, { 98, 76 },
			{ 60, 127 }, { 66, 103 }, { 102, 84 }, { 55, 124 }, { 51, 67 }, { 63, 89 }, { 52, 38 }, { 68, 100 },
			{ 69, 107 } };
	String iconDir = FilePath.weatherIconDir;
	String[] iconNames = { "0-1.png", "0-3.png", "0-4.png", "1-1.png", "1-3.png", "1-4.png", "2-1.png", "2-3.png",
			"2-4.png", "3-1.png", "3-3.png", "3-4.png", "4-1.png", "4-3.png", "4-4.png" };
	String[] dustImages = { "good.png", "soso.png", "bad.png", "sobad.png" };
	String[] dustContents = {"������ �����غ��°� ����?", "�׷����� ���� ���̿���", "���Ⱑ Ź�ϳ׿�...", "����ũ �� �����ϰ� �����ϼ���!"};

	String[] weatherCase = {"����", "����", "����", "��/�ҳ���", "�ٶ�", "�帲/�Ȱ�", "��"};

	
	Api UN_yesterday = null;
	Api UN = null;
	Api UF = null;
	Api VF = null;

	int nx, ny;
	int dustState;
	String dustText;
	int v1Icon, v2Icon, v3Icon, dIcon;
	String compareToYesterday = null;
	
	Page_Recommend recPage;

	public Page_Home(MainDrive main, Page_Recommend recPage, String title, String bgPath, boolean showFlag) {
		super(main, title, bgPath, showFlag);
		
		this.recPage=recPage;

		// on memory
		p_today = new JPanel(); // ���� �����̳�
		ch_loc = new Choice(); // ���� ����
		la_date = new TextLabel(GetDate.text_todayDate, 350, 50, 40); // 5�� 24��
		la_weatherText = new TextLabel("", 200, 30, 15); // �������� �߿���
		la_weatherIcon = new ImageLabel("", 250, 250); // ���� ������ �ش��ϴ� �̹��� ������
		la_mainTemp = new TextLabel("0 ��", 350, 50, 30); // ���� ���
		la_highLowTemp = new TextLabel("0 �� / 0 ��", 350, 20, 15);
		la_rainPer = new TextLabel("�� �� Ȯ�� 0%", 350, 80, 20); // �� �� Ȯ�� 0%
		la_reh = new TextLabel("���� 0%", 175, 20, 15); // ���� 0%
		la_wsd = new TextLabel("ǳ�� 0m/s", 175, 20, 15); // ǳ�� 0m/s

		p_weekly = new JPanel(null); // ���� ��� �����̳� (�ְ� ����)
		p_w_1 = new JPanel(); // ���� ���� �г�
		la_w1Day = new TextLabel(GetDate.day_tomorrow, 240, 60, 23); // ���� ����
		la_w1Icon = new ImageLabel("", 110, 110); // ���� ���� ������
		la_w1HLTemp = new TextLabel("0 �� / 0 ��", 240, 30, 20);
		la_w1Pop = new TextLabel("�� �� Ȯ�� 0%", 240, 30, 15); // �� �� Ȯ�� 0%

		p_w_2 = new JPanel(); // �� ���� �г�
		la_w2Day = new TextLabel(GetDate.day_afterDay, 240, 60, 23); // �� ����
		la_w2Icon = new ImageLabel("", 110, 110); // �� ���� ������
		la_w2HLTemp = new TextLabel("0 �� / 0 ��", 240, 30, 20);
		la_w2Pop = new TextLabel("�� �� Ȯ�� 0%", 240, 30, 15); // �� �� Ȯ�� 0%

		p_subBox = new JPanel(); // ���� �ϴ� �����̳� (�̼� ����)
		la_dustTitle=new TextLabel("������ �̼�����", 500, 70, 35, Font.BOLD, JLabel.LEFT);
		la_empty=new  TextLabel("", 500, 25, 0);
		la_dustImg=new ImageLabel("", 70, 70);
		la_dustData=new TextLabel("0 ��/m��", 170, 50, 30);
		la_dustContent=new TextLabel("", 380, 50, 20);

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
		ch_loc.select("����Ư����");

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
		
		p_subBox.add(la_dustTitle);
		p_subBox.add(la_empty);
		p_subBox.add(la_dustImg);
		p_subBox.add(la_dustData);
		p_subBox.add(la_dustContent);

		// ������ �г��� ���� �󺧿� ���̱� (���� �����̳� 3��)
		Page_Home.this.label.add(p_today);
		Page_Home.this.label.add(p_weekly);
		Page_Home.this.label.add(p_subBox);
		

		Thread thread2=new Thread() {
			public void run() {
				ImageLabel loadingBg = new ImageLabel(FilePath.buttonDir+"loading.jpg", main.screen_width, main.screen_height);
				loadingBg.setBounds(0, 0, main.screen_width, main.screen_height);
				Page_Home.this.label.add(loadingBg);
				p_today.setVisible(false);
				p_weekly.setVisible(false);
				p_subBox.setVisible(false);
				
				getApi("����Ư����");
				getWeatherName();
				recPage.getPlaceList(main.weatherName, "����Ư����");
				setAnother();
				resetLabel();
				
				Page_Home.this.label.remove(loadingBg);
				p_today.setVisible(true);
				p_weekly.setVisible(true);
				p_subBox.setVisible(true);
				
				updateUI();
			}
		};
		thread2.start();

		ch_loc.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Thread thread=new Thread() {
					public void run() {
						getApi(e.getItem().toString());
						getWeatherName();
						recPage.getPlaceList(main.weatherName, e.getItem().toString());
						setAnother();
						resetLabel();
					}
				};
				thread.start();
			}
		});
		updateUI();

	}

	// api �ҷ�����
	public void getApi(String space) {
		for (int i = 0; i < location.length; i++) {
			if (location[i].equals(space)) {
				nx = nxny[i][0];
				ny = nxny[i][1];
				try {
					if (Integer.parseInt(yesterdayBaseTime) < 100)
						yesterday = baseDate;
					else if (Integer.parseInt(baseTime) >= 2300)
						baseDate = yesterday;
					UN_yesterday = new Api("getUltraSrtNcst", yesterday, yesterdayBaseTime, nx, ny);
					UN = new Api("getUltraSrtNcst", baseDate, baseTime, nx, ny);
					UF = new Api("getUltraSrtFcst", baseDate, baseTime, nx, ny);
					VF = new Api("getVilageFcst", baseDate, "0500", nx, ny);
					dustList=UN.d_dataLoad();
					dustState=dustList.get(i);
					int num;
					if(dustState<=30) num=0;
					else if(dustState<=50) num=1;
					else if(dustState<=100) num=2;
					else num=3;
					dustText=dustContents[num];
					dIcon=num;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void resetLabel() {
		la_weatherText.setText(compareToYesterday);
		la_weatherIcon.setImage(iconDir + iconNames[v1Icon]);
		la_mainTemp.setText(UN.value.getT1h() + " ��");
		la_highLowTemp.setText(String.format("%d �� / %d ��", VF.value.getTmx(), VF.value.getTmn()));
		la_rainPer.setText("�� �� Ȯ�� " + VF.value.getPop() + "%");
		la_reh.setText("���� " + UN.value.getReh() + "%");
		la_wsd.setText("ǳ�� " + UN.value.getWsd() + "m/s");

		la_w1Icon.setImage(iconDir + iconNames[v2Icon]);
		la_w1HLTemp.setText(String.format("%d �� / %d ��", VF.v_2.getTmx(), VF.v_2.getTmn()));
		la_w1Pop.setText("�� �� Ȯ�� " + VF.v_2.getPop() + "%");

		la_w2Icon.setImage(iconDir + iconNames[v3Icon]);
		la_w2HLTemp.setText(String.format("%d �� / %d ��", VF.v_3.getTmx(), VF.v_3.getTmn()));
		la_w2Pop.setText("�� �� Ȯ�� " + VF.v_3.getPop() + "%");
		
		la_dustImg.setImage(FilePath.dustIconDir+dustImages[dIcon]);
		la_dustData.setText(Integer.toString(dustState)+" ��/m��");
		la_dustContent.setText(dustText);

		updateUI();
	}
	
	public void getWeatherName() {
		int num=0;
		Value un=UN.value;
		Value uf=UF.value;
		Value vf=VF.value;
		
		if (uf.getPty()==0) {
			if (un.getReh()>75) num=2; // ����
			else {
				if (un.getWsd()>10) num=4; // �ٶ�
				else {
					if(un.getT1h()>25) num=1; // ����
					else if (uf.getSky()!=1) num=5; // �帲/�Ȱ�
					else num=0; // ����
				}
			}
		} 
		else if (uf.getPty()==3) num=6; // ��
		else num=3; // ��/�ҳ���
		
		main.weatherName=weatherCase[num];
	}

	// ���������� ���� �� compareToYesterday ����
	public void setAnother() {
		v1Icon = getIconNum(VF.value.getPty(), VF.value.getSky());
		v2Icon = getIconNum(VF.v_2.getPty(), VF.v_2.getSky());
		v3Icon = getIconNum(VF.v_3.getPty(), VF.v_3.getSky());

		if (UN_yesterday.value.getT1h() < UN.value.getT1h())
			compareToYesterday = "�������� ������";
		else if (UN_yesterday.value.getT1h() == UN.value.getT1h())
			compareToYesterday = "������ ����ؿ�";
		else if (UN_yesterday.value.getT1h() > UN.value.getT1h())
			compareToYesterday = "�������� �߿���";
	}

	public int getIconNum(int pty, int sky) {
		int iconNum = 0;
		if (pty == 0 && sky == 1)
			iconNum = 0;
		else if (pty == 0 && sky == 3)
			iconNum = 1;
		else if (pty == 0 && sky == 4)
			iconNum = 2;
		else if (pty == 1 && sky == 1)
			iconNum = 3;
		else if (pty == 1 && sky == 3)
			iconNum = 4;
		else if (pty == 1 && sky == 4)
			iconNum = 5;
		else if (pty == 2 && sky == 1)
			iconNum = 6;
		else if (pty == 2 && sky == 3)
			iconNum = 7;
		else if (pty == 2 && sky == 4)
			iconNum = 8;
		else if (pty == 3 && sky == 1)
			iconNum = 9;
		else if (pty == 3 && sky == 3)
			iconNum = 10;
		else if (pty == 3 && sky == 4)
			iconNum = 11;
		else if (pty == 4 && sky == 1)
			iconNum = 12;
		else if (pty == 4 && sky == 3)
			iconNum = 13;
		else if (pty == 4 && sky == 4)
			iconNum = 14;
		return iconNum;
	}
}