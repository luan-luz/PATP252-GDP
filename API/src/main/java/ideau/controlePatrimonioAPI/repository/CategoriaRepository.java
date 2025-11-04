package ideau.controlePatrimonioAPI.repository;

import ideau.controlePatrimonioAPI.model.Categoria;
import ideau.controlePatrimonioAPI.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNome(String nome);
}
