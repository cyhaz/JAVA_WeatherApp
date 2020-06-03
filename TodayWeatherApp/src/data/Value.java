package data;

public class Value {
	private int t1h; // 1시간 기온 ℃
	private int yesterdayT1h; // 어제 기온 ℃
	private int pop; // 강수확률 %
	private int pty; // 강수형태 코드값 (0 : 없음 / 1 : 비 / 2 : 비+눈 / 3 : 눈 / 4 : 소나기)
	private int reh; // 습도 %
	private int sky; // 하늘상태 코드값 (1 : 맑음 / 3 : 구름많음 / 4 : 흐림) -> 2는 없음
	private int t3h; // 3시간 기온 ℃
	private int tmn; // 아침 최저기온 ℃ (6시에 발표)
	private int tmx; // 낮 최고기온 ℃ (15시에 발표)
	private double wsd; // 풍속 m/s

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