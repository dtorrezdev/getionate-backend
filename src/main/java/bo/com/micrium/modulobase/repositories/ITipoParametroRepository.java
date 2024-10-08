package bo.com.micrium.modulobase.repositories;

import bo.com.micrium.modulobase.models.TipoParametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alepaco.maton
 */
@Repository
public interface ITipoParametroRepository extends JpaRepository<TipoParametro, Long> {

    TipoParametro findByNombre(String nombre);

}
