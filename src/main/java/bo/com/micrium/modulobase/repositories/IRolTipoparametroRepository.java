package bo.com.micrium.modulobase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.com.micrium.modulobase.models.RolTipoParametroPermiso;

/**
 *
 * @author alepaco.maton
 */
@Repository
public interface IRolTipoparametroRepository extends JpaRepository<RolTipoParametroPermiso, Long> {

    List<RolTipoParametroPermiso> findAllByRolId(Long rolId);

    RolTipoParametroPermiso findByRolIdAndTipoParametroId(Long rolId, Long tipoParametroId);
    
}

