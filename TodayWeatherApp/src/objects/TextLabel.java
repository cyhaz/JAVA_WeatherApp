package objects;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class TextLabel extends JLabel {

	public TextLabel(String text, int width, int height, int fontSize, int fontStyle, int position) {
		super(text);

		setPreferredSize(new Dimension(width, height));
		setFont(new Font("���� ������ 230", fontStyle, fontSize));
		setHorizontalAlignment(position);
	}

	// �۲� ����, ������� �⺻ ����
	public TextLabel(String text, int width, int height, int fontSize) {
		super(text);

		setPreferredSize(new Dimension(width, height));
		setFont(new Font("���� ������ 230", Font.BOLD, fontSize));
		setHorizontalAlignment(JLabel.CENTER);
	}
}