package br.com.pvv.senai.model;

public class Prontuario {

	private long id;
	private String name;
	private String insuranceCompany;

	private String phone;

	public Prontuario(long id, String name, String insuranceCompany, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.insuranceCompany = insuranceCompany;
		this.phone = phone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setinsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
