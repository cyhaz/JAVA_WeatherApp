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
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import designed_object.ImageLabel;
import designed_object.TextLabel;
import lib.ConnectionManager;
import lib.FileManager;
import lib.SetStyle;
import main.MainDrive;

public class Page_Gallery extends Page {
	JPanel p_left; // 좌측 컨테이너
	JLabel la_titleL; // 제목
	JLabel la_weatherText; // "오늘 날씨"
	ImageLabel la_w1, la_w2, la_w3, la_w4, la_w5; // 선택할 날씨 이미지 5개
	JLabel la_feelingText; // "오늘 기분"
	ImageLabel la_feel1, la_feel2, la_feel3, la_feel4, la_feel5; // 선택할 기분 이미지 5개
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

	FileInputStream fis;
	FileOutputStream fos;
	File file;
	
	int weatherType=0;
	int feelType=0;
	
	String iconPath=MainDrive.resDir+"./weatherIcon/";

	public Page_Gallery(MainDrive mainDrive, String title, String bg_path, boolean showFlag) {
		super(mainDrive, title, bg_path, showFlag);
		
		p_left=new JPanel();
		la_titleL=new TextLabel("Today's", 430, 70, 25);
		la_weatherText=new TextLabel("오늘의 날씨", 110, 120, 18);
		la_w1=new ImageLabel(iconPath+"0-1.png", 50, 50);
		la_w2=new ImageLabel(iconPath+"0-4.png", 50, 50);
		la_w3=new ImageLabel(iconPath+"1-1.png", 50, 50);
		la_w4=new ImageLabel(iconPath+"1-4.png", 50, 50);
		la_w5=new ImageLabel(iconPath+"4-3.png", 50, 50);
		la_feelingText=new TextLabel("오늘의 기분", 110, 120, 18);
		la_feel1=new ImageLabel(iconPath+"0-1.png", 50, 50);
		la_feel2=new ImageLabel(iconPath+"0-4.png", 50, 50);
		la_feel3=new ImageLabel(iconPath+"1-1.png", 50, 50);
		la_feel4=new ImageLabel(iconPath+"1-4.png", 50, 50);
		la_feel5=new ImageLabel(iconPath+"4-3.png", 50, 50);
		la_empty1=new TextLabel("", 400, 30, 0);
		p_thumb=new JPanel(new BorderLayout());
		bt_choose=new JButton("사진 선택");
		thumbIcon=new ImageIcon(mainDrive.resDir+"./button/thumb.png");
		la_thumb=new JLabel() {
			public void paint(Graphics g) {
				g.drawImage(thumbIcon.getImage(), 0, 0, 120, 90, la_thumb);
			}
		};
		area=new JTextArea(9, 23);
		scroll=new JScrollPane(area);
		la_empty2=new TextLabel("", 400, 30, 0);
		la_upload=new ImageLabel(mainDrive.resDir+"./button/upload.png", 380, 60);
		
		p_right=new JPanel();
		la_titleR=new TextLabel("My Diary", 430, 70, 25);
		
		chooser=new JFileChooser(mainDrive.resDir);
		
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
		p_left.add(la_w1);
		p_left.add(la_w2);
		p_left.add(la_w3);
		p_left.add(la_w4);
		p_left.add(la_w5);
		p_left.add(la_feelingText);
		p_left.add(la_feel1);
		p_left.add(la_feel2);
		p_left.add(la_feel3);
		p_left.add(la_feel4);
		p_left.add(la_feel5);
		p_left.add(la_empty1);
		p_left.add(p_thumb);
		p_left.add(scroll);
		p_left.add(la_empty2);
		p_left.add(la_upload);
		
		p_right.add(la_titleR);
		
		this.label.add(p_left);
		this.label.add(p_right);
		
		la_upload.connListener(mainDrive.resDir+"./button/upload_select.png");
		la_w1.clickWeather(mainDrive.resDir+"./button/upload_select.png");
		
		bt_choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile();
			}
		});
		
		la_upload.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				upload();
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
	
	public boolean copyImage() {
		boolean result=false;
		try {
			long time=System.currentTimeMillis();
			String ext=FileManager.getEXT(file.getAbsolutePath());
			String imgName=time+"."+ext;
			fos=new FileOutputStream(main.resDir+"./copyObject/"+imgName);
			
			byte[] buff=new byte[1024];
			int data=-1;
			while(true) {
				data=fis.read(buff);
				if(data==-1) break;
				fos.write(buff);
			}
			result=true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public void upload() {
		JOptionPane.showMessageDialog(this, "업로드 시도");
		if(copyImage()) {
			String sql="";
			
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			try {
				pstmt=main.con.prepareStatement(sql);
//				pstmt.setInt(1, );
//				pstmt.setString(2, );
//				pstmt.setString(3, );
//				pstmt.setInt(4, );
//				pstmt.setInt(5, );
//				pstmt.setString(6, );
//				pstmt.setString(7, );
				int result=pstmt.executeUpdate();
				if(result==0) JOptionPane.showMessageDialog(this, "업로드 완료");
				else JOptionPane.showMessageDialog(this, "에러 발생. 확인 후 다시 시도해주세요");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				this.main.connectionManager.closeDB(pstmt);
			}
		}
	}
}