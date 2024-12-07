package com.seek.candidates.infraestructure.repository;

import com.seek.candidates.domain.model.CandidateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCandidateRepositoryAdapter extends JpaRepository<CandidateModel, Long> {
}