package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lib.FilePath;
import objects.TopIconLabel;
import pages.Page;
import pages.Page_Diary;
import pages.Page_Home;
import pages.Page_User;

public class MainDrive extends JFrame {
	public int screen_width = 1000; // ��ü ��ũ�� �ʺ�
	public int screen_height = 700; // ��ü ��ũ�� ����

	// topIcon ���� ���
	String topIconDir = FilePath.topIconDir;
	String[] topIconPath = { "instagram.png", "list.png", "home.png", "user.png", "shop.png" };

	public ConManager conManager = ConManager.getInstance(); // ���� Ŀ�ؼ� �޴���
	public static boolean loginFlag = false; // �α��� �÷���

	// ���� ������
	JPanel p_menu; // ��� �޴� �г�
	JPanel p_container; // �ϴ� ���� �����̳� �г�
	static Page[] pages = new Page[5];

	public MainDrive() {
		super("������ ���� - Ȩ");

		// on memory
		p_menu = new JPanel();
		p_container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

		// p_container�� �ٿ��� �гε�
		pages[0] = new Page_Diary(this, "���� ������", FilePath.bgDir + "bg3.jpg", true);
		pages[1] = new Page_User(this, "To do list", FilePath.bgDir + "bg2.jpg", false);
		pages[2] = new Page_Home(this, "Ȩ", FilePath.bgDir + "bg1.jpg", false);
		pages[3] = new Page_User(this, "���� ������", FilePath.bgDir + "bg4.jpg", false);
		pages[4] = new Page_User(this, "1:1 ä��", FilePath.bgDir + "bg5.jpg", false);

		// style
		p_menu.setBackground(new Color(146, 173, 77));
		p_container.setPreferredSize(new Dimension(screen_width - 15, screen_height - 20));
		createTopIcon();

		// add
		p_container.add(pages[0]);
		p_container.add(pages[1]);
		p_container.add(pages[2]);
		p_container.add(pages[3]);
		p_container.add(pages[4]);
		add(p_menu, BorderLayout.NORTH);
		add(p_container);

		// set window
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	// ��� �޴��� ������ ���� �� ����
	public void createTopIcon() {
		for (int i = 0; i < 5; i++) {
			TopIconLabel topIcon = new TopIconLabel(topIconPath[i], i);
			p_menu.add(topIcon);
		}
		p_menu.updateUI();
	}

	// Ŭ���� �����ܿ� �ش��ϴ� ������ ���
	public static void showSelectedPage(int index) {
		if (!loginFlag) {
			if (index == 0 || index == 1 || index == 4) {
				JOptionPane.showMessageDialog(pages[0], "�α��� �� �̿밡���մϴ�.");
			} else {
				setVisiblePages(index);
			}
		} else {
			setVisiblePages(index);
		}
	}

	public static void setVisiblePages(int index) {
		for (int i = 0; i < pages.length; i++) {
			pages[i].setVisible(false);
		}
		pages[index].setVisible(true);
		pages[index].updateTitle();
	}

	public static void main(String[] args) {
		new MainDrive();
	}
}