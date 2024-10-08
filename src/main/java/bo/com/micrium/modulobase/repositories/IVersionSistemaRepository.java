package bo.com.micrium.modulobase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bo.com.micrium.modulobase.models.VersionSistema;

/**
 *
 * @author alepaco.com
 */
@Repository
public interface IVersionSistemaRepository extends JpaRepository<VersionSistema, String> {
    
}
