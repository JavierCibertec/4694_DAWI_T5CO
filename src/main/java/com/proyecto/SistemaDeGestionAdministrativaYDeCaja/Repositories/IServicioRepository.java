package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServicioRepository extends JpaRepository<ServicioEntity, Long> {
}