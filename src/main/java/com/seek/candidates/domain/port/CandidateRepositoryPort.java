package com.seek.candidates.domain.port;


import com.seek.candidates.domain.model.CandidateModel;

import java.util.List;
import java.util.Optional;

public interface CandidateRepositoryPort {
  CandidateModel save(CandidateModel candidate);
  CandidateModel update(Long id, CandidateModel updatedCandidate);
  List<CandidateModel> getAlls();
  Optional<CandidateModel> findById(Long id);
  void delete(Long id);
}