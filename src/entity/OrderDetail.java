package entity;

public class OrderDetail {

	private String passid;
	private String FName;
	private String LName;
	private int morning = 0;
	private int noon = 0;
	private int evening = 0;
	private int night = 0;
	
	public OrderDetail(String passid, String fName, String lName, int morning, int noon, int evening, int night) {
		super();
		this.passid = passid;
		FName = fName;
		LName = lName;
		this.morning = morning;
		this.noon = noon;
		this.evening = evening;
		this.night = night;
	}

	public String getPassid() {
		return passid;
	}

	public void setPassid(String passid) {
		this.passid = passid;
	}

	public String getFName() {
		return FName;
	}

	public void setFName(String fName) {
		FName = fName;
	}

	public String getLName() {
		return LName;
	}

	public void setLName(String lName) {
		LName = lName;
	}

	public int getMorning() {
		return morning;
	}

	public void setMorning(int morning) {
		this.morning = morning;
	}

	public int getNoon() {
		return noon;
	}

	public void setNoon(int noon) {
		this.noon = noon;
	}

	public int getEvening() {
		return evening;
	}

	public void setEvening(int evening) {
		this.evening = evening;
	}

	public int getNight() {
		return night;
	}

	public void setNight(int night) {
		this.night = night;
	}

	@Override
	public String toString() {
		return "OrderDetail [passid=" + passid + ", FName=" + FName + ", LName=" + LName + ", morning=" + morning
				+ ", noon=" + noon + ", evening=" + evening + ", night=" + night + "]";
	}
	
	
	
	
}
