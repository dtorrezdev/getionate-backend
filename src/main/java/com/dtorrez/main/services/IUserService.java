package com.dtorrez.main.services;

import java.util.List;

import com.micrium.bd.access.jpa.modulo.administracion.models.Usuario;

public interface IUserService {

    List<Usuario> findAll();

    Usuario save(Usuario user);
}
