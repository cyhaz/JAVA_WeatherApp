package pages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import data.StatusTable;
import data.TodoList;
import data.TodoListModel;
import lib.GetDate;
import lib.SetStyle;
import main.MainDrive;
import objects.TextLabel;

public class Page_Todo extends Page implements ActionListener {
	JPanel p_container; // 메인 패널
	TextLabel la_title; // "To-do-List"
	JRadioButton ch_today;
	JRadioButton ch_tomorrow;
	JRadioButton ch_afterday;
	ButtonGroup checkGroup;
	JTextField t_write; // 입력 공간
	JButton bt_regist; // 등록 버튼
	JButton bt_edit; // 수정 버튼
	JButton bt_delete; // 삭제 버튼
	JButton bt_lookup; // 조회 버튼

	JTable table;
	TodoListModel model;
	JScrollPane scroll;
	JComboBox<String> statusBox; // list의 상태 표시 콤보박스

	Connection con;

	String dueDate = GetDate.date_today;
	int todolist_no;
	int row;

	public Page_Todo(MainDrive main, String title, String bgPath, boolean showFlag) {
		super(main, title, bgPath, showFlag);

		p_container = new JPanel();
		la_title = new TextLabel("To-Do-List", 800, 50, 30);
		ch_today = new JRadioButton("오늘");
		ch_tomorrow = new JRadioButton("내일");
		ch_afterday = new JRadioButton("모레");
		checkGroup = new ButtonGroup();
		t_write = new JTextField(60);
		bt_regist = new JButton("등록");
		bt_edit = new JButton("수정");
		bt_delete = new JButton("삭제");
		bt_lookup = new JButton("나의 To-do");
		model = new TodoListModel();
		table = new JTable(model);
		scroll = new JScrollPane(table);

		con = main.conManager.getConnection();
		checkGroup.add(ch_today);
		checkGroup.add(ch_tomorrow);
		checkGroup.add(ch_afterday);

		TableColumn comm = table.getColumnModel().getColumn(1);
		statusBox = new JComboBox();
		statusBox.addItem("할 일");
		statusBox.addItem("진행 중");
		statusBox.addItem("완료");
		comm.setCellEditor(new DefaultCellEditor(statusBox));

		// style
		table.setRowHeight(20);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		scroll.setPreferredSize(new Dimension(700, 300));
//		scroll.setBackground(Color.WHITE);

		ch_today.setBackground(new Color(182, 219, 242));
		ch_today.setSelected(true);
		ch_tomorrow.setBackground(new Color(182, 219, 242));
		ch_afterday.setBackground(new Color(182, 219, 242));

//		if(ch_today.isSelected()) dueDate=GetDate.date_today;
//		else if(ch_tomorrow.isSelected()) dueDate=GetDate.date_tomorrow;
//		else if(ch_afterday.isSelected()) dueDate=GetDate.date_afterDay;

//		load();

		p_container.add(la_title);
		p_container.add(ch_today);
		p_container.add(ch_tomorrow);
		p_container.add(ch_afterday);
		p_container.add(t_write);
		p_container.add(bt_regist);
		p_container.add(bt_lookup);
		p_container.add(bt_edit);
		p_container.add(bt_delete);
		p_container.add(scroll);

		SetStyle.setPanelStyle(p_container, 100, 90, 800, 500);

		this.label.add(p_container);

		setDueDate();
		bt_regist.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_delete.addActionListener(this);
		bt_lookup.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				row = table.getSelectedRow();
				String value = (String) table.getValueAt(row, 0);
				todolist_no = Integer.parseInt(value); // 클릭시마다 선택한 todolist_no를 보관함
			}
		});

	}

	public void setDueDate() {
		JRadioButton[] buttons = { ch_today, ch_tomorrow, ch_afterday };
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getSource() == ch_today) {
						dueDate = GetDate.date_today;
					} else if (e.getSource() == ch_tomorrow) {
						dueDate = GetDate.date_tomorrow;
					} else if (e.getSource() == ch_afterday) {
						dueDate = GetDate.date_afterDay;
					}
					load();
				}
			});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(bt_regist)) {
			insertTodo();
		} else if (obj.equals(bt_edit)) {
			editTodo();
		} else if (obj.equals(bt_delete)) {
			deleteTodo();
		} else if (obj.equals(bt_lookup)) {
			loadAll();
		}
	}

	// 테이블 조회 (하루치)
	public void load() {
		StringBuilder sql = new StringBuilder();
		sql.append("select todolist.todolist_no, status.status");
		sql.append(", todolist.content, todolist.duedate");
		sql.append(" from todolist, status");
		sql.append(" where todolist.status_no=status.status_no");
		sql.append(" and todolist.member_no=?");
		sql.append(" and todolist.duedate=?");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, main.member_no);
			pstmt.setString(2, dueDate);

			rs = pstmt.executeQuery();
			ArrayList<TodoList> list = new ArrayList<TodoList>();

			while (rs.next()) {
				StatusTable statusTable = new StatusTable();
				TodoList todolist = new TodoList();

				todolist.setStatusTable(statusTable);
				todolist.setTodolist_no(rs.getInt("todolist_no"));
				statusTable.setStatus(rs.getString("status"));
				todolist.setContent(rs.getString("content"));
				todolist.setDuedate(rs.getString("duedate"));

				list.add(todolist);
			}
			model.list = list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			main.conManager.closeDB(pstmt, rs);
		}
		table.updateUI();
	}

	// 테이블 조회 (전체)
	public void loadAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select todolist.todolist_no, status.status");
		sql.append(", todolist.content, todolist.duedate");
		sql.append(" from todolist, status");
		sql.append(" where todolist.status_no=status.status_no");
		sql.append(" and todolist.member_no=?");
		sql.append(" order by todolist.todolist_no asc");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, main.member_no);

			rs = pstmt.executeQuery();
			ArrayList<TodoList> list = new ArrayList<TodoList>();

			while (rs.next()) {
				StatusTable statusTable = new StatusTable();
				TodoList todolist = new TodoList();

				todolist.setStatusTable(statusTable);

				todolist.setTodolist_no(rs.getInt("todolist_no"));
				statusTable.setStatus(rs.getString("status"));
				todolist.setContent(rs.getString("content"));
				todolist.setDuedate(rs.getString("duedate"));

				list.add(todolist);
			}
			model.list = list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			main.conManager.closeDB(pstmt, rs);
		}
		table.updateUI();
	}

	// 할 일 등록
	public void insertTodo() {
		String sql = "insert into todolist(todolist_no, member_no, status_no, content, duedate)";
		sql += " values(seq_todolist.nextval,?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, main.member_no);
			pstmt.setInt(2, 1); // todolist 상태는 "할일"

			if (t_write.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "내용을 입력해주세요");
			} else {
				pstmt.setString(3, t_write.getText());
			}
			pstmt.setString(4, dueDate);

			int result = pstmt.executeUpdate();
			con.commit();
			if (result == 0) {
				JOptionPane.showMessageDialog(this, "등록실패");
			} else {
				JOptionPane.showMessageDialog(this, "등록 성공");
				load();
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

	// 할 일 수정
	public void editTodo() {
		if (todolist_no == 0) {
			JOptionPane.showMessageDialog(this, "수정하실 레코드를 선택하세요");
			return;
		}
		int result = JOptionPane.showConfirmDialog(this, "수정하시겠습니까?");
		if (result == JOptionPane.OK_OPTION) {
			String sql = "update todolist set status_no=(select status_no from status where status.status=?), content=?, duedate=? where todolist_no="
					+ todolist_no;

			int success = 0;
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, (String) table.getValueAt(row, 1));
				pstmt.setString(2, (String) table.getValueAt(row, 2));
				pstmt.setString(3, (String) table.getValueAt(row, 3));
				success = pstmt.executeUpdate();
				con.commit();
				if (success == 0) {
					JOptionPane.showMessageDialog(this, "수정 실패");
				} else {
					JOptionPane.showMessageDialog(this, "수정 성공");
					load();
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

	// 할 일 삭제
	public void deleteTodo() {
		// 체크사항 1. 선택한 레코드가 있는지 여부 2. 삭제할 의지 확인
		if (todolist_no == 0) {
			JOptionPane.showMessageDialog(this, "삭제하실 레코드를 선택하세요");
			return;
		}

		// 삭제할 자격이 생기면
		int result = JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?");
		if (result == JOptionPane.OK_OPTION) {

			String sql = "delete from todolist where todolist_no=" + todolist_no;

			int success = 0;
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(sql);
				success = pstmt.executeUpdate();
				con.commit();
				if (success == 0) {
					JOptionPane.showMessageDialog(this, "삭제에 실패했습니다.\n다시 시도해주세요.");
				} else {
					JOptionPane.showMessageDialog(this, "삭제 성공");
					load();
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
