package pages;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import lib.FilePath;
import lib.GetDate;
import lib.SetStyle;
import main.MainDrive;
import objects.ImageLabel;
import objects.TextLabel;

public class Page_User extends Page {
	JPanel p_loginContainer;
	TextLabel la_loginText;
	TextLabel la_empty2;
	TextLabel la_id;
	JTextField t_id;
	TextLabel la_pw;
	JPasswordField t_pw;
	ImageLabel la_login;
	
	JPanel p_signupContainer;
	TextLabel la_signupText;
	TextLabel la_signupId;
	JTextField t_signupId;
	TextLabel la_signupName;
	JTextField t_signupName;
	TextLabel la_signupPw;
	JPasswordField t_signupPw;
	ImageLabel la_signup;
	
	TextLabel la_hello;
	ImageLabel la_weather;
	ImageLabel la_diary;
	ImageLabel la_todolist;
	ImageLabel la_store;
	ImageLabel la_logout;
	
	Connection con;
	Page_Diary diaryPage;
	Page_Todo todoPage;
	Page_Recommend recPage;

	public Page_User(MainDrive main, Page_Diary diaryPage, Page_Todo todoPage, Page_Recommend recPage, String title, String bgPath, boolean showFlag) {
		super(main, title, bgPath, showFlag);

		this.diaryPage=diaryPage;
		this.todoPage=todoPage;
		this.recPage=recPage;
		this.con=main.con;
		
		p_loginContainer=new JPanel();
		setLogoutStatePanel();
		this.label.add(p_loginContainer);
	}
	
