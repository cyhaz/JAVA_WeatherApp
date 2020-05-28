package designed_object;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/* 이미지 라벨 생성 객체 */
public class ImageLabel extends JLabel {
	public ImageLabel(String path, int width, int height) {
		ImageIcon icon=new ImageIcon(path);
		Image image=icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon=new ImageIcon(image);
		setIcon(icon);
		
		setPreferredSize(new Dimension(width, height));
	}
}
