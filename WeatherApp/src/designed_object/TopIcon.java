package designed_object;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/* ��� ������ ���� ��ü (JLabel) */
public class TopIcon extends JLabel {

	public TopIcon(String path) {
		ImageIcon icon=new ImageIcon(path);
		Image image=icon.getImage().getScaledInstance(40, 35, Image.SCALE_SMOOTH);
		icon=new ImageIcon(image);
		setIcon(icon);
		
		setPreferredSize(new Dimension(60, 40));
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				ImageIcon icon=new ImageIcon(path);
				Image image=icon.getImage().getScaledInstance(45, 40, Image.SCALE_SMOOTH);
				icon=new ImageIcon(image);
				setIcon(icon);
			}
			public void mouseExited(MouseEvent e) {
				ImageIcon icon=new ImageIcon(path);
				Image image=icon.getImage().getScaledInstance(40, 35, Image.SCALE_SMOOTH);
				icon=new ImageIcon(image);
				setIcon(icon);
			}

		});
	}
}