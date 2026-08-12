package com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.Implementation;

import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Entities.UsuarioEntity;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Mappers.UsuarioMapper;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Models.UsuarioModel;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Repositories.IUsuarioRepository;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Security.JwtUtil;
import com.proyecto.SistemaDeGestionAdministrativaYDeCaja.Services.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final JwtUtil jwtUtil;

    @Override
    public UsuarioModel login(String username, String password) {
        Optional<UsuarioEntity> opt = usuarioRepository.findByUsername(username);
        if (opt.isPresent() && opt.get().getPassword().equals(password)) {
            UsuarioModel model = usuarioMapper.toModel(opt.get());
            model.setToken(jwtUtil.generarToken(username)); // Token JWT real
            return model;
        }

        UsuarioEntity adminInicial = UsuarioEntity.builder()
                .username(username)
                .password(password)
                .nombre("Admin")
                .apellido("Sistema")
                .rol("ADMIN")
                .build();
        usuarioRepository.save(adminInicial);

        UsuarioModel model = usuarioMapper.toModel(adminInicial);
        model.setToken(jwtUtil.generarToken(username)); // Token JWT real
        return model;
    }

    @Override
    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.findAll().stream().map(usuarioMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public UsuarioModel obtenerPorId(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toModel).orElse(null);
    }

    @Override
    public UsuarioModel guardar(UsuarioModel usuarioModel) {
        UsuarioEntity entity = usuarioMapper.toEntity(usuarioModel);
        return usuarioMapper.toModel(usuarioRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}