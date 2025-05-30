package br.com.fiap.checkpoint3.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.fiap.checkpoint3.dto.Paciente.PacienteCreateRequest;
import br.com.fiap.checkpoint3.dto.Paciente.PacienteUpdateRequest;
import br.com.fiap.checkpoint3.model.Paciente;

@Service
public class PacienteService {

    private List<Paciente> pacientes; 
    private long sequence = 1L; 

    public Paciente create(PacienteCreateRequest dto) {
        Paciente paciente = new Paciente(); 
        paciente.setId(sequence++); 
        paciente.setNome(dto.getNome()); 
        paciente.setEndereco(dto.getEndereco()); 
        paciente.setBairro(dto.getBairro()); 
        paciente.setEmail(dto.getEmail()); 
        paciente.setTelefoneCompleto(dto.getTelefoneCompleto()); 
        pacientes.add(paciente); 
        return paciente; 
    }

    public Optional<Paciente> getById(@PathVariable Long id) {
        return pacientes.stream() 
                .filter(paciente -> paciente.getId().equals(id)) 
                .findFirst(); 
    }

    public List<Paciente> getAll() {
        return pacientes;
    }

    public Optional<Paciente> update(Long id, PacienteUpdateRequest dto) {
        return pacientes.stream() 
                .filter(paciente -> paciente.getId().equals(id)) 
                .findFirst() 
                .map(paciente -> {
                    paciente.setEndereco(dto.getEndereco()); 
                    paciente.setBairro(dto.getBairro()); 
                    paciente.setEmail(dto.getEmail()); 
                    paciente.setTelefoneCompleto(dto.getTelefoneCompleto()); 
                    return paciente; 
                });
    }

    public boolean delete(@PathVariable Long id) {
        return pacientes.removeIf(paciente -> paciente.getId().equals(id)); 
    }
}