package bo.com.micrium.modulobase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.com.micrium.modulobase.models.Accion;

/**
 *
 * @author alepaco.maton
 */
@Repository
public interface IAccionRepository extends JpaRepository<Accion, Long> {

    List<Accion> findByFormularioId(Long formularioId);
}
