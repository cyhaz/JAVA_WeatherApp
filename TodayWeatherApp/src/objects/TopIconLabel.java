package objects;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lib.FilePath;
import main.MainDrive;

public class TopIconLabel extends JLabel {
	ImageIcon icon;
	Image img;

	public TopIconLabel(String fileName, int index) {
		setPreferredSize(new Dimension(60, 40));
		setImage(FilePath.topIconDir + fileName, 40, 35);

		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setImage(FilePath.topIconDir + fileName, 45, 40);
			}

			public void mouseExited(MouseEvent e) {
				setImage(FilePath.topIconDir + fileName, 40, 35);
			}

			public void mouseClicked(MouseEvent e) {
				MainDrive.showSelectedPage(index);
			}
		});
	}

	public void setImage(String path, int width, int height) {
		icon = new ImageIcon(path);
		img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		setIcon(icon);
	}
}