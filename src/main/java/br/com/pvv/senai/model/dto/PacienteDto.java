package br.com.pvv.senai.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.pvv.senai.entity.Paciente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PacienteDto extends GenericDto<Paciente> {

	private long id;
	@NotNull
	@Size(min = 8, max = 64)
	private String name;
	@NotNull
	private String gender;
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private Date birthDate;
	@NotNull
	@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
	private String CPF;
	@NotNull
	@Size(max = 20)
	private String RG;
	@NotNull
	private String maritalStatus;
	@NotNull
	@Pattern(regexp = "\\(\\d{2}\\) \\d{1} \\d{4}-\\d{4}")
	private String phone;
	@Email
	private String email;
	@NotNull
	@Size(min = 8, max = 64)
	private String birthCity;
	@NotNull
	@Pattern(regexp = "\\(\\d{2}\\) \\d{1} \\d{4}-\\d{4}")
	private String emergencyContact;
	private String allergies;
	private String specialCare;
	private String insuranceCompany;
	private String insuranceNumber;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	private Date insuranceExpiration;
	private EnderecoDto address;

	@Override
	protected Class<Paciente> getType() {
		return Paciente.class;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getSpecialCare() {
		return specialCare;
	}

	public void setSpecialCare(String specialCare) {
		this.specialCare = specialCare;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public Date getInsuranceExpiration() {
		return insuranceExpiration;
	}

	public void setInsuranceExpiration(Date insuranceExpiration) {
		this.insuranceExpiration = insuranceExpiration;
	}

	public EnderecoDto getAddress() {
		return address;
	}

	public void setAddress(EnderecoDto address) {
		this.address = address;
	}

}
