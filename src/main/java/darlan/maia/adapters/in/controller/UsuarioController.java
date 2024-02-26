package darlan.maia.adapters.in.controller;

import darlan.maia.adapters.in.controller.dto.UsuarioRequestDTO;
import darlan.maia.adapters.in.controller.dto.UsuarioResponseDTO;
import darlan.maia.adapters.in.controller.mapper.UsuarioControllerMapper;
import darlan.maia.adapters.in.controller.swagger.SwaggerUsuarioController;
import darlan.maia.domain.model.Usuario;
import darlan.maia.domain.port.in.UsuarioInputPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> save(@Valid @RequestBody UsuarioRequestDTO request) {
        final Usuario usuario = UsuarioControllerMapper.toDomain(request);
        final Usuario saved = service.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioControllerMapper.toDTO(saved));
    }
}
