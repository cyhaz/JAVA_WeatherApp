package objects;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

import lib.FilePath;

public class TextLabel extends JLabel {

	public TextLabel(String text, int width, int height, int fontSize, int fontStyle, int position) {
		super(text);

		setPreferredSize(new Dimension(width, height));
		setFont(setFont(fontSize));
		setHorizontalAlignment(position);
	}

	// 글꼴 굵게, 가운데정렬 기본 지정
	public TextLabel(String text, int width, int height, int fontSize) {
		super(text);

		setPreferredSize(new Dimension(width, height));
		setFont(setFont(fontSize));
		setHorizontalAlignment(JLabel.CENTER);
	}

	public Font setFont(int fontSize) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		int fontIndex = 0;

		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(FilePath.resDir + "/HANYGO230.TTF")));

			for (int i = 0; i < ge.getAvailableFontFamilyNames().length; i++) {
				if (ge.getAvailableFontFamilyNames()[i].equals("한컴 윤고딕 230")) {
					fontIndex = i;
					break;
				}
			}

		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Font(ge.getAvailableFontFamilyNames()[fontIndex], Font.BOLD, fontSize);
	}
}