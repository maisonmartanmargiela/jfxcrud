package objects;

public class Auto {
    private Integer id_auto;
	private String firm_auto;
	private String brand_auto;
	private String license_plate_auto;
	private String gas_grade_auto;
	
	
	public Auto(Integer id_auto, String firm_auto, String brand_auto, String license_plate_auto, String gas_grade_auto) 
    {
		this.id_auto = id_auto;
		this.firm_auto = firm_auto;
		this.brand_auto = brand_auto;
		this.license_plate_auto = license_plate_auto;
		this.gas_grade_auto = gas_grade_auto;
	}


	public Integer getId_auto() {
		return id_auto;
	}


	public void setId_auto(Integer id_auto) {
		this.id_auto = id_auto;
	}


	public String getFirm_auto() {
		return firm_auto;
	}


	public void setFirm_auto(String firm_auto) {
		this.firm_auto = firm_auto;
	}


	public String getBrand_auto() {
		return brand_auto;
	}


	public void setBrand_auto(String brand_auto) {
		this.brand_auto = brand_auto;
	}


	public String getLicense_plate_auto() {
		return license_plate_auto;
	}


	public void setLicense_plate_auto(String license_plate_auto) {
		this.license_plate_auto = license_plate_auto;
	}


	public String getGas_grade_auto() {
		return gas_grade_auto;
	}


	public void setGas_grade_auto(String gas_grade_auto) {
		this.gas_grade_auto = gas_grade_auto;
	}


	@Override
	public String toString() {
		return "Auto [id_auto=" + id_auto + ", firm_auto=" + firm_auto + ", brand_auto=" + brand_auto
				+ ", license_plate_auto=" + license_plate_auto + ", gas_grade_auto=" + gas_grade_auto + "]";
	}
	
	
	
	
}   

