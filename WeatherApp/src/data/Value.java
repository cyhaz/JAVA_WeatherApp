package data;

public class Value {
	private int t1h; // 1�ð� ��� ��
	private int yesterdayT1h; // ���� ��� ��
	private int pop; // ����Ȯ�� %
	private int pty; // �������� �ڵ尪 (0 : ���� / 1 : �� / 2 : ��+�� / 3 : �� / 4 : �ҳ���)
	private int reh; // ���� %
	private int sky; // �ϴû��� �ڵ尪 (1 : ���� / 3 : �������� / 4 : �帲) -> 2�� ����
	private int t3h; // 3�ð� ��� ��
	private int tmn; // ��ħ ������� �� (6�ÿ� ��ǥ)
	private int tmx; // �� �ְ��� �� (15�ÿ� ��ǥ)
	private double wsd; // ǳ�� m/s

	public int getT1h() {
		return t1h;
	}

	public void setT1h(int t1h) {
		this.t1h = t1h;
	}

	public int getYesterdayT1h() {
		return yesterdayT1h;
	}

	public void setYesterdayT1h(int yesterdayT1h) {
		this.yesterdayT1h = yesterdayT1h;
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

	public int getTmn() {
		return tmn;
	}

	public void setTmn(int tmn) {
		this.tmn = tmn;
	}

	public int getTmx() {
		return tmx;
	}

	public void setTmx(int tmx) {
		this.tmx = tmx;
	}

	public double getWsd() {
		return wsd;
	}

	public void setWsd(double wsd) {
		this.wsd = wsd;
	}
}