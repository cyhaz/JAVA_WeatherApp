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
	JPanel p_container; // ���� �г�
	TextLabel la_title; // "To-do-List"
	JRadioButton ch_today;
	JRadioButton ch_tomorrow;
	JRadioButton ch_afterday;
	ButtonGroup checkGroup;
	JTextField t_write; // �Է� ����
	JButton bt_regist; // ��� ��ư
	JButton bt_edit; // ���� ��ư
	JButton bt_delete; // ���� ��ư
	JButton bt_lookup; // ��ȸ ��ư

	JTable table;
	TodoListModel model;
	JScrollPane scroll;
	JComboBox<String> statusBox; // list�� ���� ǥ�� �޺��ڽ�

	Connection con;

	String dueDate = GetDate.date_today;
	int todolist_no;
	int row;

	public Page_Todo(MainDrive main, String title, String bgPath, boolean showFlag) {
		super(main, title, bgPath, showFlag);

		p_container = new JPanel();
		la_title = new TextLabel("To-Do-List", 800, 50, 30);
		ch_today = new JRadioButton("����");
		ch_tomorrow = new JRadioButton("����");
		ch_afterday = new JRadioButton("��");
		checkGroup = new ButtonGroup();
		t_write = new JTextField(60);
		bt_regist = new JButton("���");
		bt_edit = new JButton("����");
		bt_delete = new JButton("����");
		bt_lookup = new JButton("���� To-do");
		model = new TodoListModel();
		table = new JTable(model);
		scroll = new JScrollPane(table);

		con = main.conManager.getConnection();
		checkGroup.add(ch_today);
		checkGroup.add(ch_tomorrow);
		checkGroup.add(ch_afterday);

		TableColumn comm = table.getColumnModel().getColumn(1);
		statusBox = new JComboBox();
		statusBox.addItem("�� ��");
		statusBox.addItem("���� ��");
		statusBox.addItem("�Ϸ�");
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
				todolist_no = Integer.parseInt(value); // Ŭ���ø��� ������ todolist_no�� ������
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

	// ���̺� ��ȸ (�Ϸ�ġ)
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

	// ���̺� ��ȸ (��ü)
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

	// �� �� ���
	public void insertTodo() {
		String sql = "insert into todolist(todolist_no, member_no, status_no, content, duedate)";
		sql += " values(seq_todolist.nextval,?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, main.member_no);
			pstmt.setInt(2, 1); // todolist ���´� "����"

			if (t_write.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "������ �Է����ּ���");
			} else {
				pstmt.setString(3, t_write.getText());
			}
			pstmt.setString(4, dueDate);

			int result = pstmt.executeUpdate();
			con.commit();
			if (result == 0) {
				JOptionPane.showMessageDialog(this, "��Ͻ���");
			} else {
				JOptionPane.showMessageDialog(this, "��� ����");
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

	// �� �� ����
	public void editTodo() {
		if (todolist_no == 0) {
			JOptionPane.showMessageDialog(this, "�����Ͻ� ���ڵ带 �����ϼ���");
			return;
		}
		int result = JOptionPane.showConfirmDialog(this, "�����Ͻðڽ��ϱ�?");
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
					JOptionPane.showMessageDialog(this, "���� ����");
				} else {
					JOptionPane.showMessageDialog(this, "���� ����");
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

	// �� �� ����
	public void deleteTodo() {
		// üũ���� 1. ������ ���ڵ尡 �ִ��� ���� 2. ������ ���� Ȯ��
		if (todolist_no == 0) {
			JOptionPane.showMessageDialog(this, "�����Ͻ� ���ڵ带 �����ϼ���");
			return;
		}

		// ������ �ڰ��� �����
		int result = JOptionPane.showConfirmDialog(this, "�����Ͻðڽ��ϱ�?");
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
					JOptionPane.showMessageDialog(this, "������ �����߽��ϴ�.\n�ٽ� �õ����ּ���.");
				} else {
					JOptionPane.showMessageDialog(this, "���� ����");
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
