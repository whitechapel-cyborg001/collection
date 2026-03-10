package net.vys.collection.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import net.vys.collection.entities.User;
import net.vys.collection.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Buscamos el usuario en la base de datos
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                    "Usuario no encontrado: " + username
                ));

        // Convertimos nuestro User en un UserDetails que entiende Spring Security
        // Spring usará este objeto para verificar contraseña y roles
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())   // Ya viene hasheado de la BD
                .roles(user.getRole())          // Spring añade "ROLE_" automáticamente
                .build();
    }
}

/*
## El flujo de los tres juntos
```
Spring Security necesita verificar un usuario
            │
            ▼
  UserDetailsServiceImpl.loadUserByUsername()
            │
            ▼
  UserRepository.findByUsername()  →  Consulta la BD
            │
            ▼
  Devuelve un UserDetails que Spring ya entiende
   */