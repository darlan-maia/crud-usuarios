package darlan.maia.adapters.in.controller;

import darlan.maia.adapters.in.controller.swagger.SwaggerUsuarioController;
import darlan.maia.domain.port.in.UsuarioInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UsuarioController implements SwaggerUsuarioController {

    private final UsuarioInputPort service;

}
