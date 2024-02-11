package darlan.maia.adapters.in.controller;

import darlan.maia.adapters.in.controller.dto.UsuarioResponseDTO;
import darlan.maia.adapters.in.controller.mapper.UsuarioControllerMapper;
import darlan.maia.adapters.in.controller.swagger.SwaggerUsuarioController;
import darlan.maia.domain.model.Usuario;
import darlan.maia.domain.port.in.UsuarioInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UsuarioController implements SwaggerUsuarioController {

    private final UsuarioInputPort service;

    @Override
    @GetMapping("/{username}")
    public ResponseEntity<UsuarioResponseDTO> findByUsername(final @PathVariable("username") String username) {
        final Usuario usuario = service.findByUsername(username);
        return ResponseEntity.ok().body(UsuarioControllerMapper.toDTO(usuario));
    }
}
