package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.CuentaPorCobrarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICuentaPorCobrarRepository extends JpaRepository<CuentaPorCobrarEntity, Long> {

    List<CuentaPorCobrarEntity> findByIdSocio(Long idSocio);

    List<CuentaPorCobrarEntity> findByIdPuesto(Long idPuesto);

    // MÉTODO REQUERIDO PARA ELIMINAR CUENTAS ASOCIADAS AL SOCIO
    void deleteByIdSocio(Long idSocio);

    boolean existsByIdSocioAndIdServicioAndPeriodo(Long idSocio, Long idServicio, String periodo);
}