package bo.com.micrium.modulobase.services;

import java.util.List;

import bo.com.micrium.modulobase.models.Usuario;

public interface IUserService {

    List<Usuario> findAll();

    Usuario save(Usuario user);
}