	// 로그아웃 상태의 패널 세팅
	public void setLogoutStatePanel() {
		p_loginContainer.removeAll();
		
		la_loginText=new TextLabel("로그인", 350, 70, 25);
		la_id=new TextLabel("아이디 ", 120, 50, 20);
		t_id=new JTextField();
		la_pw=new TextLabel("비밀번호 ", 120, 85, 20);
		t_pw=new JPasswordField();
		la_login=new ImageLabel(FilePath.buttonDir+"login.png", 330, 50);
		
		p_signupContainer=new JPanel();
		la_signupText=new TextLabel("회원가입", 350, 50, 25);
		la_signupName=new TextLabel("이름 ", 120, 50, 20);
		t_signupName=new JTextField();
		la_signupId=new TextLabel("아이디 ", 120, 50, 20);
		t_signupId=new JTextField();
		la_signupPw=new TextLabel("비밀번호 ", 120, 50, 20);
		t_signupPw=new JPasswordField();
		la_signup=new ImageLabel(FilePath.buttonDir+"signup.png", 330, 50);
		
		SetStyle.setPanelStyle(p_loginContainer, 40, 200, 450, 300);
		t_id.setPreferredSize(new Dimension(200, 40));
		t_pw.setPreferredSize(new Dimension(200, 40));
		SetStyle.setPanelStyle(p_signupContainer, 510, 200, 450, 300);
		t_signupName.setPreferredSize(new Dimension(200, 40));
		t_signupId.setPreferredSize(new Dimension(200, 40));
		t_signupPw.setPreferredSize(new Dimension(200, 40));
		
		p_loginContainer.add(la_loginText);
		p_loginContainer.add(la_id);
		p_loginContainer.add(t_id);
		p_loginContainer.add(la_pw);
		p_loginContainer.add(t_pw);
		p_loginContainer.add(la_login);
		
		p_signupContainer.add(la_signupText);
		p_signupContainer.add(la_signupName);
		p_signupContainer.add(t_signupName);
		p_signupContainer.add(la_signupId);
		p_signupContainer.add(t_signupId);
		p_signupContainer.add(la_signupPw);
		p_signupContainer.add(t_signupPw);
		p_signupContainer.add(la_signup);
		
		this.label.add(p_signupContainer);
		
		la_login.ifEnteredSetImage(FilePath.buttonDir+"login_select.png", this);
		la_signup.ifEnteredSetImage(FilePath.buttonDir+"signup_select.png", this);
		la_login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				loginCheck();
			}
		});
		la_signup.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				signUp();
			}
		});
	}
	
	// 로그인 버튼 클릭 시 로그인 체크
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
				JOptionPane.showMessageDialog(this, "로그인 되었습니다.");
				showMyInfo(user_name);
				
				pageReSet();
			} else {
				main.loginFlag=false;
				JOptionPane.showMessageDialog(this, "로그인에 실패했습니다.\n아이디/비밀번호를 확인해주세요.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			main.conManager.closeDB(pstmt, rs);
		}
	}
	
	//  로그인 성공 시 마이페이지 띄우기
	public void showMyInfo(String name) {
		p_loginContainer.removeAll();
		this.label.remove(p_signupContainer);
		
		la_hello=new TextLabel(name+"님, 반갑습니다!", 350, 150, 22);
		la_weather=new ImageLabel(FilePath.buttonDir+"weather.png", 150, 150);
		la_diary=new ImageLabel(FilePath.buttonDir+"diary.png", 150, 150);
		la_todolist=new ImageLabel(FilePath.buttonDir+"todo.png", 150, 150);
		la_store=new ImageLabel(FilePath.buttonDir+"recommend.png", 150, 150);
		la_empty2=new TextLabel("", 300, 30, 0);
		la_logout=new ImageLabel(FilePath.buttonDir+"logout.png", 300, 50);
		
		SetStyle.setPanelStyle(p_loginContainer, 300, 20, 400, 650);
		
		la_logout.ifEnteredSetImage(FilePath.buttonDir+"logout_select.png", this);
		la_logout.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				logout();
			}
		});
		
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
		
		la_weather.ifEnteredSetImage(FilePath.buttonDir+"weather_select.png", this);
		la_diary.ifEnteredSetImage(FilePath.buttonDir+"diary_select.png", this);
		la_todolist.ifEnteredSetImage(FilePath.buttonDir+"todo_select.png", this);
		la_store.ifEnteredSetImage(FilePath.buttonDir+"recommend_select.png", this);
		
		updateUI();
	}
	
	public void logout() {
		int result=JOptionPane.showConfirmDialog(this, "로그아웃하시겠습니까?");
		if(result==JOptionPane.OK_OPTION) {
			main.loginFlag=false;
			main.member_no=0;

			setLogoutStatePanel();
			updateUI();
			JOptionPane.showMessageDialog(this, "로그아웃되었습니다.");
			
			pageReSet();
		}
	}
	
	public void pageReSet() {
		for (int i = 0; i < diaryPage.la_weatherImages.length; i++) {
			diaryPage.la_weatherImages[i].setImage(FilePath.oriIconDir + i + ".png");
		}
		for (int i = 0; i < diaryPage.la_feelImages.length; i++) {
			diaryPage.la_feelImages[i].setImage(FilePath.oriIconDir + i + ".png");
		}
		
		diaryPage.getDiaryList();
		diaryPage.area.setText("");
		diaryPage.thumbIcon = new ImageIcon(FilePath.buttonDir+"thumb.png");
		diaryPage.la_thumb.repaint();
		diaryPage.imageName="";
		
		todoPage.load();
		todoPage.buttons[0].setSelected(true);
	}
	
	public void signUp() {
		if(t_signupName.getText().equals("") || t_signupId.getText().equals("") || new String(t_signupPw.getPassword()).equals("")) {
			JOptionPane.showMessageDialog(this, "모든 항목을 입력해주세요");
		} else {
			int result=JOptionPane.showConfirmDialog(this, "회원가입하시겠습니까?");
			if(result==JOptionPane.OK_OPTION) {
				String sql="insert into member(member_no, member_id, member_passwd, member_name)";
				sql+=" values(seq_member.nextval, ?, ?, ?)";
				
				PreparedStatement pstmt=null;
				try {
					con.setAutoCommit(false);
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, t_signupId.getText());
					pstmt.setString(2, new String(t_signupPw.getPassword()));
					pstmt.setString(3, t_signupName.getText());
					
					int resultSql=pstmt.executeUpdate();
					con.commit();
					if(resultSql!=0) {
						JOptionPane.showMessageDialog(this, "회원가입되었습니다.\n로그인 후 이용해주세요.");
						t_signupName.setText("");
						t_signupId.setText("");
						t_signupPw.setText("");
					} else {
						JOptionPane.showMessageDialog(this, "에러가 발생했습니다.\n확인 후 다시 시도해주세요");
					}
				} catch (SQLException e) {
					try {
						con.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				} finally {
					main.conManager.closeDB(pstmt);
				}
			}
		}
	}
}