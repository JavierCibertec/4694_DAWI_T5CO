package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.PuestoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPuestoRepository extends JpaRepository<PuestoEntity, Long> {

    List<PuestoEntity> findByIdGiro(Long idGiro);

    List<PuestoEntity> findByIdSocio(Long idSocio);
}
