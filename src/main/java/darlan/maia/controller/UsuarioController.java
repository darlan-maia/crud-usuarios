package darlan.maia.controller;

import darlan.maia.controller.swagger.SwaggerUsuarioController;
import darlan.maia.model.dto.UsuarioResponseDTO;
import darlan.maia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
