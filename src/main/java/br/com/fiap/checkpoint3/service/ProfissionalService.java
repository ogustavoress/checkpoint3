package br.com.fiap.checkpoint3.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.fiap.checkpoint3.dto.profissional.ProfissionalCreateRequest;
import br.com.fiap.checkpoint3.dto.profissional.ProfissionalUpdateRequest;
import br.com.fiap.checkpoint3.model.Profissional;
import br.com.fiap.checkpoint3.repository.ProfissionalRepository;

@Service
public class ProfissionalService {

    @Autowired
    ProfissionalRepository profissionalRepository;

    public Profissional create(ProfissionalCreateRequest dto){
        Profissional profissional = dto.toModel(); 
        LocalDateTime now = LocalDateTime.now();
        profissional.setCreatedAt(now);
        profissional.setUpdatedAt(now);
        return profissionalRepository.save(profissional); 
    }

    public Optional<Profissional> getProfissionalById(@PathVariable Long id){
        return profissionalRepository.findById(id);
    }

    public List<Profissional> getAll(){
        return profissionalRepository.findAll();
    }

    public Optional<Profissional> update(Long id, ProfissionalUpdateRequest dto){
        return profissionalRepository.findById(id).map(profissional ->{
                profissional.setUpdatedAt(LocalDateTime.now());
                return profissionalRepository.save(dto.toModel(profissional));
            });
    }

    public boolean delete(@PathVariable Long id){
        if (profissionalRepository.findById(id).isPresent()) {
            profissionalRepository.deleteById(id);
            return true;
        }
        throw new RuntimeException("Profissional inexistente: ID " + id);
    }

    

}
