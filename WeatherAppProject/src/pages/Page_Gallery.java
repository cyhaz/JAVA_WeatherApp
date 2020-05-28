package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import designed_object.ImageLabel;
import designed_object.TextLabel;
import lib.SetStyle;

public class Page_Gallery extends Page {
	JPanel p_left; // 좌측 컨테이너
	JLabel la_titleL; // 제목
	JLabel la_weatherText; // "오늘 날씨"
	JPanel p_weatherImage; // 선택할 날씨 이미지 5개
	JLabel la_feelingText; // "오늘 기분"
	JPanel p_feelingImage; // 선택할 기분 이미지 5개
	JPanel p_thumb;
	JButton bt_choose;
	JLabel la_thumb;	// 썸네일
	JTextArea area; // 텍스트입력 공간
	JScrollPane scroll; // 텍스트입력 공간 포함하는 스크롤
	JLabel la_upload; // 업로드 버튼
	JLabel la_empty1;
	JLabel la_empty2;
	
	JPanel p_right; // 우측 컨테이너
	JLabel la_titleR; // 제목

	public Page_Gallery(MainDriver mainDriver, String title, String bg_path, boolean showFlag) {
		super(mainDriver, title, bg_path, showFlag);
		
		p_left=new JPanel();
		la_titleL=new TextLabel("Today's", 430, 70, 25);
		la_weatherText=new TextLabel("오늘의 날씨", 110, 120, 18);
		p_weatherImage=new JPanel();
		la_feelingText=new TextLabel("오늘의 기분", 110, 120, 18);
		p_feelingImage=new JPanel();
		la_empty1=new TextLabel("", 400, 30, 0);
		p_thumb=new JPanel(new BorderLayout());
		bt_choose=new JButton("사진 선택");
		la_thumb=new ImageLabel(mainDriver.bgDir+"bg5.jpg", 120, 90);
		area=new JTextArea(9, 23);
		scroll=new JScrollPane(area);
		la_empty2=new TextLabel("", 400, 30, 0);
		la_upload=new ImageLabel(mainDriver.bgDir+"bg1.jpg", 380, 60);
		
		p_right=new JPanel();
		
		// style
		SetStyle.setPanelStyle(p_left, 40, 20, 430, 655);
		SetStyle.setPanelStyle(p_right, 520, 20, 430, 655);
		la_titleL.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
		p_weatherImage.setPreferredSize(new Dimension(270, 70));
		p_feelingImage.setPreferredSize(new Dimension(270, 70));
		
		// add
		p_thumb.add(bt_choose, BorderLayout.NORTH);
		p_thumb.add(la_thumb);
		
		p_left.add(la_titleL);
		p_left.add(la_weatherText);
		p_left.add(p_weatherImage);
		p_left.add(la_feelingText);
		p_left.add(p_feelingImage);
		p_left.add(la_empty1);
		p_left.add(p_thumb);
		p_left.add(scroll);
		p_left.add(la_empty2);
		p_left.add(la_upload);
		
		
		this.label.add(p_left);
		this.label.add(p_right);
		
		la_upload.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				upload();
			}
		});
	}
	
	public void upload() {
		
	}
}
