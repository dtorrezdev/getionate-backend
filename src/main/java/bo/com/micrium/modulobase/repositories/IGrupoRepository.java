package bo.com.micrium.modulobase.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bo.com.micrium.modulobase.models.Grupo;
import bo.com.micrium.modulobase.models.Rol;

/**
 *
 * @author alepaco.maton
 */
@Repository
public interface IGrupoRepository extends JpaRepository<Grupo, Long> {

    Page<Grupo> findAllByEstadoTrue(Pageable pagination);

    Grupo findByNombreAndEstadoTrue(String nombre);

    List<Grupo> findAllByRolIdAndEstadoTrue(Rol rol_Id);

    long countByEstadoTrueAndRolId(Rol rol_Id);

    @Query(value = "select g.* "
            + "from mu_grupo_ad g inner join mu_rol r "
            + "on g.rol_id = r.id and g.estado= 1 "            
            + "and (-1 = ? or UPPER(g.nombre) like ?) "
            + "and (-1 = ? or UPPER(g.descripcion) like ?) "
            + "and (-1 = ? or UPPER(r.nombre) like ?) ",
            countQuery = "select count(*) "
            + "from mu_grupo_ad g inner join mu_rol r "
            + "on g.rol_id = r.id and g.estado= 1 "
            + "and (-1 = ? or UPPER(g.nombre) like ?) "
            + "and (-1 = ? or UPPER(g.descripcion) like ?) "
            + "and (-1 = ? or UPPER(r.nombre) like ?) ",
            nativeQuery = true)
    Page<Grupo> filter(int flag1, String nombre,
            int flag2, String descripcion,
            int flag3, String rolNombre,
            Pageable pageable);
}
