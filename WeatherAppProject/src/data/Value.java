package data;

public class Value {
	private String fcstDate;
	private String fcstTime;
	private int pop;			// ����Ȯ�� %
	private int pty;				// �������� �ڵ尪	(0 : ���� / 1 : �� / 2 : ��+�� / 3 : �� / 4 : �ҳ���)
	private int reh;				// ���� %
	private int sky;				// �ϴû��� �ڵ尪	(1 : ���� / 3 : �������� / 4 : �帲) -> 2�� ����
	private int t3h;				// 3�ð� ��� ��
	private double tmn;		// ��ħ ������� ��	(6�ÿ� ��ǥ)
	private double tmx;		// �� �ְ��� ��		(15�ÿ� ��ǥ)
	private double wsd;		// ǳ�� m/s
	
	public String getFcstDate() {
		return fcstDate;
	}
	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}
	public String getFcstTime() {
		return fcstTime;
	}
	public void setFcstTime(String fcstTime) {
		this.fcstTime = fcstTime;
	}
	public int getPop() {
		return pop;
	}
	public void setPop(int pop) {
		this.pop = pop;
	}
	public int getPty() {
		return pty;
	}
	public void setPty(int pty) {
		this.pty = pty;
	}
	public int getReh() {
		return reh;
	}
	public void setReh(int reh) {
		this.reh = reh;
	}
	public int getSky() {
		return sky;
	}
	public void setSky(int sky) {
		this.sky = sky;
	}
	public int getT3h() {
		return t3h;
	}
	public void setT3h(int t3h) {
		this.t3h = t3h;
	}
	public double getTmn() {
		return tmn;
	}
	public void setTmn(double tmn) {
		this.tmn = tmn;
	}
	public double getTmx() {
		return tmx;
	}
	public void setTmx(double tmx) {
		this.tmx = tmx;
	}
	public double getWsd() {
		return wsd;
	}
	public void setWsd(double wsd) {
		this.wsd = wsd;
	}
}
