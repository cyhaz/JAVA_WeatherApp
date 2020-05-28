package pages;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Page extends JPanel {
	MainDriver mainDriver;		// 본 페이지가 붙여질 window
	String title;							// 본 페이지가 붙여졌을 때 설정될 window title
	String bg_path;						// 배경 이미지 경로
	int width;								// 너비
	int height;								// 높이
	boolean showFlag;				// 본 페이지가 붙여질지에 대한 논리값
	
	Image img;
	ImageIcon icon;
	JLabel label;
	
	public Page(MainDriver mainDriver, String title, String bg_path, boolean showFlag) {			// 화면제목, 배경이미지경로, visible 논리값	
		this.mainDriver=mainDriver;
		this.title=title;
		this.bg_path=bg_path;
		this.showFlag=showFlag;
		
		this.width=mainDriver.screen_width;
		this.height=mainDriver.screen_height;
		
		this.icon=new ImageIcon(this.bg_path);
		this.img=icon.getImage().getScaledInstance(this.width+(this.width/40), this.height-(this.height/150), Image.SCALE_SMOOTH);
		this.icon=new ImageIcon(this.img);
		this.label=new JLabel(this.icon);
		this.label.setBounds(0, 0, this.width+(this.width/40), this.height-(this.height/150));
		
		this.add(label);
		
		this.setLayout(null);
		this.setVisible(this.showFlag);
		this.setPreferredSize(new Dimension(this.width, this.height));
	}
	
	public void updateTitle() {
		this.mainDriver.setTitle("오늘의 날씨 - "+this.title);
	}
}
