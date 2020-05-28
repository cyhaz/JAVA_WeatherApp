package pages;

import java.awt.Color;

import javax.swing.JPanel;

public class Page_Gallery extends Page {
	JPanel p_test;
	
	public Page_Gallery(MainDriver mainDriver, String title, String bg_path, boolean showFlag) {
		super(mainDriver, title, bg_path, showFlag);
		
		p_test=new JPanel();
		p_test.setBounds(20, 20, 100, 100);
		p_test.setBackground(Color.red);
		this.label.add(p_test);
	}
}
