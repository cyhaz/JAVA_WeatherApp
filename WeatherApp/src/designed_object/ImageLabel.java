package designed_object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/* 이미지 라벨 생성 객체 */
public class ImageLabel extends JLabel {
	int width;
	int height;
	String path;
	ImageIcon icon;
	Image image;
	String thisPath;
	
	public ImageLabel(String path, int width, int height) {
		this.path=path;
		this.thisPath=path;
		this.width=width;
		this.height=height;
		
		setIconImage(path);
		setPreferredSize(new Dimension(width, height));
	} 
	
	public void setImage(String path) {
		this.path=path;
		setIconImage(path);
	}
	
	public void connListener(String selectPath) {
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setIconImage(selectPath);
				updateUI();
			}
			public void mouseExited(MouseEvent e) {
				setIconImage(path);
				updateUI();
			}
		});
	}
	
	public void connListenerClicked(String selectPath) {
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ImageLabel.this.removeAll();
				ImageLabel.this.setIcon(null);
				
				if(thisPath.equals(path)) {
					setIconImage(selectPath);
					thisPath=selectPath;
				} else {
					setIconImage(path);
					thisPath=path;
				}
				ImageLabel.this.updateUI();
			}
		});
	}
	
	public void setIconImage(String path) {
		icon=new ImageIcon(path);
		image=icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon=new ImageIcon(image);
		setIcon(icon);
	}
}