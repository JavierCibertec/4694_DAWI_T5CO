package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.GiroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGiroRepository extends JpaRepository<GiroEntity, Long> {
}