package designed_object;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

/* ÅØ½ºÆ® ¶óº§ »ý¼º °´Ã¼ */
public class TextLabel extends JLabel {
	
	public TextLabel(String text, int width, int height, int fontSize) {
		super(text);
		
		setPreferredSize(new Dimension(width, height));
		setFont(new Font("ÇÑÄÄ À±°íµñ 230", Font.BOLD, fontSize));
		setHorizontalAlignment(JLabel.CENTER);
	}
}
