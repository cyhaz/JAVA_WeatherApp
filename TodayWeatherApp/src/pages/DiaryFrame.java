package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import lib.FilePath;
import main.ConManager;
import objects.ImageLabel;
import objects.TextLabel;

public class DiaryFrame extends JFrame {
	ImageLabel p_image;
	JPanel p_contents;
	JLabel la_timeText;
	JLabel la_weatherType;
	JTextArea t_content;
	JScrollPane scroll;
	ImageLabel bt_delete;

	int width = 300;
	int height = 400;

	String weatherType = null;
	String feelType = null;
	String imagePath;
	Page_Diary page_diary;

	public DiaryFrame(Page_Diary page_diary, int num, String date, String time, String weatherType, String feelType, String imagepath,
			String content) {
		super("Diary");
		this.page_diary=page_diary;
		this.imagePath=imagepath;

		p_image = new ImageLabel(FilePath.copyObjectDir + imagepath, width, 180);
		p_contents = new JPanel();
		la_timeText = new TextLabel(String.format("%s %s 업로드", date, time), 280, 20, 12);
		la_weatherType = new TextLabel(String.format("#오늘날씨 #%s #오늘기분 #%s", weatherType, feelType), 280, 20, 13);
		t_content = new JTextArea(5, 25);
		scroll = new JScrollPane(t_content);
		bt_delete = new ImageLabel(FilePath.buttonDir + "delete.png", 30, 30);

		la_timeText.setHorizontalAlignment(JLabel.LEFT);
		la_weatherType.setHorizontalAlignment(JLabel.LEFT);
		la_timeText.setForeground(Color.gray);
		la_weatherType.setForeground(Color.blue);
		p_contents.setBackground(Color.white);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		t_content.setFocusable(false);
		t_content.setText(content);
		t_content.setLineWrap(true);

		add(p_image, BorderLayout.NORTH);
		add(p_contents);
		p_contents.add(la_timeText);
		p_contents.add(la_weatherType);
		p_contents.add(scroll);
		p_contents.add(bt_delete);

		setVisible(true);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);

		bt_delete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(DiaryFrame.this, "삭제하시겠습니까?");
				if (result == JOptionPane.OK_OPTION) {
					delete(num);
				}
			}
		});
	}

	public void delete(int diary_no) {
		String sql = "delete from diary where diary_no=" + diary_no;
		ConManager conManager=ConManager.getInstance();
		Connection con = conManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			int row = pstmt.executeUpdate();
			if (row == 0)
				JOptionPane.showMessageDialog(this, "삭제 실패\n다시 시도해주세요.");
			else {
				JOptionPane.showMessageDialog(this, "삭제되었습니다.");
				new File(FilePath.copyObjectDir+imagePath).delete();
				dispose();
				page_diary.getDiaryList();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conManager.closeDB(pstmt);
			conManager.closeDB(con);
		}
	}
}