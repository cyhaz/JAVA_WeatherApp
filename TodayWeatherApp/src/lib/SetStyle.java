package lib;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class SetStyle {

	// ū ���� �г� ��Ÿ�� ���� �޼���
	public static void setPanelStyle(JPanel panel, int x, int y, int width, int height) {
		panel.setBounds(x, y, width, height);
		panel.setBackground(new Color(255, 255, 255, 100));
		Border border = BorderFactory.createRaisedBevelBorder();
		panel.setBorder(border);
	}
}