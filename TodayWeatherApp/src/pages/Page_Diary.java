package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

import data.Diary;
import lib.FilePath;
import lib.GetDate;
import lib.SetStyle;
import main.MainDrive;
import objects.ImageLabel;
import objects.TextLabel;

public class Page_Diary extends Page {
	JPanel p_left; // ���� �����̳�
	JLabel la_titleL; // ����
	JLabel la_weatherText; // "���� ����"
	public static ImageLabel[] la_weatherImages = new ImageLabel[5]; // ������ ���� �̹��� 5��
	JLabel la_feelingText; // "���� ���"
	public static ImageLabel[] la_feelImages = new ImageLabel[5];
	JPanel p_thumb; // ������ ���� �����
	JButton bt_choose; // ���� ���� ��ư
	JFileChooser chooser; // ���� chooser
	JLabel la_thumb; // �����
	ImageIcon thumbIcon;
	JTextArea area; // �ؽ�Ʈ�Է� ����
	JScrollPane scroll; // �ؽ�Ʈ�Է� ���� �����ϴ� ��ũ��
	ImageLabel la_upload; // ���ε� ��ư
	JLabel la_empty1;
	JLabel la_empty2;

	JPanel p_right; // ���� �����̳�
	ImageLabel bt_lookUp; // ��� ��ȸ ��ư
	JLabel la_titleR; // ����

	String iconPath = FilePath.oriIconDir;

	Connection con;
	FileInputStream fis;
	FileOutputStream fos;
	File file;
	String imageName;

	public static int weatherType = 50;
	public static int feelType = 50;

