package ideau.controlePatrimonioAPI.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ideau.controlePatrimonioAPI.model.Usuario;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByUsuario(String usuario);
}
