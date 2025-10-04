package ideau.controlePatrimonioAPI.services.implementation;

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
    public List<PatrimonioDTO> retornarPorIdSetor(Long idSetor) {
        return List.of();
    }

    @Override
    public List<PatrimonioDTO> retornarPorIdNota(Long idNota) {
        return List.of();
    }

    @Override
    public Map<Integer, PatrimonioDTO> cadastrarLote(Map<Integer, Patrimonio> mapObjetos) {
        Map<Integer, String> mapErros = new HashMap<>();
        Map<Integer, PatrimonioDTO> mapRetorno = new HashMap<>();
        String strErros;
        Integer intAtual = 0;

        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(false);

            for(Map.Entry<Integer, Patrimonio> entry : mapObjetos.entrySet()) {
                intAtual = entry.getKey();
                Patrimonio objPatr = entry.getValue();
                strErros = "";
                if (objPatr.getIdItem() == null) {
                    strErros += "idItem; ";
                }
                if (objPatr.getIdSetor() == null) {
                    strErros += "idSetor; ";
                }

                if(!strErros.isBlank()) {
                    strErros += "faltando na requisição!";
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
        return List.of();
    }

    @Override
    public PatrimonioDTO retornaPorId(Long id) {
        return null;
    }

    @Override
    public PatrimonioDTO atualizar(Patrimonio obj) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        return null;
    }
}
