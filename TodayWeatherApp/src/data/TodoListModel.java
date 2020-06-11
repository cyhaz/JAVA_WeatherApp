package data;



import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TodoListModel  extends AbstractTableModel{
	
	public ArrayList<TodoList> list = new ArrayList<TodoList>();
	String[] column = {"NO","����","����", "���� ����"};
	
	public int getRowCount() {
		return list.size();
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int col) {
		return column[col];
	}
	
	public boolean isCellEditable(int row, int col) {
		boolean flag = true;
		if(col==0) {
			flag=false;
		}
		return flag;
	}
	
	public void setValueAt(Object value, int row, int col) {		
		//����Ʈ�� ������ ����
		TodoList todolist = (TodoList)list.get(row); 
		if(col==1) {
			todolist.getStatusTable().setStatus((String)value);			
		}else if(col==2){
			todolist.setContent((String)value);
		}else if(col==3){
			todolist.setDuedate((String)value);
		}
	}

	
	public Object getValueAt(int row, int col) {
		TodoList todoList = list.get(row);
		String data = null;
		if(col==0) {
			data=Integer.toString(todoList.getTodolist_no());
		}else if(col==1) {
			data = todoList.getStatusTable().getStatus();
		}else if(col==2) {
			data=todoList.getContent();
		}else if(col==3) {
			data=todoList.getDuedate();
		}
		
		return data;
	}
}
