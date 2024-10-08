package bo.com.micrium.modulobase.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import bo.com.micrium.modulobase.models.Usuario;


/**
 *
 * @author alepaco.maton
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByNombreUsuario(String username);

    Usuario findByNombreUsuarioAndEstadoIn(String nombreUsuario, Collection<Short> estados);

    Page<Usuario> findAllByTipoNotAndEstadoIn(short tipo, Collection<Short> estados, Pageable pageable);

    long countByEstadoInAndRolIdId(Collection<Short> estados, Long rolId);
    
    @Query(value = "select u.* "
            + "from MU_USUARIO u inner join MU_ROL r "
            + "on u.rol_id = r.id  "
            + "and (-1 = ? or cast(u.id  as varchar(20)) = ?) "
            + "and (-1 = ? or UPPER(u.nombre_usuario) like ?) "
            + "and (-1 = ? or UPPER(u.nombre_completo) like ?) "
            + "and (-1 = ? or cast(r.id as varchar(20)) = ?) "
            + "and (-1 = ? or UPPER(r.nombre) = ?) "
            + "and (-1 = ? or cast(u.tipo  as varchar(20)) = ?) "
            + "and (-1 = ? or cast(u.estado  as varchar(20)) = ?) "
            + "and u.tipo <> ? ",
            countQuery = "select count(*) "
            + "from MU_USUARIO u inner join MU_ROL r "
            + "on u.rol_id = r.id  "
            + "and (-1 = ? or cast(u.id  as varchar(20)) = ?) "
            + "and (-1 = ? or UPPER(u.nombre_usuario) like ?) "
            + "and (-1 = ? or UPPER(u.nombre_completo) like ?) "
            + "and (-1 = ? or cast(r.id as varchar(20)) = ?) "
            + "and (-1 = ? or UPPER(r.nombre) = ?) "
            + "and (-1 = ? or cast(u.tipo  as varchar(20)) = ?) "
            + "and (-1 = ? or cast(u.estado  as varchar(20)) = ?) "
            + "and u.tipo <> ? ",
            nativeQuery = true)
    Page<Usuario> filter(
            int flag0, String id,
            int flag1, String nombreUsuario,
            int flag2, String nombreCompleto,
            int flag6, String rolId,
            int flag3, String rolNombre,
            int flag4, String tipo,
            int flag5, String estado,
            int tipoadministrador,
            Pageable pageable);

    @Query(value = "select a.* from mu_usuario a,mu_rol b where a.rol_id=b.id and a.rol_id = ?", nativeQuery = true)
    List<Usuario> listusuariosInvestigadores(Long rol_id);

    Usuario findByIdAndRolIdIdAndEstado(Long id, Long rolId, short estado);

    Usuario findByIdAndRolIdIdAndEstadoIn(Long id, Long rolId, List<Short> estados);

    Usuario findBynombreUsuario(String nombreUsuario);

}
