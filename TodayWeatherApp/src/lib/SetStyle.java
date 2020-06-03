package lib;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class SetStyle {

	// 큰 투명 패널 스타일 적용 메서드
	public static void setPanelStyle(JPanel panel, int x, int y, int width, int height) {
		panel.setBounds(x, y, width, height);
		panel.setBackground(new Color(255, 255, 255, 100));
		Border border = BorderFactory.createRaisedBevelBorder();
		panel.setBorder(border);
	}
}