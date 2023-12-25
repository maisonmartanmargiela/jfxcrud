package objects;

public class Gas_station {
	private Integer id_gas;
	private String name_gas;
	private String address_gas;
	private String gas_grade_gas;
	
	public Gas_station(Integer id_gas, String name_gas, String address_gas, String gas_grade_gas) {
		this.id_gas = id_gas;
		this.name_gas = name_gas;
		this.address_gas = address_gas;
		this.gas_grade_gas = gas_grade_gas;
	}

	public Integer getId_gas() {
		return id_gas;
	}

	public void setId_gas(Integer id_gas) {
		this.id_gas = id_gas;
	}

	public String getName_gas() {
		return name_gas;
	}

	public void setName_gas(String name_gas) {
		this.name_gas = name_gas;
	}

	public String getAddress_gas() {
		return address_gas;
	}

	public void setAddress_gas(String address_gas) {
		this.address_gas = address_gas;
	}

	public String getGas_grade_gas() {
		return gas_grade_gas;
	}

	public void setGas_grade_gas(String gas_grade_gas) {
		this.gas_grade_gas = gas_grade_gas;
	}

	@Override
	public String toString() {
		return "Gas_station [id_gas=" + id_gas + ", name_gas=" + name_gas + ", address_gas=" + address_gas
				+ ", gas_grade_gas=" + gas_grade_gas + "]";
	}
	
	
	
	
}
