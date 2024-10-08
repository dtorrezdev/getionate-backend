package bo.com.micrium.modulobase.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import bo.com.micrium.modulobase.models.Parametro;

/**
 *
 * @author alepaco.maton
 */
@Repository
public interface IParametroRepository extends JpaRepository<Parametro, Long> {
 
    Page<Parametro> findAllByTipoParametroIdIn(Collection<Long> tipoParametroIds, Pageable pageable);
    
    List<Parametro> findAllByTipoParametroId(Long tipoParametroId);

    
    Parametro findByNombre(String nombre);
    
    @Query(value = "select p.* "
            + "from MU_PARAMETRO p "
            + "where p.tipo_parametro_id = ?  "
            + "and (-1 = ? or UPPER(p.nombre) like ?) "
            + "and (-1 = ? or UPPER(p.valor) like ?) "
            + "and (-1 = ? or UPPER(p.descripcion) like ?) ", nativeQuery = true)
    Page<Parametro> filter(Long tipo_parametro_id,
    					int flag1, String nombre,
    					int flag2,String valor,
			    		int flag3,String descripcion,
			    		Pageable pageable);
    
}
