package data;

public class Diary {
	private int diaryno;
	private int user_id;
	private String regist_date;
	private String regist_time;
	private int weathertype;
	private int feeltype;
	private String image;
	private String content;
	
	public int getDiaryno() {
		return diaryno;
	}
	public void setDiaryno(int diaryno) {
		this.diaryno = diaryno;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getRegist_date() {
		return regist_date;
	}
	public void setRegist_date(String regist_date) {
		this.regist_date = regist_date;
	}
	public String getRegist_time() {
		return regist_time;
	}
	public void setRegist_time(String regist_time) {
		this.regist_time = regist_time;
	}
	public int getWeathertype() {
		return weathertype;
	}
	public void setWeathertype(int weathertype) {
		this.weathertype = weathertype;
	}
	public int getFeeltype() {
		return feeltype;
	}
	public void setFeeltype(int feeltype) {
		this.feeltype = feeltype;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
