package designed_object;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/* 상단 아이콘 생성 객체 (JLabel) */
public class TopIcon extends JLabel {

	public TopIcon(String path) {
		ImageIcon icon=new ImageIcon(path);
		Image image=icon.getImage().getScaledInstance(40, 35, Image.SCALE_SMOOTH);
		icon=new ImageIcon(image);
		setIcon(icon);
		
		setPreferredSize(new Dimension(60, 35));
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
//				setBackground(Color.yellow);
			}
			public void mouseExited(MouseEvent e) {
//				setBackground(null);
			}
		});
	}
}