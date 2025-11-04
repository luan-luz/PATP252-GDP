package ideau.controlePatrimonioAPI.services;

import ideau.controlePatrimonioAPI.model.LoginRequest;
import ideau.controlePatrimonioAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutLoginService {

    @Autowired
    private UserRepository userRepository;

    public boolean validateLogin(LoginRequest request){
        return userRepository.findByUsuario(request.getUsuario())
                .map(user -> user.getSenha().equals(request.getSenha()))
                .orElse(false);
    }
}
