package ideau.controlePatrimonioAPI.controller;

import ideau.controlePatrimonioAPI.model.LoginRequest;
import ideau.controlePatrimonioAPI.services.AutLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthLoginController {

    @Autowired
    private AutLoginService authService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        boolean success = authService.validateLogin(request);
    if (success)
        return ResponseEntity.ok("Login válido");
    else
        return ResponseEntity.status(401).body("Usuário ou senha incorreto");
    }

}
