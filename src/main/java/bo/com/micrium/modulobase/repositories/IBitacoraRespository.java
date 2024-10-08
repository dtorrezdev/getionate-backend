package bo.com.micrium.modulobase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import bo.com.micrium.modulobase.models.Bitacora;

/**
 *
 * @author alepaco.maton
 */
@Repository
public interface IBitacoraRespository extends JpaRepository<Bitacora, Long> {

    /*@Query(value = "select * "
            + "from MU_BITACORA u "
            + "where (((-1 = ? or u.fecha >= ?) and (-1 = ? or u.fecha <= ?)) and (-1 = ? or (to_char(u.fecha, 'DD/MM/YYYY HH24:MI:SS') LIKE ?))) "
            + "and (-1 = ? or UPPER(accion) like ?) "
            + "and (-1 = ? or UPPER(direccion_ip) like ?) "
            + "and (-1 = ? or UPPER(formulario) like ?) "
            + "and (-1 = ? or UPPER(usuario) like ?)", nativeQuery = true)
    Page<Bitacora> filter(int fechaIniBool, Date fechaIni, int fechaFinBool, Date fechaFin, int fechaBool, String fecha,
            int accionBool, String accion, int direccionIpBool, String direccionIp, int formularioBool, String formulario,
            int usuarioBool, String usuario, Pageable pageable);*/
    @Query(value = "select * "
            + "from MU_BITACORA u "
            + "WHERE ( (-1 = ? or TO_DATE(TO_CHAR(u.FECHA, 'YYYY-MM-DD') , 'YYYY-MM-DD') >= TO_DATE(?, 'YYYY-MM-DD'))  "
            + "AND (-1 = ? or TO_DATE(TO_CHAR(u.FECHA, 'YYYY-MM-DD') , 'YYYY-MM-DD') <= TO_DATE(?, 'YYYY-MM-DD')) ) "
            + "AND (-1 = ? or TO_CHAR(u.FECHA, 'DD/MM/YYYY HH24:MI:SS') like ?) "
            + "and (-1 = ? or UPPER(accion) like ?) "
            + "and (-1 = ? or UPPER(direccion_ip) like ?) "
            + "and (-1 = ? or UPPER(formulario) like ?) "
            + "and (-1 = ? or UPPER(usuario) like ?)", nativeQuery = true)
    Page<Bitacora> filter(int fechaIniBool, String fechaIni, int fechaFinBool, String fechaFin, int fechaBool, String fecha, int accionBool, String accion,
            int direccionIpBool, String direccionIp, int formularioBool, String formulario, int usuarioBool, String usuario, Pageable pageable);

    Page<Bitacora> findByIdAndAccion(Long id, String accion, Pageable pageable);

}
