package bo.com.micrium.modulobase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

import bo.com.micrium.modulobase.models.Rol;

/**
 *
 * @author alepaco.maton
 */
@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {

    Rol findByNombreAndEstadoTrue(String nombre);

    Page<Rol> findAllByIdNotAndEstadoTrue(Long id, Pageable pageable);

    @Query(value = "select * "
            + "from MU_ROL "
            + "where "
            + "(-1 = ? or UPPER(nombre) like ?) "
            + "and (-1 = ? or UPPER(descripcion) like ?) "
            + "and estado = 1 and id not in (?) ", nativeQuery = true)
    Page<Rol> filter(int flag1, String nombre,
            int flag2, String descripcion, Long idroladministrador,
            Pageable pageable);

}
