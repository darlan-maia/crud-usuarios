package darlan.maia.controller;

import darlan.maia.controller.swagger.SwaggerUsuarioController;
import darlan.maia.model.dto.UsuarioRequestDTO;
import darlan.maia.model.dto.UsuarioResponseDTO;
import darlan.maia.model.dto.UsuarioUpdateRequestDTO;
import darlan.maia.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController implements SwaggerUsuarioController {

    @Autowired
    private UsuarioService service;

    @Override
    @GetMapping("/{username}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorUsername(final @PathVariable("username") String username) {
        return ResponseEntity.ok().body(service.buscaPorUsername(username));
    }

    @Override
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> salvar(final @Valid @RequestBody UsuarioRequestDTO request) {

        final URI uri = URI.create("/api/v1/%s".formatted(request.getUsername()));

        return ResponseEntity.created(uri).body(service.salvar(request));
    }

    @Override
    @PutMapping("/{username}")
    public ResponseEntity<UsuarioResponseDTO> update(
            final @PathVariable("username") String username,
            final @Valid @RequestBody UsuarioUpdateRequestDTO request
    ) {
        return ResponseEntity.ok().body(service.update(username, request));
    }

    @Override
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(final @PathVariable("username") String username) {
        service.delete(username);
        return ResponseEntity.noContent().build();
    }


}
