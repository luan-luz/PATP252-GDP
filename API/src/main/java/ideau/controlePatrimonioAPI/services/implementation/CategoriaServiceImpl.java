package ideau.controlePatrimonioAPI.services.implementation;

import ideau.controlePatrimonioAPI.exception.SimplesHttpException;
import ideau.controlePatrimonioAPI.exception.ValidacaoException;
import ideau.controlePatrimonioAPI.model.Categoria;
import ideau.controlePatrimonioAPI.model.Fornecedor;
import ideau.controlePatrimonioAPI.repository.CategoriaRepository;
import ideau.controlePatrimonioAPI.services.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoriaServiceImpl implements GenericService<Categoria, Categoria> {
    private final CategoriaRepository repo;

    public CategoriaServiceImpl(CategoriaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Map<Integer, Categoria> cadastrarLote(Map<Integer, Categoria> mapObjetos) {
        Map<Integer, Categoria> mapRetorno = new HashMap<>();
        Map<Integer, String> mapErros = new HashMap<>();
        int cont = 1;

        for(Map.Entry<Integer, Categoria> entry : mapObjetos.entrySet()) {
            Integer intAtual = entry.getKey();
            String erros = "";
            if (repo.existsByNome(entry.getValue().getNome())) {
                erros = "Já existe uma categoria com o nome: " + entry.getValue().getNome();
            }
            if (!erros.isBlank()) {
                mapErros.put(intAtual, erros);
            } else {
                try {
                    mapRetorno.put(intAtual, new Categoria(repo.save(entry.getValue()).getNome()));
                } catch (Exception e) {
                    mapErros.put(intAtual, e.getMessage());
                }
                cont++;
            }
        }
        if (!mapErros.isEmpty()) {
            throw new ValidacaoException(HttpStatus.BAD_REQUEST, "Erro no cadastro da(s) Categoria(s).", mapErros);
        }
        for (Map.Entry<Integer, Categoria> entry : mapRetorno.entrySet()) {
            mapRetorno.replace(entry.getKey(), retornaPorId(entry.getValue().getId()));
        }
        return mapRetorno;
    }

    @Override
    public List<Categoria> retornarTodos() {
        return repo.findAll();
    }

    @Override
    public Categoria retornaPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrada categoria com id: " + id));
    }

    @Override
    public Categoria atualizar(Categoria obj) {
        if (repo.existsByNome(obj.getNome())) {
            throw new SimplesHttpException(HttpStatus.CONFLICT, "Já existe uma categoria com nome: " + obj.getNome());
        }
        repo.save(obj);
        return obj;
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrada categoria com id: " + id);
        }
        repo.deleteById(id);
        return null;
    }
}
