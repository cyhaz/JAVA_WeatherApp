package designed_object;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

/* �ؽ�Ʈ �� ���� ��ü */
public class TextLabel extends JLabel {
	
	public TextLabel(String text, int width, int height, int fontSize) {
		super(text);
		
		setPreferredSize(new Dimension(width, height));
		setFont(new Font("����", Font.BOLD, fontSize));
		setHorizontalAlignment(JLabel.CENTER);
	}
}
