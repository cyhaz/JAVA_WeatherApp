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
	int screen_width = 1000; // 전체 스크린 너비
	int screen_height = 700; // 전체 스크린 높이

	JPanel p_menu; // 상단 메뉴 패널
	JPanel p_container; // 하단 메인 컨테이너 패널

	Page[] pages = new Page[5]; // 하단 메인 컨테이너에 붙여질 패널 5개 (홈, 로그인, 사진관리, ...)

	// 상단 메뉴 아이콘 이미지 경로
	File resFile = new File("src/res");
	String resDir = resFile.getAbsolutePath();
	String bgDir = resDir + "./bg/";
	String topIconDir = resDir + "./topIcon/";
	String[] topIconPath = { topIconDir + "instagram.png", topIconDir + "list.png", topIconDir + "home.png",
			topIconDir + "user.png", topIconDir + "chat.png" };

	public MainDriver() {
		super("오늘의 날씨 - 홈");

		// on memory
		p_menu = new JPanel();
		p_container = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));

		// pages (홈, 로그인, 사진관리, ...)
		pages[0] = new Page_Gallery(this, "날씨 갤러리", bgDir + "bg3.jpg", false);
		pages[1] = new Page_List(this, "To do list", bgDir + "bg2.jpg", false);
		pages[2] = new Page_Home(this, "홈", bgDir + "bg1.jpg", true);					
		pages[3] = new Page_User(this, "마이 페이지", bgDir + "bg4.jpg", false);
		pages[4] = new Page_Chat(this, "1:1 채팅", bgDir + "bg5.jpg", false);

		// style
		p_menu.setBackground(new Color(52, 85, 115));
		p_container.setPreferredSize(new Dimension(screen_width - 15, screen_height - 20));

		createTopIcon(); // 상단 메뉴 아이콘 생성 (클릭 이벤트 구현 필요!!!!!!!!!!!!!!!!!!!!!!)

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

	// 상단 메뉴에 아이콘 생성 및 부착
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

	// 클릭한 아이콘에 해당하는 페이지 출력
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