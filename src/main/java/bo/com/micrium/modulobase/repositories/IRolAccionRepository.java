package bo.com.micrium.modulobase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.com.micrium.modulobase.models.RolAccion;

@Repository
public interface IRolAccionRepository extends JpaRepository<RolAccion, Long> {
    
    List<RolAccion> findAllByRolId(Long rolId);

    RolAccion findByRolIdAndAccionId(Long rolId, Long accionId);

    List<RolAccion> findByAccionId(Long accionId);
    
}
