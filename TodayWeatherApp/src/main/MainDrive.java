package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lib.FilePath;
import objects.TopIconLabel;
import pages.Page;
import pages.Page_Diary;
import pages.Page_Home;
import pages.Page_Recommend;
import pages.Page_Todo;
import pages.Page_User;

public class MainDrive extends JFrame {
	public int screen_width = 1000; // 전체 스크린 너비
	public int screen_height = 700; // 전체 스크린 높이

	// topIcon 파일 경로
	String topIconDir = FilePath.topIconDir;
	String[] topIconPath = { "instagram.png", "list.png", "home.png", "user.png", "shop.png" };

	public ConManager conManager = ConManager.getInstance(); // 메인 커넥션 메니저
	public Connection con = conManager.getConnection();
	public static boolean loginFlag = false; // 로그인 플래그
	public static int member_no;
	public static String member_id;

	// 메인 디자인
	JPanel p_menu; // 상단 메뉴 패널
	JPanel p_container; // 하단 메인 컨테이너 패널
	static Page[] pages = new Page[5];

	public MainDrive() {
		super("오늘의 날씨 - 홈");

		// on memory
		p_menu = new JPanel();
		p_container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

		// p_container에 붙여질 패널들
//		pages[0] = new Page_Diary(this, "날씨 갤러리", FilePath.bgDir + "bg3.jpg", false);
//		pages[1] = new Page_Todo(this, "To do list", FilePath.bgDir + "bg2.jpg", false);
//		pages[2] = new Page_Home(this, "홈", FilePath.bgDir + "bg1.jpg", false);
		pages[3] = new Page_User(this, "마이 페이지", FilePath.bgDir + "bg4.jpg", true);
//		pages[4] = new Page_Recommend(this, "Today's Place", FilePath.bgDir + "bg5.jpg", false);

		// style
		p_menu.setBackground(new Color(12, 21, 74));
		p_container.setPreferredSize(new Dimension(screen_width - 15, screen_height - 20));
		createTopIcon();

		// add
//		p_container.add(pages[0]);
//		p_container.add(pages[1]);
//		p_container.add(pages[2]);
		p_container.add(pages[3]);
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
				conManager.closeDB(con);
				System.exit(0);
			}
		});
	}

	// 상단 메뉴에 아이콘 생성 및 부착
	public void createTopIcon() {
		for (int i = 0; i < 5; i++) {
			TopIconLabel topIcon = new TopIconLabel(topIconPath[i], i);
			p_menu.add(topIcon);
		}
		p_menu.updateUI();
	}

	// 클릭한 아이콘에 해당하는 페이지 출력
	public static void showSelectedPage(int index) {
		if (!loginFlag) {
			if (index == 0 || index == 1 || index == 4) {
				JOptionPane.showMessageDialog(pages[0], "로그인 후 이용가능합니다.");
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

	public void ifLogoutResetPages() {
		if (!loginFlag) {
			// 각 페이지 조회했던 데이터 리셋
		}
	}

	public static void main(String[] args) {
		new MainDrive();
	}
}