package objects;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.MainDrive;
import pages.Page_Diary;

public class ImageLabel extends JLabel {
	ImageIcon icon;
	Image img;

	String path;
	int width;
	int height;
	int clickCount;
	int thisNum=50;

	public ImageLabel(String path, int width, int height) {
		this.path = path;
		this.width = width;
		this.height = height;

		setPreferredSize(new Dimension(width, height));
		setImage(path, width, height);
	}

	public void ifEnteredSetImage(String path_2) {
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setImage(path_2, width, height);
			}

			public void mouseExited(MouseEvent e) {
				setImage(path, width, height);
			}
		});
	}

	public void ifClickedSetImage(String path_2, ImageLabel[] list) {
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 클릭 시 이미지 변경
				if(clickCount%2==0) {
					for(int i=0;i<list.length;i++) {
						list[i].setImage(list[i].path);
						list[i].clickCount=2;
					}
					setImage(path_2);
					clickCount=1;
				} else {
					setImage(path);
					clickCount=0;
				}
				for(int i=0;i<list.length;i++) {
					if(ImageLabel.this.equals(list[i]))  thisNum=i;
				}
				if(list.equals(Page_Diary.la_weatherImages)) {
					Page_Diary.weatherType=thisNum;
				} else {
					Page_Diary.feelType=thisNum;
				}
			}
		});
	}

	public void ifClickedGoPage(int pageNum) {
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 클릭 시 페이지 바로가기
				MainDrive.showSelectedPage(pageNum);
			}
		});
	}

	public void setImage(String path, int width, int height) {
		icon = new ImageIcon(path);
		img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		setIcon(icon);
	}
	
	public void setImage(String path) {
		setImage(path, width, height);
	}
}