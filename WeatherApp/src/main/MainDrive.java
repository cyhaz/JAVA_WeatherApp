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
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import designed_object.TopIcon;
import lib.ConnectionManager;
import pages.Page;
import pages.Page_Gallery;
import pages.Page_Home;
import pages.Page_User;

public class MainDrive extends JFrame {
	public int screen_width = 1000; // ��ü ��ũ�� �ʺ�
	public int screen_height = 700; // ��ü ��ũ�� ����

	// ���� ���
	public static String resDir = new File("src/res").getAbsolutePath();	
	String bgDir = resDir + "./bg/";
	String topIconDir = resDir + "./topIcon/";
	String[] topIconPath = { topIconDir + "photo.png", topIconDir + "list.png", topIconDir + "home.png",
			topIconDir + "user.png", topIconDir + "shop.png" };

	// ���� ������
	JPanel p_menu; // ��� �޴� �г�
	JPanel p_container; // �ϴ� ���� �����̳� �г�

	// ������ 5��
	public static Page[] pages = new Page[5];
	
	public ConnectionManager connectionManager;
	public Connection con;
	public static boolean loginFlag=false;

	public MainDrive() throws Exception {
		super("������ ���� - Ȩ");
		
		connectionManager=new ConnectionManager();
		con=connectionManager.getConnection();

		// on memory
		p_menu = new JPanel();
		p_container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		// p_container�� �ٿ��� �гε�
		pages[0] = new Page_Gallery(this, "���� ������", bgDir + "bg3.jpg", true);
		pages[1] = new Page_User(this, "To do list", bgDir + "bg2.jpg", false);
		pages[2] = new Page_Home(this, "Ȩ", bgDir + "bg1.jpg", true);
		pages[3] = new Page_User(this, "���� ������", bgDir + "bg4.jpg", false);
		pages[4] = new Page_User(this, "1:1 ä��", bgDir + "bg5.jpg", false);

		// style
		p_menu.setBackground(new Color(52, 85, 115));
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
	public static void showSelectedPage(int index) {
		if(!loginFlag) {
			if(index==0 || index==1 || index==4) {
				JOptionPane.showMessageDialog(pages[0], "�α��� �� �̿밡���մϴ�.");
			} else {
				for (int i = 0; i < pages.length; i++) {
					pages[i].setVisible(false);
				}
				pages[index].setVisible(true);
				pages[index].updateTitle();
			}
		} else {
			for (int i = 0; i < pages.length; i++) {
				pages[i].setVisible(false);
			}
			pages[index].setVisible(true);
			pages[index].updateTitle();
		}
	}

	public static void main(String[] args) throws Exception {
		new MainDrive();
	}
}