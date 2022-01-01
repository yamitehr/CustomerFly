package util;

public class OrderDetail {

	public String PassPort;
	public int morning;
	public int noon;
	public int evening;
	public int night;
	public OrderDetail(String passPort, int morning, int noon, int evening, int night) {
		super();
		PassPort = passPort;
		this.morning = morning;
		this.noon = noon;
		this.evening = evening;
		this.night = night;
	}
	public OrderDetail() {
		// TODO Auto-generated constructor stub
	}
	
}
