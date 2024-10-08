package bo.com.micrium.modulobase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import bo.com.micrium.modulobase.models.Formulario;

/**
 *
 * @author alepaco.maton
 */
@Repository
public interface IFormularioRepository extends JpaRepository<Formulario, Long> {

    List<Formulario> findByModuloIdIsNull(Sort sort);
    
    List<Formulario> findByModuloId(Long moduloId, Sort sort);
}
