package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import designed_object.TopIcon;
import pages.Page;
import pages.Page_Gallery;
import pages.Page_Home;

public class MainDrive extends JFrame {
	public int screen_width = 1000; // ��ü ��ũ�� �ʺ�
	public int screen_height = 700; // ��ü ��ũ�� ����

	// ���� ���
	public static String resDir = new File("src/res").getAbsolutePath();
	String bgDir = resDir + "./bg/";
	String topIconDir = resDir + "./topIcon/";
	String[] topIconPath = { topIconDir + "instagram.png", topIconDir + "list.png", topIconDir + "home.png",
			topIconDir + "user.png", topIconDir + "chat.png" };

	// ���� ������
	JPanel p_menu; // ��� �޴� �г�
	JPanel p_container; // �ϴ� ���� �����̳� �г�

	// ������ 5��
	public Page[] pages = new Page[5];

	public MainDrive() throws Exception {
		super("������ ���� - Ȩ");

		// on memory
		p_menu = new JPanel();
		p_container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		// p_container�� �ٿ��� �гε�
		pages[0] = new Page_Gallery(this, "���� ������", bgDir + "bg3.jpg", true);
//		pages[1] = new Page_Home(this, "To do list", bgDir + "bg2.jpg", false);
		pages[2] = new Page_Home(this, "Ȩ", bgDir + "bg1.jpg", false);
//		pages[3] = new Page_Home(this, "���� ������", bgDir + "bg4.jpg", false);
//		pages[4] = new Page_Home(this, "1:1 ä��", bgDir + "bg5.jpg", false);

		// style
		p_menu.setBackground(new Color(52, 85, 115));
		p_container.setPreferredSize(new Dimension(screen_width - 15, screen_height - 20));
		createTopIcon();

		// add
		p_container.add(pages[0]);
//		p_container.add(pages[1]);
		p_container.add(pages[2]);
//		p_container.add(pages[3]);
//		p_container.add(pages[4]);
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

	public static void main(String[] args) throws Exception {
		new MainDrive();
	}
}