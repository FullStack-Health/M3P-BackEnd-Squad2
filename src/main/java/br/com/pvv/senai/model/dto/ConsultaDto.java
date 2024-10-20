package br.com.pvv.senai.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.pvv.senai.entity.Consulta;
import br.com.pvv.senai.model.dto.annotations.SkipMakeEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ConsultaDto extends GenericDto<Consulta> {

	private long Id;
	@NotNull
	@Size(min = 8, max = 64)
	private String reason;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	@NotNull
	@DateTimeFormat(pattern = "hh:mm:ss")
	private LocalTime time;
	@NotNull
	@Size(min = 16, max = 1024)
	private String issueDescription;
	private String prescribedMedication;
	@Size(min = 16, max = 256)
	private String observation;

	@SkipMakeEntity
	private PacienteDto patient;

	@NotNull
	@SkipMakeEntity
	private int patientId;

	@Override
	protected Class<Consulta> getType() {
		return Consulta.class;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public String getPrescribedMedication() {
		return prescribedMedication;
	}

	public void setPrescribedMedication(String prescribedMedication) {
		this.prescribedMedication = prescribedMedication;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public PacienteDto getPatient() {
		return patient;
	}

	public void setPatient(PacienteDto patient) {
		this.patient = patient;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

}
