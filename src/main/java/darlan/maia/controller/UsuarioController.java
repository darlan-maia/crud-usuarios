package darlan.maia.controller;

import darlan.maia.controller.swagger.SwaggerUsuarioController;
import darlan.maia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController implements SwaggerUsuarioController {

    @Autowired
    private UsuarioService service;
}
