package br.com.pvv.senai.model.dto;

import br.com.pvv.senai.entity.Consulta;
import br.com.pvv.senai.entity.Exame;
import br.com.pvv.senai.entity.Paciente;
import br.com.pvv.senai.entity.Paciente;

import java.util.List;

public class ProntuarioDto {
    private Paciente paciente;
    private List<Exame> exames;
    private List<Consulta> consultas;


    public ProntuarioDto(Paciente paciente, List<Exame> exames, List<Consulta> consultas) {
        this.paciente = paciente;
        this.exames = exames;
        this.consultas = consultas;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public void setExames(List<Exame> exames) {
        this.exames = exames;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
}
