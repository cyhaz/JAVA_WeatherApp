package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import designed_object.TopIcon;

public class MainDriver extends JFrame {
	int screen_width = 1000; // ��ü ��ũ�� �ʺ�
	int screen_height = 700; // ��ü ��ũ�� ����

	JPanel p_menu; // ��� �޴� �г�
	JPanel p_container; // �ϴ� ���� �����̳� �г�

	Page[] pages = new Page[5]; // �ϴ� ���� �����̳ʿ� �ٿ��� �г� 5�� (Ȩ, �α���, ��������, ...)

	// ��� �޴� ������ �̹��� ���
	File resFile = new File("src/res");
	String resDir = resFile.getAbsolutePath();
	String bgDir = resDir + "./bg/";
	String topIconDir = resDir + "./topIcon/";
	String[] topIconPath = { topIconDir + "instagram.png", topIconDir + "list.png", topIconDir + "home.png",
			topIconDir + "user.png", topIconDir + "chat.png" };

	public MainDriver() {
		super("������ ���� - Ȩ");

		// on memory
		p_menu = new JPanel();
		p_container = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));

		// pages (Ȩ, �α���, ��������, ...)
		pages[0] = new Page_Gallery(this, "���� ������", bgDir + "bg3.jpg", false);
		pages[1] = new Page_List(this, "To do list", bgDir + "bg2.jpg", false);
		pages[2] = new Page_Home(this, "Ȩ", bgDir + "bg1.jpg", true);					
		pages[3] = new Page_User(this, "���� ������", bgDir + "bg4.jpg", false);
		pages[4] = new Page_Chat(this, "1:1 ä��", bgDir + "bg5.jpg", false);

		// style
		p_menu.setBackground(new Color(52, 85, 115));
		p_container.setPreferredSize(new Dimension(screen_width - 15, screen_height - 20));

		createTopIcon(); // ��� �޴� ������ ���� (Ŭ�� �̺�Ʈ ���� �ʿ�!!!!!!!!!!!!!!!!!!!!!!)

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
		for (int i = 0; i < pages.length; i++) {
			TopIcon topIcon = new TopIcon(topIconPath[i]);
			p_menu.add(topIcon);
			int index = i;
			topIcon.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					showSelectedPage(index);
				}
			});
		}
		p_menu.updateUI();
	}

	// Ŭ���� �����ܿ� �ش��ϴ� ������ ���
	public void showSelectedPage(int index) {
		for (int i = 0; i < pages.length; i++) {
			pages[i].setVisible(false);
		}
		pages[index].setVisible(true);
		pages[index].updateTitle();
	}

	public static void main(String[] args) {
		new MainDriver();
	}
}