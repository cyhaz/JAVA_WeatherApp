package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import designed_object.ImageLabel;
import designed_object.TextLabel;
import main.MainDrive;

public class DiaryFrame extends JFrame {
	ImageLabel p_image;
	JPanel p_contents;
	JLabel la_timeText;
	JLabel la_weatherType;
	JTextArea t_content;
	JScrollPane scroll;
	
	int width=300;
	int height=400;
	
	String weatherType=null;
	String feelType=null;
	
	public DiaryFrame() {
		super("Diary");
		
		p_image=new ImageLabel(MainDrive.resDir+"./bg/bg1.jpg", width, 180);
		p_contents=new JPanel();
		la_timeText=new TextLabel("몇월 몇일 몇시 몇분 업로드", 280, 20, 12);
		la_weatherType=new TextLabel(String.format("#오늘날씨 #%s #오늘기분 #%s", weatherType, feelType), 280, 20, 13);
		t_content=new JTextArea(8, 25);
		scroll=new JScrollPane(t_content);
		
		la_timeText.setHorizontalAlignment(JLabel.LEFT);
		la_weatherType.setHorizontalAlignment(JLabel.LEFT);
		la_timeText.setForeground(Color.gray);
		la_weatherType.setForeground(Color.blue);
		p_contents.setBackground(Color.white);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		t_content.setFocusable(false);
		t_content.setText("일기내용");
		
		add(p_image, BorderLayout.NORTH);
		add(p_contents);
		p_contents.add(la_timeText);
		p_contents.add(la_weatherType);
		p_contents.add(scroll);
		
		setVisible(true);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new DiaryFrame();
	}
}