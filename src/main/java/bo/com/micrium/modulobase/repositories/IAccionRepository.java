
package bo.com.micrium.modulobase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import bo.com.micrium.modulobase.models.Accion;

@Repository
public interface IAccionRepository extends JpaRepository<Accion, Long> {

    List<Accion> findByFormularioId(Long formularioId);

    List<Accion> findByFormularioIdIsNull(Sort sort);
    
    List<Accion> findByFormularioId(Long moduloId, Sort sort);

    //Formulario findByNombreAndEstadoTrue(String nombre);
    Accion findByNombre(String nombre);

    //Page<Formulario> findAllByIdNotAndEstadoTrue(Long id, Pageable pageable);
    Page<Accion> findAllById(Long id, Pageable pageable);

    @Query(value = "select * "
            + "from MU_ACCION "
            + "where "
            + "(-1 = ? or UPPER(formulario_id) like ?) "
            + "and (-1 = ? or UPPER(nombre) like ?) "
            + "and (-1 = ? or UPPER(url) like ?) "
            + "and (-1 = ? or UPPER(metodo) like ?) ", nativeQuery = true)
           // + "and estado = 1 and id not in (?) ", nativeQuery = true)
    Page<Accion> filter(int flag1, String formularioId,
            int flag2, String nombre,
            int flag3, String url,
            int flag4, String metodo,
           //  Long idroladministrador,
            Pageable pageable);
}
