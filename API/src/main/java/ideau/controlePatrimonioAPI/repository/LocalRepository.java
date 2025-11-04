package ideau.controlePatrimonioAPI.repository;

import ideau.controlePatrimonioAPI.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
    boolean existsByNome(String nome);
}
