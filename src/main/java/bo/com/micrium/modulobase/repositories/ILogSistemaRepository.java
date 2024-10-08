package bo.com.micrium.modulobase.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bo.com.micrium.modulobase.models.LogSistema;

/**
 *
 * @author alepaco.com
 */
@Repository
public interface ILogSistemaRepository extends JpaRepository<LogSistema, Long> {
 
    @Query(value = "select * "
            + "from log_sistema "
            + "WHERE ( (-1 = ? or TO_DATE(TO_CHAR(fecha_registro, 'YYYY-MM-DD') , 'YYYY-MM-DD') >= TO_DATE(?, 'YYYY-MM-DD'))  "
            + "AND (-1 = ? or TO_DATE(TO_CHAR(fecha_registro, 'YYYY-MM-DD') , 'YYYY-MM-DD') <= TO_DATE(?, 'YYYY-MM-DD')) ) "
            + "and (-1 = ? or cast(id as varchar(20) ) = ?) "
            + "and (-1 = ? or TO_CHAR(fecha_registro, 'dd/MM/yyyy HH24:MI:SS') like ?) "
            + "and (-1 = ? or UPPER(app) like ?) "
            + "and (-1 = ? or UPPER(proceso) like ?) "
            + "and (-1 = ? or UPPER(detalle) like ?) "
            + "and (-1 = ? or UPPER(nivel) like ?) "
            + "and (-1 = ? or UPPER(trazabilidad) like ?) ", nativeQuery = true)
    Page<LogSistema> filtrar(
            int fechaIniBool, String fechaIni, int fechaFinBool, String fechaFin,
            int queryfilterTexto, String id,
            int queryfilterTexto0, String fechaRegistro,
            int queryfilterTexto1, String app,
            int queryfilterTexto2, String proceso,
            int queryfilterTexto3, String detalle,
            int queryfilterTexto4, String nivel,
            int queryfilterTexto5, String trazabilidad,
            Pageable pageRequest);

}
