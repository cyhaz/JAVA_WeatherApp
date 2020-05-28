package designed_object;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

/* 텍스트 라벨 생성 객체 */
public class TextLabel extends JLabel {
	
	public TextLabel(String text, int width, int height, int fontSize) {
		super(text);
		
		setPreferredSize(new Dimension(width, height));
		setFont(new Font("굴림", Font.BOLD, fontSize));
		setHorizontalAlignment(JLabel.CENTER);
	}
}
