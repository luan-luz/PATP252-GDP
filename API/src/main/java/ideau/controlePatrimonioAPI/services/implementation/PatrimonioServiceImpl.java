package ideau.controlePatrimonioAPI.services.implementation;

import ideau.controlePatrimonioAPI.exception.SimplesHttpException;
import ideau.controlePatrimonioAPI.exception.ValidacaoException;
import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.repository.implementation.PatrimonioRepositoryImpl;
import ideau.controlePatrimonioAPI.services.PatrimonioService;
import ideau.controlePatrimonioAPI.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PatrimonioServiceImpl implements PatrimonioService {
    private final PatrimonioRepositoryImpl repo;
    private final DataSource ds;

    PatrimonioServiceImpl(PatrimonioRepositoryImpl repo, DataSource ds) {
        this.repo = repo;
        this.ds   = ds;
    }

    @Override
    public Map<Integer, Patrimonio> cadastrarLote(Map<Integer, Patrimonio> mapPatrimonios) {
        Map<Integer, String> mapErros = new HashMap<>();
        String strErros;
        Integer intAtual = 0;
        Long lngId;

        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(false);

            for(Map.Entry<Integer, Patrimonio> entry : mapPatrimonios.entrySet()) {
                intAtual = entry.getKey();
                Patrimonio objPatr = entry.getValue();
                strErros = "";
                if (StringUtils.isBlank(objPatr.getNomeItem())) {
                    strErros += "nomeItem; ";
                }
                if (objPatr.getIdCategoria() == 0) {
                    strErros += "idCategoria; ";
                }
                if (objPatr.getIdStatus() == 0) {
                    strErros += "idStatus; ";
                }
                if (objPatr.getIdSetor() == 0) {
                    strErros += "idSetor; ";
                }
                if(objPatr.getIdNota() == 0) {
                    strErros += "idNota; ";
                }

                if(!strErros.isBlank()) {
                    strErros += "faltando na requisição!";
                    mapErros.put(intAtual, strErros);
                } else {
                    Savepoint savepoint = con.setSavepoint();
                    try {
                        lngId = repo.salvar(entry.getValue(), con);
                        objPatr.setId(lngId);
                        } catch (Exception e) {
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
            return mapPatrimonios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados!", e);
        }
    }

    @Override
    public List<Patrimonio> retornarTodos() {
        List<Patrimonio> lstRetorno = new ArrayList<>();
        lstRetorno = repo.retornaTodos();
        if (lstRetorno.isEmpty()) {
            throw new SimplesHttpException(
                    HttpStatus.NOT_FOUND,
                    "Não foram encontrados Patrimônios no banco!");
        }
        return lstRetorno;
    }

    @Override
    public Patrimonio retornaPorId(Long id) {
        Patrimonio objRetorno = repo.retornaPorId(id);
        if (objRetorno == null) {
            throw new SimplesHttpException(
                    HttpStatus.NOT_FOUND,
                    "Não foram encontrados Patrimônios com id = " + id);
        }
        return objRetorno;
    }

    @Override
    public List<Patrimonio> retornarPorIdCategoria(Long idCategoria) {
        List<Patrimonio> lstRetorno = new ArrayList<>();
        lstRetorno = repo.retornaPorIdCategoria(idCategoria);
        if (lstRetorno.isEmpty()) {
            throw new SimplesHttpException(
                    HttpStatus.NOT_FOUND,
                    "Não foram encontrados Patrimônios com idCategoria = " + idCategoria);
        }
        return lstRetorno;
    }

    @Override
    public List<Patrimonio> retornarPorIdSetor(Long idSetor) {
        List<Patrimonio> lstRetorno = new ArrayList<>();
        lstRetorno = repo.retornaPorIdSetor(idSetor);
        if (lstRetorno.isEmpty()) {
            throw new SimplesHttpException(
                    HttpStatus.NOT_FOUND,
                    "Não foram encontrados Patrimônios com idSetor = " + idSetor);
        }
        return lstRetorno;
    }

    @Override
    public List<Patrimonio> retornarPorIdStatus(Long idStatus) {
        List<Patrimonio> lstRetorno = new ArrayList<>();
        lstRetorno = repo.retornaPorIdStatus(idStatus);
        if (lstRetorno.isEmpty()) {
            throw new SimplesHttpException(
                    HttpStatus.NOT_FOUND,
                    "Não foram encontrados Patrimônios com idStatus = " + idStatus);
        }
        return lstRetorno;
    }

    @Override
    public List<Patrimonio> retornarPorIdNota(Long idNota) {
        List<Patrimonio> lstRetorno = new ArrayList<>();
        lstRetorno = repo.retornaPorIdNota(idNota);
        if (lstRetorno.isEmpty()) {
            throw new SimplesHttpException(
                    HttpStatus.NOT_FOUND,
                    "Não foram encontrados Patrimônios com idNota = " + idNota);
        }
        return lstRetorno;
    }

    @Override
    public Patrimonio atualizar(Patrimonio obj) {
    Map<Integer, String> mapErros = new HashMap<>();
        if (!Utils.isPatrimonioValido(obj, mapErros)) {
            throw new ValidacaoException(HttpStatus.BAD_REQUEST, "Erro(s) de validação, verifique a requisição.", mapErros);
        }
        if (repo.retornaPorId(obj.getId()) == null) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Patrimônio com id: " + obj.getId() + " não encontrado.");
        }
        repo.atualizar(obj);
        return obj;
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        if (repo.retornaPorId(id) == null) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Erro ao deletar Patrimônio! Patrimônio com id = " +
                    id + " não encontrado.");
        }
        repo.deletar(id);
        return ResponseEntity
                .noContent().build();
    }
}
