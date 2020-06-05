package pages;

import javax.swing.JPanel;

import lib.SetStyle;
import main.MainDrive;

public class Page_Recommend extends Page {
	JPanel p_container; // 메인 패널
	
	public Page_Recommend(MainDrive main, String title, String bgPath, boolean showFlag) {
		super(main, title, bgPath, showFlag);
		
		p_container=new JPanel();
		
		SetStyle.setPanelStyle(p_container, 100, 90, 800, 500);
		
		this.label.add(p_container);
	}
}
