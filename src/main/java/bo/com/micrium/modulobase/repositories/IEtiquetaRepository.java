package bo.com.micrium.modulobase.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import bo.com.micrium.modulobase.models.Etiqueta;

/**
 *
 * @author alepaco.maton
 */
@Repository
public interface IEtiquetaRepository extends JpaRepository<Etiqueta, Long> {

    Page<Etiqueta> findAllByEstadoTrue(Pageable pagination);

    List<Etiqueta> findAllByLlaveIn(Collection<String> llaves);

    List<Etiqueta> findAllByGrupoIn(Collection<String> grupos);

    Etiqueta findByGrupoAndLlave(String grupo, String llave);
    
    @Query(value = "select * "
            + "from MU_ETIQUETA "
            + "where  "
            + "(-1 = ? or UPPER(llave) like ?) "
            + "and (-1 = ? or UPPER(valor) like ?) "
            + "and (-1 = ? or UPPER(grupo) like ?) ", nativeQuery = true)
    Page<Etiqueta> filter(int flag1, String llave,
    					int flag2,String valor,
			    		int flag3,String grupo,
			    		Pageable pageable);

}
