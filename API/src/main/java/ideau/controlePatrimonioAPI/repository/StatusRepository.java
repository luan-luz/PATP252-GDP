package ideau.controlePatrimonioAPI.repository;

import ideau.controlePatrimonioAPI.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    boolean existsByNome(String nome);
}
