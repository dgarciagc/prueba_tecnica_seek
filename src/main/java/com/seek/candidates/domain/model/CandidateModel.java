package com.seek.candidates.domain.model;

import com.seek.candidates.utils.GenderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidate")
public class CandidateModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(unique = true)
  private String email;

  @Enumerated(EnumType.STRING) // Indica que se mapeará como un ENUM de tipo STRING
  private GenderEnum gender;

  private BigDecimal salaryExpected;
}
