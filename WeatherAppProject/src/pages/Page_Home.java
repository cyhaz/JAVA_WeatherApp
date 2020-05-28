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
	JPanel p_today; // ���� �����̳� (���� ����)
	Choice ch_loc; // ���� ���� ��ü
	JLabel la_date; // ���� ��¥
	JLabel la_weatherText; // �������� ���� �� (����?)
	JLabel la_weatherIcon; // ���� ���� ������
	JLabel la_mainTemp; // ���� ���
	JLabel la_highLowTemp; // �ְ� / ���� ���
	JLabel la_rainPer; // �� �� Ȯ��
	JLabel la_reh; // ����
	JLabel la_wsd; // ǳ��

	JPanel p_weekly; // ���� ��� �����̳� (�ְ� ����)
	JPanel p_w_1; // ���� ���� �г�
	JLabel la_w1Day; // ���� ����
	JLabel la_w1Icon; // ���� ���� ������
	JLabel la_w1MainTemp; // ���� ��� ���
	JLabel la_w1HLTemp; // ���� �ְ� / ���� ���

	JPanel p_w_2; // �� ���� �г�
	JLabel la_w2Day; // �� ����
	JLabel la_w2Icon; // �� ���� ������
	JLabel la_w2MainTemp; // �� ��� ���
	JLabel la_w2HLTemp; // �� �ְ� / ���� ���

	JPanel p_subBox; // ���� �ϴ� �����̳� (�̼�����)

	// ��Ÿ
	Date d = new Date();
	String todayText = new SimpleDateFormat("M�� dd��").format(d); // 5�� 25��
	String searchDate = new SimpleDateFormat("yyyyMMdd").format(d); // 20200525
	
	String[] days= {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Satday"};

	String dir = "C:/naver/NaverCloud/Another/YH Development/java_workspace/TeamProject/src/res/icon/";
	String[] weatherImgPaths = { dir + "0-1.png", dir + "0-3.png", dir + "0-4.png", dir + "1-1.png", dir + "1-3.png",
			dir + "1-4.png", dir + "2-1.png", dir + "2-3.png", dir + "2-4.png", dir + "3-1.png", dir + "3-3.png",
			dir + "3-4.png", dir + "4-1.png", dir + "4-3.png", dir + "4-4.png" };

	String[] location = { "������", "��⵵", "��󳲵�", "���ϵ�", "���ֱ�����", "�뱸������", "����������", "�λ걤����", "����Ư����", "����Ư����ġ��", "��걤����",
			"��õ������", "���󳲵�", "����ϵ�", "���ֵ�", "��û����", "��û�ϵ�" };

	int today = Integer.parseInt(searchDate);
	String[] date_list = { Integer.toString(today), Integer.toString(today + 1), Integer.toString(today + 2) };
	String[] time_list = { "0600", "0900", "1200", "1500", "1800", "2100", "0000", "0300" };
	
	// ���� String
	Calendar calendar=Calendar.getInstance();
	int thisDay=calendar.get(Calendar.DAY_OF_WEEK)-1;
	String day1, day2;
	
	// ������
	public Page_Home(MainDriver mainDriver, String title, String bg_path, boolean showFlag) {
		super(mainDriver, title, bg_path, showFlag);
		
		// ����
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
		p_today = new JPanel(); // ���� �����̳�
		ch_loc = new Choice(); // ���� ����
		la_date = new TextLabel(todayText, 350, 50, 40); // 5�� 24��
		la_weatherText = new TextLabel("�������� ���� ��", 200, 30, 15); // �������� �߿���
		la_weatherIcon = new ImageLabel(weatherImgPaths[0], 250, 250); // ���� ������ �ش��ϴ� �̹��� ������
		la_mainTemp = new TextLabel(v_today.getT3h() + " ��", 350, 50, 30); // ���� ���
		la_highLowTemp = new TextLabel(String.format("%.1f�� / %.1f��", today_tmx, today_tmn), 350, 20, 15); // �ְ��� / �������
		la_rainPer = new TextLabel("��� Ȯ�� " + v_today.getPop()+"%", 350, 80, 20); // �� �� Ȯ�� 0%
		la_reh = new TextLabel("���� "+v_today.getReh()+"%", 175, 20, 15); // ���� 0%
		la_wsd = new TextLabel("ǳ�� "+v_today.getWsd()+"m/s", 175, 20, 15); // ǳ�� 0m/s

		p_weekly = new JPanel(null); // ���� ��� �����̳� (�ְ� ����)
		p_w_1 = new JPanel(); // ���� ���� �г�
		la_w1Day = new TextLabel(day1, 240, 60, 23); // ���� ����
		la_w1Icon = new ImageLabel(weatherImgPaths[1], 110, 110); // ���� ���� ������
		la_w1MainTemp = new TextLabel(v_day1.getT3h() + " ��", 240, 30, 20); // ���� ��� ���
		la_w1HLTemp = new TextLabel(String.format("%.1f�� / %.1f��", day1_tmx, day1_tmn), 240, 30, 15); // ���� �ְ� / ���� ���

		p_w_2 = new JPanel(); // �� ���� �г�
		la_w2Day = new TextLabel(day2, 240, 60, 23); // �� ����
		la_w2Icon = new ImageLabel(weatherImgPaths[2], 110, 110); // �� ���� ������
		la_w2MainTemp = new TextLabel(v_day2.getT3h() + " ��", 240, 30, 20); // �� ��� ���
		la_w2HLTemp = new TextLabel(String.format("%.1f�� / %.1f��", day2_tmx, day2_tmn), 240, 30, 15); // �� �ְ� / ���� ���

		p_subBox = new JPanel(); // ���� �ϴ� �����̳� (�̼� ����)

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

		// ������ �г��� ���� �󺧿� ���̱� (���� �����̳� 3��)
		this.label.add(p_today);
		this.label.add(p_weekly);
		this.label.add(p_subBox);
	}

	// ū ���� �г� ��Ÿ�� ���� �޼���
	public void setPanelStyle(JPanel panel, int x, int y, int width, int height) {
		panel.setBounds(x, y, width, height);
		panel.setBackground(new Color(255, 255, 255, 100));
		Border border = BorderFactory.createRaisedBevelBorder();
		panel.setBorder(border);
	}
}