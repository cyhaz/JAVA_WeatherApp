package designed_object;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.MainDrive;

/* 이미지 라벨 생성 객체 */
public class ImageLabel extends JLabel {
	int width;
	int height;
	String path;
	String selectPath;
	
	public ImageLabel(String path, int width, int height) {
		this.path=path;
		this.selectPath=path;
		this.width=width;
		this.height=height;
		
		setIconImage(path);
		setPreferredSize(new Dimension(width, height));
	} 
	
	public void setImage(String path) {
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
	
	// 오늘의 날씨, 기분 클릭 시 선택 및 weatherType, feelType값 변경
	public void clickWeather(String selectPath) {
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(ImageLabel.this.selectPath.equals(path)) {
					setIconImage(selectPath);
					ImageLabel.this.selectPath=selectPath;					
				} else if(ImageLabel.this.selectPath.equals(selectPath)){
					setIconImage(path);
					ImageLabel.this.selectPath=path;
				}
			}
		});
	}
	
	public void shortCuts(int pageNum) {
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {	
				MainDrive.showSelectedPage(pageNum);
			}
		});
	}
	
	public void setIconImage(String path) {
		ImageIcon icon=new ImageIcon(path);
		Image image=icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon=new ImageIcon(image);
		setIcon(icon);
	}
}