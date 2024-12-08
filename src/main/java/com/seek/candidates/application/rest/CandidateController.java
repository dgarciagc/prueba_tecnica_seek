package com.seek.candidates.application.rest;

import com.seek.candidates.domain.dto.CandidateDto;
import com.seek.candidates.domain.service.CandidateService;
import com.seek.candidates.utils.LibUtils;
import com.seek.candidates.utils.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/candidates")
@Log4j2
public class CandidateController {

  private CandidateService service;

  public CandidateController(CandidateService service) {
    this.service = service;
  }
  @Operation(
      summary = "Guardar candidato",
      description = "Guarda un nuevo candidato en la base de datos y retorna los detalles del candidato creado.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = CandidateDto.class)
          )
      ),
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Candidato guardado exitosamente",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultResponse.class))
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Datos inválidos en la solicitud"
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Error interno del servidor"
          )
      }
  )
  @PostMapping
  public ResponseEntity<ResultResponse<CandidateDto>> createCandidate(@Valid @RequestBody CandidateDto candidate, BindingResult result) {
    ResultResponse<CandidateDto> response = new ResultResponse<>();
    try {
      if (result.hasErrors()) {
        throw new BadRequestException(LibUtils.formatMessage(result));
      }
      response.setData(service.save(candidate));
      response.setSuccess(true);
      response.setMsg("candidato creado");
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.info("Error al actualizar candidato: {}",  e.getMessage());
      response.setSuccess(false);
      response.setMsg(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }
  @Operation(
      summary = "Actualizar candidato",
      description = "Actualiza los datos de un candidato existente identificado por su ID.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = CandidateDto.class)
          )
      ),
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Candidato actualizado exitosamente",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultResponse.class))
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Datos inválidos en la solicitud"
          ),
          @ApiResponse(
              responseCode = "404",
              description = "Candidato no encontrado"
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Error interno del servidor"
          )
      }
  )
  @PutMapping("/{id}")
  public ResponseEntity<ResultResponse<CandidateDto>> updateCandidate(@PathVariable Long id, @Valid @RequestBody CandidateDto updatedCandidate, BindingResult result) {
    ResultResponse<CandidateDto> response = new ResultResponse<>();

    try {
      if (result.hasErrors()) {
        throw new BadRequestException(LibUtils.formatMessage(result));
      }
      response.setData(service.update(id, updatedCandidate));
      response.setSuccess(true);
      response.setMsg("candidato actualizado");
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      log.info("Candidato no encontrado: {}",  e.getMessage());
      response.setSuccess(false);
      response.setMsg(e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } catch (Exception e) {
      response.setSuccess(false);
      response.setMsg(e.getMessage());
      log.info("Error al actualizar candidato: {}",  e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

    }
  }
  @Operation(
      summary = "Listar candidatos",
      description = "Retorna una lista de todos los candidatos registrados.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandidateDto.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor")
      }
  )
  @GetMapping
  public ResponseEntity<List<CandidateDto>> getAllCandidates() {
    return ResponseEntity.ok(service.getAlls());
  }
  @Operation(
      summary = "Obtener candidato por ID",
      description = "Retorna los datos de un candidato especificado por su ID.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Candidato encontrado",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandidateDto.class))),
          @ApiResponse(responseCode = "404", description = "Candidato no encontrado")
      }
  )
  @GetMapping("/{id}")
  public ResponseEntity<CandidateDto> getCandidateById(@PathVariable Long id) {
    return service.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
  @Operation(
      summary = "Eliminar candidato",
      description = "Elimina un candidato existente identificado por su ID.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Candidato eliminado exitosamente",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultResponse.class))),
          @ApiResponse(responseCode = "404", description = "Candidato no encontrado"),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor")
      }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<ResultResponse<?>> deleteCandidate(@PathVariable Long id) {
    ResultResponse<?> response = new ResultResponse<>();
    response.setSuccess(true);
    response.setMsg("Candidato eliminado");
    service.delete(id);
    return ResponseEntity.ok(response);
  }
}
