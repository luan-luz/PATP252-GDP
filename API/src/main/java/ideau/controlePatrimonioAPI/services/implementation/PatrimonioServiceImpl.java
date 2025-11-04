package ideau.controlePatrimonioAPI.services.implementation;

import ideau.controlePatrimonioAPI.exception.SimplesHttpException;
import ideau.controlePatrimonioAPI.exception.ValidacaoException;
import ideau.controlePatrimonioAPI.model.Item;
import ideau.controlePatrimonioAPI.model.ItemDTO;
import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.model.PatrimonioDTO;
import ideau.controlePatrimonioAPI.repository.implementation.PatrimonioRepositoryImpl;
import ideau.controlePatrimonioAPI.services.PatrimonioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PatrimonioServiceImpl implements PatrimonioService {
    private final DataSource ds;
    private final PatrimonioRepositoryImpl repo;

    public PatrimonioServiceImpl(DataSource ds, PatrimonioRepositoryImpl repo) {
        this.ds = ds;
        this.repo = repo;
    }

    @Override
    public List<PatrimonioDTO> retornarPorIdStatus(Long idStatus) {
        return List.of();
    }

    @Override
    public List<PatrimonioDTO> retornarPorIdLocal(Long idLocal) {
        return List.of();
    }

    @Override
    public List<PatrimonioDTO> retornarPorIdNota(Long idNota) {
        return List.of();
    }

    @Override
    public boolean verificaExistenciaNumPatr(String numPatr) {
        return repo.patrJaExiste(numPatr);
    }

    @Override
    public Map<Integer, PatrimonioDTO> cadastrarLote(Map<Integer, Patrimonio> mapObjetos) {
        Map<Integer, String> mapErros = new HashMap<>();
        Map<Integer, PatrimonioDTO> mapRetorno = new HashMap<>();
        String strErros;
        Integer intAtual = 0;
        System.out.println(mapObjetos);
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(false);

            for(Map.Entry<Integer, Patrimonio> entry : mapObjetos.entrySet()) {
                intAtual = entry.getKey();
                Patrimonio objPatr = entry.getValue();
                strErros = "";

                if (repo.patrJaExiste(objPatr.getNumPatr())) {
                    strErros += " Número patrimônio já em uso! Escolha outro.";
                }
                if (objPatr.getIdItem() == null) {
                    strErros += "idItem faltando na requisição!; ";
                }
                if (objPatr.getIdLocal() == null) {
                    strErros += "idLocal faltando na requisição!; ";
                }
                if (objPatr.getIdStatus() == null) {
                    strErros += "idSetor faltando na requisição!; ";
                }

                if(!strErros.isBlank()) {
                    mapErros.put(intAtual, strErros);
                } else {
                    Savepoint savepoint = con.setSavepoint();
                    try {
                        mapRetorno.put(intAtual, new PatrimonioDTO(repo.salvar(objPatr, con)));//Construtor com apenas o id
                    } catch (Exception e) {                                                   //usa o id para consultar o patrimonio depois
                        mapErros.put(intAtual, e.getMessage());
                        con.rollback(savepoint);
                    }
                }
            }
            if (!mapErros.isEmpty()) {
                con.rollback();
                throw new ValidacaoException(HttpStatus.BAD_REQUEST, "Erro de validação!", mapErros);
            }
            con.commit();
            con.setAutoCommit(true);
            for (Map.Entry<Integer, PatrimonioDTO> entry : mapRetorno.entrySet()) {
                mapRetorno.replace(entry.getKey(), repo.retornaPorId(entry.getValue().getId()));
            }
            return mapRetorno;
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco!", e);
        }
    }

    @Override
    public List<PatrimonioDTO> retornarTodos() {
        return repo.retornaTodos();
    }

    @Override
    public PatrimonioDTO retornaPorId(Long id) {
        PatrimonioDTO dto = repo.retornaPorId(id);
        if (dto != null) {
            return dto;
        } else {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Nenhum Patrimônio encontrado com id: " + id);
        }
    }

    @Override
    public PatrimonioDTO atualizar(Patrimonio obj) {
        if (obj.getId() == null) {
            throw new SimplesHttpException(HttpStatus.BAD_REQUEST, "Informe o id do Patrimônio que deseja atualizar!");
        }
        if (repo.patrJaExiste(obj.getNumPatr())) {
            throw new SimplesHttpException(HttpStatus.CONFLICT, "Número do Patrimônio já em uso! Escolha outro.");
        }
        return repo.atualizar(obj);
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        if (repo.retornaPorId(id) == null) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Não encontrado patrimônio com id: " + id);
        }
        repo.deletar(id);
        return null;
    }
}
