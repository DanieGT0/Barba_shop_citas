package com.barberapp.service;

import com.barberapp.entity.Usuario;
import com.barberapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String oauth2Id = oauth2User.getAttribute("sub") != null ?
            oauth2User.getAttribute("sub") : oauth2User.getAttribute("id");
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        Optional<Usuario> existingUser = usuarioRepository
            .findByOauth2IdAndOauth2Provider(oauth2Id, registrationId);

        Usuario usuario;
        if (existingUser.isPresent()) {
            usuario = existingUser.get();
        } else {
            Optional<Usuario> userByEmail = usuarioRepository.findByEmail(email);
            if (userByEmail.isPresent()) {
                usuario = userByEmail.get();
                usuario.setOauth2Id(oauth2Id);
                usuario.setOauth2Provider(registrationId);
            } else {
                usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setUsername(email);
                usuario.setNombre(extractFirstName(name));
                usuario.setApellido(extractLastName(name));
                usuario.setOauth2Id(oauth2Id);
                usuario.setOauth2Provider(registrationId);
                usuario.setTipo(Usuario.TipoUsuario.CLIENTE);
            }
            usuarioRepository.save(usuario);
        }

        return oauth2User;
    }

    private String extractFirstName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Usuario";
        }
        String[] nameParts = fullName.trim().split("\\s+");
        return nameParts[0];
    }

    private String extractLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }
        String[] nameParts = fullName.trim().split("\\s+");
        if (nameParts.length > 1) {
            return String.join(" ", java.util.Arrays.copyOfRange(nameParts, 1, nameParts.length));
        }
        return "";
    }
}