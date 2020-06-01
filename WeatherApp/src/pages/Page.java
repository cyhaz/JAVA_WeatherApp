package pages;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MainDrive;

/* 기본 페이지 틀 */
public class Page extends JPanel {
	MainDrive main;
	String title;
	int width;
	int height;
	
	Image image;
	ImageIcon icon;
	JLabel label;

	public Page(MainDrive main, String title, String bgPath, boolean showFlag) {
		this.main=main;
		this.title=title;
		this.width=main.screen_width;
		this.height=main.screen_height;
		
		this.icon=new ImageIcon(bgPath);
		this.image=icon.getImage().getScaledInstance(width+(width/40), height-(height/150), Image.SCALE_SMOOTH);
		this.icon=new ImageIcon(image);
		this.label=new JLabel(icon);
		this.label.setBounds(0, 0, width+(width/40), height-(height/150));

		this.add(label);
		
		this.setLayout(null);
		this.setVisible(showFlag);
		this.setPreferredSize(new Dimension(this.width, this.height));
	}
	
	public void updateTitle() {
		this.main.setTitle("오늘의 날씨 - "+this.title);
	}
}