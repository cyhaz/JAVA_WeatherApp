package pages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import lib.FilePath;
import lib.SetStyle;
import main.MainDrive;
import objects.ImageLabel;
import objects.TextLabel;

public class Page_User extends Page {
	JPanel p_loginContainer;
	TextLabel la_empty;
	TextLabel la_empty2;
	TextLabel la_id;
	JTextField t_id;
	TextLabel la_pw;
	JPasswordField t_pw;
	ImageLabel la_login;
	
	TextLabel la_hello;
	ImageLabel la_weather;
	ImageLabel la_diary;
	ImageLabel la_todolist;
	ImageLabel la_store;
	ImageLabel la_logout;
	
	Connection con;

	public Page_User(MainDrive main, String title, String bgPath, boolean showFlag) {
		super(main, title, bgPath, showFlag);
		
		con=main.con;
		p_loginContainer=new JPanel();
		setLogoutStatePanel();
		this.label.add(p_loginContainer);
	}
	
	// �α׾ƿ� ������ �г� ����
	public void setLogoutStatePanel() {
		la_empty=new TextLabel("", 400, 200, 0);
		la_id=new TextLabel("���̵� ", 120, 50, 20);
		t_id=new JTextField();
		la_pw=new TextLabel("��й�ȣ ", 120, 110, 20);
		t_pw=new JPasswordField();
		la_login=new ImageLabel(FilePath.buttonDir+"login.png", 330, 50);
		
		SetStyle.setPanelStyle(p_loginContainer, 300, 20, 400, 650);
		t_id.setPreferredSize(new Dimension(200, 40));
		t_pw.setPreferredSize(new Dimension(200, 40));
		
		p_loginContainer.add(la_empty);
		p_loginContainer.add(la_id);
		p_loginContainer.add(t_id);
		p_loginContainer.add(la_pw);
		p_loginContainer.add(t_pw);
		p_loginContainer.add(la_login);
		
		la_login.ifEnteredSetImage(FilePath.buttonDir+"login_select.png");
		la_login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				loginCheck();
			}
		});
	}
	
	// �α��� ��ư Ŭ�� �� �α��� üũ
	public void loginCheck() {
		String sql="select * from member where member_id=? and member_passwd=?";
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, t_id.getText());
			pstmt.setString(2, new String(t_pw.getPassword()));
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				main.loginFlag=true;
				main.member_no=rs.getInt(1);
				main.member_id=rs.getString(2);
				String user_name=rs.getString(4);
				JOptionPane.showMessageDialog(this, "�α��� �Ǿ����ϴ�.");
				showMyInfo(user_name);
			} else {
				main.loginFlag=false;
				JOptionPane.showMessageDialog(this, "�α��ο� �����߽��ϴ�.\n���̵�/��й�ȣ�� Ȯ�����ּ���.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			main.conManager.closeDB(pstmt, rs);
		}
	}
	
	//  �α��� ���� �� ���������� ����
	public void showMyInfo(String name) {
		p_loginContainer.removeAll();
		p_loginContainer.setBackground(Color.white);
		
		la_empty=new TextLabel("", 300, 50, 0);
		la_hello=new TextLabel(name+"��, �ݰ����ϴ�!", 350, 100, 22);
		la_weather=new ImageLabel(FilePath.buttonDir+"weather.png", 150, 150);
		la_diary=new ImageLabel(FilePath.buttonDir+"diary.png", 150, 150);
		la_todolist=new ImageLabel(FilePath.buttonDir+"todo.png", 150, 150);
		la_store=new ImageLabel(FilePath.buttonDir+"recommend.png", 150, 150);
		la_empty2=new TextLabel("", 300, 30, 0);
		la_logout=new ImageLabel(FilePath.buttonDir+"logout.png", 300, 50);
		
		la_logout.ifEnteredSetImage(FilePath.buttonDir+"logout_select.png");
		la_logout.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				logout();
			}
		});
		
		p_loginContainer.add(la_empty);
		p_loginContainer.add(la_hello);
		p_loginContainer.add(la_weather);
		p_loginContainer.add(la_diary);
		p_loginContainer.add(la_todolist);
		p_loginContainer.add(la_store);
		p_loginContainer.add(la_empty2);
		p_loginContainer.add(la_logout);
		
		p_loginContainer.updateUI();
		
		la_weather.ifClickedGoPage(2);
		la_diary.ifClickedGoPage(0);
		la_todolist.ifClickedGoPage(1);
		la_store.ifClickedGoPage(4);
	}
	
	public void logout() {
		int result=JOptionPane.showConfirmDialog(this, "�α׾ƿ��Ͻðڽ��ϱ�?");
		if(result==JOptionPane.OK_OPTION) {
			main.loginFlag=false;
			p_loginContainer.removeAll();
			setLogoutStatePanel();
			updateUI();
			JOptionPane.showMessageDialog(this, "�α׾ƿ��Ǿ����ϴ�.");
		}
	}
}