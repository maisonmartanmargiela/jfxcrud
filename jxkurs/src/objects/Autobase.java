package objects;

public class Autobase {
	private Integer id_autobase;
	private String number_autobase;
	private String address_autobase;
	private Integer id_auto;
	private Integer id_gas;
	
	public Autobase(Integer id_autobase, String number_autobase, String address_autobase, Integer id_auto,
			Integer id_gas) {
		this.id_autobase = id_autobase;
		this.number_autobase = number_autobase;
		this.address_autobase = address_autobase;
		this.id_auto = id_auto;
		this.id_gas = id_gas;
	}

	public Integer getId_autobase() {
		return id_autobase;
	}

	public void setId_autobase(Integer id_autobase) {
		this.id_autobase = id_autobase;
	}

	public String getNumber_autobase() {
		return number_autobase;
	}

	public void setNumber_autobase(String number_autobase) {
		this.number_autobase = number_autobase;
	}

	public String getAddress_autobase() {
		return address_autobase;
	}

	public void setAddress_autobase(String address_autobase) {
		this.address_autobase = address_autobase;
	}

	public Integer getId_auto() {
		return id_auto;
	}

	public void setId_auto(Integer id_auto) {
		this.id_auto = id_auto;
	}

	public Integer getId_gas() {
		return id_gas;
	}

	public void setId_gas(Integer id_gas) {
		this.id_gas = id_gas;
	}

	@Override
	public String toString() {
		return "Autobase [id_autobase=" + id_autobase + ", number_autobase=" + number_autobase + ", address_autobase="
				+ address_autobase + ", id_auto=" + id_auto + ", id_gas=" + id_gas + "]";
	}
	
	
	
	
}
