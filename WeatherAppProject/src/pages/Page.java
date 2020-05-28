package pages;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Page extends JPanel {
	MainDriver mainDriver;		// �� �������� �ٿ��� window
	String title;							// �� �������� �ٿ����� �� ������ window title
	String bg_path;						// ��� �̹��� ���
	int width;								// �ʺ�
	int height;								// ����
	boolean showFlag;				// �� �������� �ٿ������� ���� ����
	
	Image img;
	ImageIcon icon;
	JLabel label;
	
	public Page(MainDriver mainDriver, String title, String bg_path, boolean showFlag) {			// ȭ������, ����̹������, visible ����	
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
		this.mainDriver.setTitle("������ ���� - "+this.title);
	}
}
