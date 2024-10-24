package bo.com.micrium.modulobase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import bo.com.micrium.modulobase.models.Formulario;
//import bo.com.micrium.modulobase.models.Rol;

/**
 *
 * @author alepaco.maton
 */
@Repository
public interface IFormularioRepository extends JpaRepository<Formulario, Long> {

    List<Formulario> findByModuloIdIsNull(Sort sort);
    
    List<Formulario> findByModuloId(Long moduloId, Sort sort);

    //Formulario findByNombreAndEstadoTrue(String nombre);
    Formulario findByNombre(String nombre);

    //Page<Formulario> findAllByIdNotAndEstadoTrue(Long id, Pageable pageable);
    Page<Formulario> findAllById(Long id, Pageable pageable);

    @Query(value = "select * "
            + "from MU_FORMULARIO "
            + "where "
            + "(-1 = ? or UPPER(nombre) like ?) "
            + "and (-1 = ? or UPPER(orden) like ?) "
            + "and (-1 = ? or UPPER(modulo_id) like ?) "
            + "and (-1 = ? or UPPER(url) like ?) "
            + "and (-1 = ? or UPPER(icono) like ?) ", nativeQuery = true)
           // + "and estado = 1 and id not in (?) ", nativeQuery = true)
    Page<Formulario> filter(int flag1, String nombre,
            int flag2, String orden,
            int flag3, String moduloId,
            int flag4, String url,
            int flag5, String icono,
           //  Long idroladministrador,
            Pageable pageable);
}
