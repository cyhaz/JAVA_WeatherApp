package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lib.FilePath;
import lib.SetStyle;
import main.MainDrive;
import objects.ImageLabel;
import objects.TextLabel;

public class Page_Diary extends Page {
	JPanel p_left; // 좌측 컨테이너
	JLabel la_titleL; // 제목
	JLabel la_weatherText; // "오늘 날씨"
	public static ImageLabel[] la_weatherImages=new ImageLabel[5]; // 선택할 날씨 이미지 5개
	JLabel la_feelingText; // "오늘 기분"
	public static ImageLabel[] la_feelImages=new ImageLabel[5];
	JPanel p_thumb; // 선택한 파일 썸네일
	JButton bt_choose; // 파일 선택 버튼
	JFileChooser chooser; // 파일 chooser
	JLabel la_thumb;	// 썸네일
	ImageIcon thumbIcon;
	JTextArea area; // 텍스트입력 공간
	JScrollPane scroll; // 텍스트입력 공간 포함하는 스크롤
	ImageLabel la_upload; // 업로드 버튼
	JLabel la_empty1;
	JLabel la_empty2;
	
	JPanel p_right; // 우측 컨테이너
	JLabel la_titleR; // 제목
	
	String iconPath=FilePath.oriIconDir;
	
	FileInputStream fis;
	FileOutputStream fos;
	File file;
	
	public static int weatherType=50;
	public static int feelType=50;

	public Page_Diary(MainDrive main, String title, String bgPath, boolean showFlag) {
		super(main, title, bgPath, showFlag);
		
		p_left=new JPanel();
		la_titleL=new TextLabel("Today's", 430, 70, 25);
		la_weatherText=new TextLabel("오늘의 날씨", 110, 120, 18);
		la_weatherImages[0]=new ImageLabel(iconPath+"0.png", 50, 50);
		la_weatherImages[1]=new ImageLabel(iconPath+"1.png", 50, 50);
		la_weatherImages[2]=new ImageLabel(iconPath+"2.png", 50, 50);
		la_weatherImages[3]=new ImageLabel(iconPath+"3.png", 50, 50);
		la_weatherImages[4]=new ImageLabel(iconPath+"4.png", 50, 50);
		la_feelingText=new TextLabel("오늘의 기분", 110, 120, 18);
		la_feelImages[0]=new ImageLabel(iconPath+"0.png", 50, 50);
		la_feelImages[1]=new ImageLabel(iconPath+"1.png", 50, 50);
		la_feelImages[2]=new ImageLabel(iconPath+"2.png", 50, 50);
		la_feelImages[3]=new ImageLabel(iconPath+"3.png", 50, 50);
		la_feelImages[4]=new ImageLabel(iconPath+"4.png", 50, 50);
		la_empty1=new TextLabel("", 400, 30, 0);
		p_thumb=new JPanel(new BorderLayout());
		bt_choose=new JButton("사진 선택");
		thumbIcon=new ImageIcon(FilePath.buttonDir +"thumb.png");
		la_thumb=new JLabel() {
			public void paint(Graphics g) {
				g.drawImage(thumbIcon.getImage(), 0, 0, 120, 90, la_thumb);
			}
		};
		area=new JTextArea(9, 23);
		scroll=new JScrollPane(area);
		la_empty2=new TextLabel("", 400, 30, 0);
		la_upload=new ImageLabel(FilePath.buttonDir +"upload.png", 380, 60);
		
		p_right=new JPanel();
		la_titleR=new TextLabel("My Diary", 430, 70, 25);
		
		chooser=new JFileChooser(FilePath.resDir);
		
		// style
		SetStyle.setPanelStyle(p_left, 40, 20, 430, 655);
		SetStyle.setPanelStyle(p_right, 530, 20, 430, 655);
		la_titleL.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
		la_titleR.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.red));
		la_thumb.setPreferredSize(new Dimension(120, 90));

		// add
		p_thumb.add(bt_choose, BorderLayout.NORTH);
		p_thumb.add(la_thumb);
		
		p_left.add(la_titleL);
		p_left.add(la_weatherText);
		p_left.add(la_weatherImages[0]);
		p_left.add(la_weatherImages[1]);
		p_left.add(la_weatherImages[2]);
		p_left.add(la_weatherImages[3]);
		p_left.add(la_weatherImages[4]);
		p_left.add(la_feelingText);
		p_left.add(la_feelImages[0]);
		p_left.add(la_feelImages[1]);
		p_left.add(la_feelImages[2]);
		p_left.add(la_feelImages[3]);
		p_left.add(la_feelImages[4]);
		p_left.add(la_empty1);
		p_left.add(p_thumb);
		p_left.add(scroll);
		p_left.add(la_empty2);
		p_left.add(la_upload);
		
		p_right.add(la_titleR);
		
		this.label.add(p_left);
		this.label.add(p_right);
		
		la_upload.ifEnteredSetImage(FilePath.buttonDir+"upload_select.png");
		
		for(int i=0;i<la_weatherImages.length;i++) {
			la_weatherImages[i].ifClickedSetImage(FilePath.selectIconDir+i+".png", la_weatherImages);
		}
		for(int i=0;i<la_feelImages.length;i++) {
			la_feelImages[i].ifClickedSetImage(FilePath.selectIconDir+i+".png", la_feelImages);
		}
		
		bt_choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile();
			}
		});
		
		la_upload.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				upload();
			}
		});
	}
	
	// 이미지파일 선택 후 썸네일  표현
	public void chooseFile() {
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				file=chooser.getSelectedFile();
				fis = new FileInputStream(file);
				thumbIcon=new ImageIcon(file.getAbsolutePath());
				la_thumb.repaint();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void copyImage() {
		
	}
	
	public void upLoad() {
		String imageName;
		String diaryContent;
	}
}