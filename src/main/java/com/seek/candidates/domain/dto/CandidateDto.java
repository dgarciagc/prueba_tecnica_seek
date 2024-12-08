package com.seek.candidates.domain.dto;

import com.seek.candidates.domain.model.CandidateModel;
import com.seek.candidates.utils.GenderEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDto {
  private Long id;
  @NotNull(message = "Nombre es obligatorio")
  private String nombre;
  @NotNull(message = "Correo es obligatorio")
  @Email(message = "El formato del correo es erroneo")
  private String correo;
  @NotNull(message = "Genero es obligatorio")
  private GenderEnum genero;
  @NotNull(message = "Salario es obligatorio")
  @DecimalMin(value = "0.01", inclusive = true, message = "El salario debe ser mayor a cero")

  private BigDecimal salario;
  public CandidateModel toEntity() {
    CandidateModel candidateModel = new CandidateModel();
    candidateModel.setId(this.id);
    candidateModel.setName(this.nombre);
    candidateModel.setEmail(this.correo);
    candidateModel.setGender(this.genero);
    candidateModel.setSalaryExpected(this.salario);
    return candidateModel;
  }
}
