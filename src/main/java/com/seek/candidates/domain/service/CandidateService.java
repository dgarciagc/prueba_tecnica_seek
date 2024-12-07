package com.seek.candidates.domain.service;

import com.seek.candidates.domain.model.CandidateModel;
import com.seek.candidates.domain.port.CandidateRepositoryPort;
import com.seek.candidates.infraestructure.repository.JpaCandidateRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CandidateService implements CandidateRepositoryPort {

  private final JpaCandidateRepositoryAdapter candidateRepository;

  public CandidateService(JpaCandidateRepositoryAdapter candidateRepository) {
    this.candidateRepository = candidateRepository;
  }

  @Override
  public CandidateModel save(CandidateModel candidate) {
    return candidateRepository.save(candidate);
  }

  @Override
  public CandidateModel update(Long id, CandidateModel updatedCandidate) {
    return candidateRepository.findById(id)
        .map(existingCandidate -> {
          // Actualiza los campos necesarios
          existingCandidate.setName(updatedCandidate.getName());
          existingCandidate.setEmail(updatedCandidate.getEmail());
          existingCandidate.setGender(updatedCandidate.getGender());
          existingCandidate.setSalaryExpected(updatedCandidate.getSalaryExpected());
          // Guarda los cambios
          return candidateRepository.save(existingCandidate);
        })
        .orElseThrow(() -> new IllegalArgumentException("Candidato no encontrado con ID: " + id));
  }

  @Override
  public List<CandidateModel> getAlls() {
    return candidateRepository.findAll();
  }

  @Override
  public Optional<CandidateModel> findById(Long id) {
    return candidateRepository.findById(id);
  }

  @Override
  public void delete(Long id) {
    candidateRepository.deleteById(id);
  }
}
