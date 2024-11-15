package bo.com.micrium.modulobase.services;

import java.util.List;

import com.micrium.bd.access.jpa.models.Usuario;

public interface IUserService {

    List<Usuario> findAll();

    Usuario save(Usuario user);
}
