package com.seek.candidates.domain.port;


import com.seek.candidates.domain.dto.CandidateDto;
import com.seek.candidates.domain.model.CandidateModel;

import java.util.List;
import java.util.Optional;

public interface CandidateRepositoryPort {
  CandidateDto save(CandidateDto candidate);
  CandidateDto update(Long id, CandidateDto updatedCandidate);
  List<CandidateDto> getAlls();
  Optional<CandidateDto> findById(Long id);
  void delete(Long id);
}