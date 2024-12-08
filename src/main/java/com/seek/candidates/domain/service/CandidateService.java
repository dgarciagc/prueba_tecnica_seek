package com.seek.candidates.domain.service;

import com.seek.candidates.domain.dto.CandidateDto;
import com.seek.candidates.domain.model.CandidateModel;
import com.seek.candidates.domain.port.CandidateRepositoryPort;
import com.seek.candidates.infraestructure.repository.JpaCandidateRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class CandidateService implements CandidateRepositoryPort {

  private final JpaCandidateRepositoryAdapter candidateRepository;

  public CandidateService(JpaCandidateRepositoryAdapter candidateRepository) {
    this.candidateRepository = candidateRepository;
  }

  @Override
  public CandidateDto save(CandidateDto candidate) {
    CandidateModel savedModel = candidateRepository.save(candidate.toEntity());
    // Validar que el resultado no sea nulo
    if (Objects.isNull(savedModel)) {
      throw new NullPointerException("El candidato guardado no puede ser nulo.");
    }
    return savedModel.toDto();
  }

  @Override
  public CandidateDto update(Long id, CandidateDto updatedCandidate) {
    return candidateRepository.findById(id)
        .map(existingCandidate -> {
          // Actualiza los campos necesarios
          existingCandidate.setName(updatedCandidate.getNombre());
          existingCandidate.setEmail(updatedCandidate.getCorreo());
          existingCandidate.setGender(updatedCandidate.getGenero());
          existingCandidate.setSalaryExpected(updatedCandidate.getSalario());
          // Guarda los cambios
          return candidateRepository.save(existingCandidate)
              .toDto();
        })
        .orElseThrow(() -> new IllegalArgumentException("Candidato no encontrado con ID: " + id));
  }

  @Override
  public List<CandidateDto> getAlls() {
    return candidateRepository.findAll().stream()
        .map(CandidateModel::toDto)
        .toList();
  }
  @Override
  public Optional<CandidateDto> findById(Long id) {
    return candidateRepository.findById(id).map(CandidateModel::toDto);
  }
  @Override
  public void delete(Long id) {
    candidateRepository.deleteById(id);
  }
}
