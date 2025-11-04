package ideau.controlePatrimonioAPI.services.implementation;

import ideau.controlePatrimonioAPI.exception.SimplesHttpException;
import ideau.controlePatrimonioAPI.exception.ValidacaoException;
import ideau.controlePatrimonioAPI.model.Categoria;
import ideau.controlePatrimonioAPI.model.Local;
import ideau.controlePatrimonioAPI.repository.CategoriaRepository;
import ideau.controlePatrimonioAPI.repository.LocalRepository;
import ideau.controlePatrimonioAPI.services.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocalServiceImpl implements GenericService<Local, Local> {
    private final LocalRepository repo;

    public LocalServiceImpl(LocalRepository repo) {
        this.repo = repo;
    }

    @Override
    public Map<Integer, Local> cadastrarLote(Map<Integer, Local> mapObjetos) {
        Map<Integer, Local> mapRetorno = new HashMap<>();
        Map<Integer, String> mapErros = new HashMap<>();
        int cont = 1;

        for(Map.Entry<Integer, Local> entry : mapObjetos.entrySet()) {
            Integer intAtual = entry.getKey();
            String erros = "";
            if (repo.existsByNome(entry.getValue().getNome())) {
                erros = "Já existe um Local com o nome: " + entry.getValue().getNome();
            }
            if (!erros.isBlank()) {
                mapErros.put(intAtual, erros);
            } else {
                try {
                    mapRetorno.put(intAtual, new Local(repo.save(entry.getValue()).getId()));
                } catch (Exception e) {
                    mapErros.put(intAtual, e.getMessage());
                }
                cont++;
            }
        }
        if (!mapErros.isEmpty()) {
            throw new ValidacaoException(HttpStatus.BAD_REQUEST, "Erro no cadastro do(s) Local(is).", mapErros);
        }
        for (Map.Entry<Integer, Local> entry : mapRetorno.entrySet()) {
            mapRetorno.replace(entry.getKey(), retornaPorId(entry.getValue().getId()));
        }
        return mapRetorno;
    }

    @Override
    public List<Local> retornarTodos() {
        return repo.findAll();
    }

    @Override
    public Local retornaPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrado Local com id: " + id));
    }

    @Override
    public Local atualizar(Local obj) {
        if (repo.existsByNome(obj.getNome())) {
            throw new SimplesHttpException(HttpStatus.CONFLICT, "Já existe um local com nome: " + obj.getNome());
        }
        repo.save(obj);
        return obj;
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrado local com id: " + id);
        }
        repo.deleteById(id);
        return null;
    }
}
