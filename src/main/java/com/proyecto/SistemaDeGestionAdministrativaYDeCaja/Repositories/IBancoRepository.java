package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.BancoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBancoRepository extends JpaRepository<BancoEntity, Long> {

    Optional<BancoEntity> findByNumeroCuenta(String numeroCuenta);
}
