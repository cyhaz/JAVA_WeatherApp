package pages;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Api;
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

	String baseDate = GetDate.date_today;
	String baseTime = GetDate.time_before1h;
	String yesterday = GetDate.date_yesterday;
	String yesterdayBaseTime = GetDate.time_after1h;

	String[] location = { "������", "��⵵", "��󳲵�", "���ϵ�", "���ֱ�����", "�뱸������", "����������", "�λ걤����", "����Ư����", "����Ư����ġ��", "��걤����",
			"��õ������", "���󳲵�", "����ϵ�", "���ֵ�", "��û����", "��û�ϵ�" };
	int[][] nxny = { { 73, 134 }, { 60, 120 }, { 91, 77 }, { 89, 91 }, { 58, 74 }, { 89, 90 }, { 67, 100 }, { 98, 76 },
			{ 60, 127 }, { 66, 103 }, { 102, 84 }, { 55, 124 }, { 51, 67 }, { 63, 89 }, { 52, 38 }, { 68, 100 },
			{ 69, 107 } };
	String iconDir = FilePath.weatherIconDir;
	String[] iconNames = { "0-1.png", "0-3.png", "0-4.png", "1-1.png", "1-3.png", "1-4.png", "2-1.png", "2-3.png",
			"2-4.png", "3-1.png", "3-3.png", "3-4.png", "4-1.png", "4-3.png", "4-4.png" };

	Api UN_yesterday = null;
	Api UN = null;
	Api UF = null;
	Api VF = null;

	int nx, ny;
	int v1Icon, v2Icon, v3Icon;
	String compareToYesterday = null;

	public Page_Home(MainDrive main, String title, String bgPath, boolean showFlag) {
		super(main, title, bgPath, showFlag);

		getApi("����Ư����");
		setAnother();

		// on memory
		p_today = new JPanel(); // ���� �����̳�
		ch_loc = new Choice(); // ���� ����
		la_date = new TextLabel(GetDate.text_todayDate, 350, 50, 40); // 5�� 24��
		la_weatherText = new TextLabel(compareToYesterday, 200, 30, 15); // �������� �߿���
		la_weatherIcon = new ImageLabel(iconDir + iconNames[v1Icon], 250, 250); // ���� ������ �ش��ϴ� �̹��� ������
		la_mainTemp = new TextLabel(UN.value.getT1h() + " ��", 350, 50, 30); // ���� ���
		la_highLowTemp = new TextLabel(String.format("%d �� / %d ��", VF.value.getTmx(), VF.value.getTmn()), 350, 20, 15);
		la_rainPer = new TextLabel("�� �� Ȯ�� " + VF.value.getPop() + "%", 350, 80, 20); // �� �� Ȯ�� 0%
		la_reh = new TextLabel("���� " + UN.value.getReh() + "%", 175, 20, 15); // ���� 0%
		la_wsd = new TextLabel("ǳ�� " + UN.value.getWsd() + "m/s", 175, 20, 15); // ǳ�� 0m/s

		p_weekly = new JPanel(null); // ���� ��� �����̳� (�ְ� ����)
		p_w_1 = new JPanel(); // ���� ���� �г�
		la_w1Day = new TextLabel(GetDate.day_tomorrow, 240, 60, 23); // ���� ����
		la_w1Icon = new ImageLabel(iconDir + iconNames[v2Icon], 110, 110); // ���� ���� ������
		la_w1HLTemp = new TextLabel(String.format("%d �� / %d ��", VF.v_2.getTmx(), VF.v_2.getTmn()), 240, 30, 20);
		la_w1Pop = new TextLabel("�� �� Ȯ�� " + VF.v_2.getPop() + "%", 240, 30, 15); // �� �� Ȯ�� 0%

		p_w_2 = new JPanel(); // �� ���� �г�
		la_w2Day = new TextLabel(GetDate.day_afterDay, 240, 60, 23); // �� ����
		la_w2Icon = new ImageLabel(iconDir + iconNames[v3Icon], 110, 110); // �� ���� ������
		la_w2HLTemp = new TextLabel(String.format("%d �� / %d ��", VF.v_3.getTmx(), VF.v_3.getTmn()), 240, 30, 20);
		la_w2Pop = new TextLabel("�� �� Ȯ�� " + VF.v_3.getPop() + "%", 240, 30, 15); // �� �� Ȯ�� 0%

		p_subBox = new JPanel(); // ���� �ϴ� �����̳� (�̼� ����)

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

		// ������ �г��� ���� �󺧿� ���̱� (���� �����̳� 3��)
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

		this.updateUI();
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