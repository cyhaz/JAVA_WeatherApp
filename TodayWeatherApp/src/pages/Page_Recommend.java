package pages;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.RecommendItem;
import lib.SetStyle;
import main.MainDrive;
import objects.ImageLabel;
import objects.TextLabel;

public class Page_Recommend extends Page {
	JPanel p_container; // 메인 패널
	TextLabel la_title; // "Recommend Place"
	int widthPlus=20;
	
	Connection con;
	
	public Page_Recommend(MainDrive main, String title, String bgPath, boolean showFlag) {
		super(main, title, bgPath, showFlag);
		
		con=main.con;
		
		p_container=new JPanel(null);
		la_title=new TextLabel("Recommend Place", 850, 70, 30);
		la_title.setBounds(0, 0, 850, 70);
		
		SetStyle.setPanelStyle(p_container, 70, 100, 860, 480);
		
		p_container.add(la_title);
		this.label.add(p_container);
		
	}
	
	public void getPlaceList(String weatherName, String location) {
		setContainerPanel();
		
		String sql="select id, name, address, phone, image from store where id IN "
				+ "(select store_id from recommend"
				+ " where weather_id = (select id from weather where name = ?)"
				+ " and location_id in (select id from location where FIRST_SEP =?))";
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, weatherName);
			pstmt.setString(2, location);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				RecommendItem item=new RecommendItem();
				item.setId(rs.getInt("id"));
				item.setImage(rs.getString("image"));
				item.setName(rs.getString("name"));
				item.setAddress(rs.getString("address"));
				item.setPhone(rs.getString("phone"));
				
				JPanel card=createPanel(item.getImage(), item.getName(), item.getAddress(), item.getPhone());
				p_container.add(card);
			}
			updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			main.conManager.closeDB(pstmt, rs);
		}
	}
	
	// p_container의 모든 카드 삭제
	public void setContainerPanel() {
		Component[] childList=p_container.getComponents();

		for(int i=0;i<childList.length;i++) {
			if(childList[i]!=la_title) {
				p_container.remove(childList[i]);
			}
		}
		updateUI();
		widthPlus=20;
	}
	
	public JPanel createPanel(String imgPath, String name, String address, String phone) {
		JPanel card=new JPanel();
		SetStyle.setPanelStyle(card, widthPlus, 80, 200, 350);
		widthPlus+=210;
		URL url;
		try {
			url = new URL(imgPath);
			ImageIcon icon=new ImageIcon(url);
			Image image=icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			icon=new ImageIcon(image);
			JLabel img=new JLabel(icon);
			img.setPreferredSize(new Dimension(150, 150));
			TextLabel la_name=new TextLabel(name, 170, 50, 17);
			TextLabel la_address=new TextLabel(address, 170, 50, 12);
			TextLabel la_phone=new TextLabel(phone, 170, 50, 12);
			card.add(img);
			card.add(la_name);
			card.add(la_address);
			card.add(la_phone);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return card;
	}
}