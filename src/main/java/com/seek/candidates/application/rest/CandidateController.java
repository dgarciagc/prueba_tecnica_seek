package com.seek.candidates.application.rest;

import com.seek.candidates.domain.model.CandidateModel;
import com.seek.candidates.domain.service.CandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

  private CandidateService service;

  public CandidateController(CandidateService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<CandidateModel> createCandidate(@RequestBody CandidateModel candidate) {
    return ResponseEntity.ok(service.save(candidate));
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> updateCandidate(@PathVariable Long id, @RequestBody CandidateModel updatedCandidate) {
    try {
      return ResponseEntity.ok(service.update(id, updatedCandidate));
    } catch (IllegalArgumentException e) {
      System.err.println("Candidato no encontrado: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      System.err.println("Error al actualizar candidato: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al actualizar el candidato.");
    }
  }
  @GetMapping
  public ResponseEntity<List<CandidateModel>> getAllCandidates() {
    return ResponseEntity.ok(service.getAlls());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CandidateModel> getCandidateById(@PathVariable Long id) {
    return service.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