	public Page_Diary(MainDrive main, String title, String bgPath, boolean showFlag) {
		super(main, title, bgPath, showFlag);

		p_left = new JPanel();
		la_titleL = new TextLabel("Today's", 430, 70, 25);
		la_weatherText = new TextLabel("������ ����", 110, 120, 18);
		la_weatherImages[0] = new ImageLabel(iconPath + "0.png", 50, 50);
		la_weatherImages[1] = new ImageLabel(iconPath + "1.png", 50, 50);
		la_weatherImages[2] = new ImageLabel(iconPath + "2.png", 50, 50);
		la_weatherImages[3] = new ImageLabel(iconPath + "3.png", 50, 50);
		la_weatherImages[4] = new ImageLabel(iconPath + "4.png", 50, 50);
		la_feelingText = new TextLabel("������ ���", 110, 120, 18);
		la_feelImages[0] = new ImageLabel(iconPath + "0.png", 50, 50);
		la_feelImages[1] = new ImageLabel(iconPath + "1.png", 50, 50);
		la_feelImages[2] = new ImageLabel(iconPath + "2.png", 50, 50);
		la_feelImages[3] = new ImageLabel(iconPath + "3.png", 50, 50);
		la_feelImages[4] = new ImageLabel(iconPath + "4.png", 50, 50);
		la_empty1 = new TextLabel("", 400, 30, 0);
		p_thumb = new JPanel(new BorderLayout());
		bt_choose = new JButton("���� ����");
		thumbIcon = new ImageIcon(FilePath.buttonDir + "thumb.png");
		la_thumb = new JLabel() {
			public void paint(Graphics g) {
				g.drawImage(thumbIcon.getImage(), 0, 0, 120, 90, la_thumb);
			}
		};
		area = new JTextArea(9, 23);
		area.setLineWrap(true);
		scroll = new JScrollPane(area);
		la_empty2 = new TextLabel("", 400, 30, 0);
		la_upload = new ImageLabel(FilePath.buttonDir + "upload.png", 380, 60);

		p_right = new JPanel();
		bt_lookUp=new ImageLabel(FilePath.buttonDir+"refresh.png", 35, 35);
		la_titleR = new TextLabel("My Diary", 350, 70, 25);

		chooser = new JFileChooser(FilePath.resDir);
		con = main.con;

		// style
		SetStyle.setPanelStyle(p_left, 40, 20, 430, 655);
		SetStyle.setPanelStyle(p_right, 530, 20, 430, 655);
//		p_right.setBackground(Color.white);
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

		p_right.add(bt_lookUp);
		p_right.add(la_titleR);

		this.label.add(p_left);
		this.label.add(p_right);

		la_upload.ifEnteredSetImage(FilePath.buttonDir + "upload_select.png", this);
		bt_lookUp.ifEnteredSetImage(FilePath.buttonDir+"refresh_select.png", this);

		for (int i = 0; i < la_weatherImages.length; i++) {
			la_weatherImages[i].ifClickedSetImage(FilePath.selectIconDir + i + ".png", la_weatherImages, this);
		}
		for (int i = 0; i < la_feelImages.length; i++) {
			la_feelImages[i].ifClickedSetImage(FilePath.selectIconDir + i + ".png", la_feelImages, this);
		}

		bt_choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile();
			}
		});

		la_upload.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Thread thread=new Thread() {
					@Override
					public void run() {
						if (weatherType>4 || feelType>4 || imageName.equals("") || area.getText().equals("")) {
							JOptionPane.showMessageDialog(Page_Diary.this, "�������� ���� �׸��� �ֽ��ϴ�.");
						} else {
							upLoad();							
						}
					}
				};
				thread.start();
			}
		});
		bt_lookUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Thread thread=new Thread() {
					@Override
					public void run() {
						getDiaryList();
					}
				};
				thread.start();
			}
		});
	}

	// �̹������� ���� �� ����� ǥ��
	public void chooseFile() {
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				file = chooser.getSelectedFile();
				fis = new FileInputStream(file);
				thumbIcon = new ImageIcon(file.getAbsolutePath());
				la_thumb.repaint();
				
				long time = System.currentTimeMillis();
				String ext = FilePath.getEXT(file.getAbsolutePath());
				imageName = time + "." + ext;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean copyImage() {
		boolean result = false;
		try {
			fos = new FileOutputStream(FilePath.copyObjectDir + imageName);

			byte[] buff = new byte[1024];
			int data = -1;
			while (true) {
				data = fis.read(buff);
				if (data == -1)
					break;
				fos.write(buff);
			}
			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "�̹��� ��θ� �ٽ� Ȯ�����ּ���.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			main.conManager.closeStream(fis);
			main.conManager.closeStream(fos);
		}
		return result;
	}

	public void upLoad() {
		int answer=JOptionPane.showConfirmDialog(this, "����Ͻðڽ��ϱ�?");
		if(answer==JOptionPane.OK_OPTION) {
			if (copyImage()) {
				String diaryContent = area.getText();
				String registDate = GetDate.text_todayDate;
				String registTime = GetDate.text_nowTime();
				
				String sql = "insert into diary(diary_no, member_no, regist_date, regist_time, weathertype, feeltype, image, content)";
				sql += " values(seq_diary.nextval, ?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement pstmt = null;
				try {
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, main.member_no); // member_no
					pstmt.setString(2, registDate); // regist_date
					pstmt.setString(3, registTime); // regist_time
					pstmt.setInt(4, weatherType + 1); // weathertype
					pstmt.setInt(5, feelType + 1); // feeltype
					pstmt.setString(6, imageName); // image
					pstmt.setString(7, diaryContent); // content
					
					int result = pstmt.executeUpdate();
					if (result != 0) {
						JOptionPane.showMessageDialog(this, "��ϵǾ����ϴ�.");					
						area.setText("");
						thumbIcon = new ImageIcon(FilePath.buttonDir+"thumb.png");
						la_thumb.repaint();

						for (int i = 0; i < la_weatherImages.length; i++) {
							la_weatherImages[i].setImage(FilePath.oriIconDir + i + ".png");
						}
						for (int i = 0; i < la_feelImages.length; i++) {
							la_feelImages[i].setImage(FilePath.oriIconDir + i + ".png");
						}
						updateUI();
						getDiaryList();
						imageName="";
					} else {
						JOptionPane.showMessageDialog(this, "��Ͽ� �����߽��ϴ�.\nȮ�� �� �ٽ� �õ����ּ���.");					
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					main.conManager.closeDB(pstmt);
				}
			}
		}
	}

	public void getDiaryList() {
		setRightPanel();
		
		String sql = "select d.DIARY_NO , d.MEMBER_NO , d.REGIST_DATE , d.REGIST_TIME , w.WEATHERTYPE as weathertype, f.FEELTYPE as feeltype, d.IMAGE , d.CONTENT";
		sql+=" from diary d, weathertype w, feeltype f where member_no=" + main.member_no;
		sql+=" and d.weathertype=w.weather_id and d.feeltype=f.feel_id order by diary_no desc";

		PreparedStatement pstmt=null;
		ResultSet rs=null;

		try {
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Diary diary=new Diary();
				diary.setDiary_no(rs.getInt("diary_no"));
				diary.setMember_no(rs.getInt("member_no"));
				diary.setRegist_date(rs.getString("regist_date"));
				diary.setRegist_time(rs.getString("regist_time"));
				diary.setWeathertype(rs.getString("weathertype"));
				diary.setFeeltype(rs.getString("feeltype"));
				diary.setImage(rs.getString("image"));
				diary.setContent(rs.getString("content"));
				
				int num=diary.getDiary_no();
				String date=diary.getRegist_date();
				String time=diary.getRegist_time();
				String wt=diary.getWeathertype();
				String ft=diary.getFeeltype();
				String path=diary.getImage();
				String diaryText=diary.getContent();
				
				ImageLabel card=new ImageLabel(FilePath.copyObjectDir+diary.getImage(), 130, 130);
				card.ifClickedNewDiaryFram(main, Page_Diary.this, num, date, time, wt, ft, path, diaryText);
				p_right.add(card);
			}
			updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			main.conManager.closeDB(pstmt, rs);
		}
	}

	// p_right�� ��� ī�� ����
	public void setRightPanel() {
		Component[] childList=p_right.getComponents();

		for(int i=0;i<childList.length;i++) {
			if(childList[i]!=la_titleR && childList[i]!=bt_lookUp) {
				p_right.remove(childList[i]);
			}
		}
		updateUI();
	}
}