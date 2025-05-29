package br.com.fiap.checkpoint3.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.checkpoint3.dto.PacienteCreateRequest;
import br.com.fiap.checkpoint3.dto.PacienteResponse;
import br.com.fiap.checkpoint3.dto.PacienteUpdateRequest;
import br.com.fiap.checkpoint3.service.PacienteService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteResponse> createPaciente(@RequestBody PacienteCreateRequest dto) {

        return ResponseEntity.status(201).body(new PacienteResponse().toDto(pacienteService.createPaciente(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> updatePaciente(@PathVariable Long id, @RequestBody PacienteUpdateRequest dto) {

        return pacienteService.updatePaciente(id, dto)
                .map(updatePaciente -> new PacienteResponse().toDto(updatePaciente))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        if (pacienteService.deletePaciente(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> getPacienteById(@PathVariable Long id) {
        return pacienteService.getPacienteById(id)
                .map(paciente -> new PacienteResponse().toDto(paciente))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> getAllPacientes() {
        List<PacienteResponse> pacientes = pacienteService.getAll()
                .stream()
                .map(paciente -> new PacienteResponse().toDto(paciente))
                .collect(Collectors.toList());

        return ResponseEntity.ok(pacientes);
    }
}
