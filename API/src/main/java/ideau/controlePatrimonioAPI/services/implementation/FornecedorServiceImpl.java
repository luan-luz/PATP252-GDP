package ideau.controlePatrimonioAPI.services.implementation;

import ideau.controlePatrimonioAPI.exception.SimplesHttpException;
import ideau.controlePatrimonioAPI.exception.ValidacaoException;

import ideau.controlePatrimonioAPI.model.Fornecedor;
import ideau.controlePatrimonioAPI.repository.FornecedorRepository;

import ideau.controlePatrimonioAPI.services.FornecedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FornecedorServiceImpl implements FornecedorService {
    private final FornecedorRepository repo;
    private final DataSource ds;
    public FornecedorServiceImpl(FornecedorRepository repo, DataSource ds) {
        this.repo = repo;
        this.ds = ds;
    }

    @Override
    @Transactional
    public Map<Integer, Fornecedor> cadastrarLote(Map<Integer, Fornecedor> mapObjetos) {
        Map<Integer, Fornecedor> mapRetorno = new HashMap<>();
        Map<Integer, String> mapErros = new HashMap<>();
        int cont = 1;

        for(Map.Entry<Integer, Fornecedor> entry : mapObjetos.entrySet()) {
            Integer intAtual = entry.getKey();
            String erros = validaFornecedor(entry.getValue());
            if (!erros.isBlank()) {
                mapErros.put(intAtual, erros);
            } else {
                try {
                    entry.getValue().setCnpj(entry.getValue().getCnpj()
                            .replace(".", "")
                            .replace("/", "")
                            .replace("-", ""));
                    mapRetorno.put(intAtual, new Fornecedor(repo.save(entry.getValue()).getId()));
                } catch (Exception e) {
                    mapErros.put(intAtual, e.getMessage());
                }
                cont++;
            }
        }
        if (!mapErros.isEmpty()) {
            throw new ValidacaoException(HttpStatus.BAD_REQUEST, "Erro no cadastro do(s) Fornecedor(es).", mapErros);
        }
        for (Map.Entry<Integer, Fornecedor> entry : mapRetorno.entrySet()) {
            mapRetorno.replace(entry.getKey(), retornaPorId(entry.getValue().getId()));
        }
        return mapRetorno;
    }

    @Override
    public List<Fornecedor> retornarTodos() {
        List<Fornecedor> lstResult = repo.findAll();
        if (lstResult.isEmpty()) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Nenhum Fornecedor encontrado no banco de dados.");
        }
        return lstResult;
    }

    @Override
    public Fornecedor retornaPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new SimplesHttpException(HttpStatus.NOT_FOUND, "Nenhum fornecedor encontrado com id: " + id));
    }

    @Override
    public Fornecedor atualizar(Fornecedor obj) {
        if (!repo.existsById(obj.getId())) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrado fornecedor com id: "+ obj.getId());
        }
        String erros = validaFornecedor(obj);
        if (!erros.isBlank()) {
            throw new SimplesHttpException(HttpStatus.BAD_REQUEST, erros);
        }
        repo.save(obj);
        return obj;
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrados Fornecedores com id: " + id);
        }
        repo.deleteById(id);
        return null;
    }

    private String validaFornecedor(Fornecedor fnc) {
        StringBuilder stbErros = new StringBuilder();
        if (fnc.getRazaoSocial() == null) {
            stbErros.append("campo razaoSocial está nulo; ");
        } else if (fnc.getRazaoSocial().isBlank()) {
            stbErros.append("campo razaoSocial está em branco; ");
        }

        if (fnc.getCnpj() == null) {
            stbErros.append("campo CNPJ está nulo; ");
        } else if (fnc.getCnpj().isBlank()) {
            stbErros.append("campo CNPJ está em branco; ");
        } else if (repo.existsByCnpj(fnc.getCnpj())) {
            stbErros.append("Já existe um fornecedor cadastrado com o CNPJ: ");
            stbErros.append(fnc.getCnpj());
        }
        if (fnc.getNomeLogradouro() == null) {
            stbErros.append("campo logradouro está nulo; ");
        } else if (fnc.getNomeLogradouro().isBlank()) {
            stbErros.append("campo logradouro está em branco; ");
        }
        return stbErros.toString();
    }
}
