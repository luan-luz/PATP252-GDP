package ideau.controlePatrimonioAPI.services.implementation;

import ideau.controlePatrimonioAPI.exception.SimplesHttpException;
import ideau.controlePatrimonioAPI.exception.ValidacaoException;
import ideau.controlePatrimonioAPI.model.Nota;
import ideau.controlePatrimonioAPI.model.NotaDTO;
import ideau.controlePatrimonioAPI.repository.implementation.NotaRepositoryImpl;
import ideau.controlePatrimonioAPI.services.NotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class NotaServiceImpl implements NotaService {
    private final NotaRepositoryImpl repo;
    private final DataSource ds;
    public NotaServiceImpl(NotaRepositoryImpl repo, DataSource ds) {
        this.repo = repo;
        this.ds = ds;
    }

    @Override
    public NotaDTO retornarPorIdFornecedor(Long idFornecedor) {
        return null;
    }

    @Override
    public Map<Integer, NotaDTO> cadastrarLote(Map<Integer, Nota> mapObjetos) {
        Map<Integer, NotaDTO> mapRetorno = new HashMap<>();
        Map<Integer, String> mapErros = new HashMap<>();
        int cont = 1;
        try {
            Connection con = ds.getConnection();
            con.setAutoCommit(false);
            for(Map.Entry<Integer, Nota> entry : mapObjetos.entrySet()) {
                Integer intAtual = entry.getKey();
                String erros = validaNotaFiscal(entry.getValue());
                if (!erros.isBlank()) {
                    mapErros.put(intAtual, erros);
                } else {
                    Savepoint save = con.setSavepoint();
                    try {
                        mapRetorno.put(intAtual, new NotaDTO(repo.salvar(entry.getValue(), con)));
                    } catch (Exception e) {
                        mapErros.put(intAtual, e.getMessage());
                        con.rollback(save);
                    }
                    cont++;
                }
            }
            con.commit();
            con.setAutoCommit(true);
            if (!mapErros.isEmpty()) {
                throw new ValidacaoException(HttpStatus.BAD_REQUEST, "Erro no cadastro da(s) nota(s).", mapErros);
            }
            for (Map.Entry<Integer, NotaDTO> entry : mapRetorno.entrySet()) {
                mapRetorno.replace(entry.getKey(), retornaPorId(entry.getValue().getId()));
            }
            return mapRetorno;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar Nota: " + e.getMessage());
        }
    }

    @Override
    public List<NotaDTO> retornarTodos() {
        List<NotaDTO> lstResult = repo.retornaTodos();
        if (lstResult.isEmpty()) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Nenhuma nota fiscal encontrada no banco de dados.");
        }
        return lstResult;
    }

    @Override
    public NotaDTO retornaPorId(Long id) {
        NotaDTO dto = repo.retornaPorId(id);
        if (dto == null) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Nenhuma nota encontrada com id: " + id);
        }
        return dto;
    }

    @Override
    public NotaDTO atualizar(Nota obj) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        return null;
    }
    private String validaNotaFiscal(Nota nf) {
        StringBuilder stbErros = new StringBuilder();
        if (nf.getNumNota().isBlank()) {
            stbErros.append("numNota; ");
        }
        if (nf.getSerieNota().isBlank()) {
            stbErros.append("serieNota; ");
        }
        if (nf.getChaveNota().isBlank()) {
            stbErros.append("chaveNota; ");
        }
        if (nf.getDtEmissao() == null) {
            stbErros.append("dtEmissao; ");
        }
        if (nf.getVlrTotal() == null) {
            stbErros.append("vlrTotal; ");
        }
        if (nf.getIdFornecedor() == null) {
            stbErros.append("idFornecedor; ");
        }
        return stbErros.toString();
    }
}
