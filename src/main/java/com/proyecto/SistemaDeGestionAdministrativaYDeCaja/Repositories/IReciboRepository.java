package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.ReciboEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IReciboRepository extends JpaRepository<ReciboEntity, Long> {

    @Query("SELECT MAX(r.correlativo) FROM ReciboEntity r")
    Optional<Long> findUltimoCorrelativo();
}