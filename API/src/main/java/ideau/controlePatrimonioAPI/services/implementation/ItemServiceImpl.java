package ideau.controlePatrimonioAPI.services.implementation;

import ideau.controlePatrimonioAPI.exception.SimplesHttpException;
import ideau.controlePatrimonioAPI.exception.ValidacaoException;
import ideau.controlePatrimonioAPI.model.Item;
import ideau.controlePatrimonioAPI.model.ItemDTO;
import ideau.controlePatrimonioAPI.repository.implementation.ItemRepositoryImpl;
import ideau.controlePatrimonioAPI.services.ItemService;
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
public class ItemServiceImpl implements ItemService {
    private final ItemRepositoryImpl repo;
    private final DataSource ds;

    ItemServiceImpl(ItemRepositoryImpl repo, DataSource ds) {
        this.repo = repo;
        this.ds   = ds;
    }

    @Override
    public Map<Integer, ItemDTO> cadastrarLote(Map<Integer, Item> mapItens) {
        Map<Integer, String> mapErros = new HashMap<>();
        Map<Integer, ItemDTO> mapRetorno = new HashMap<>();
        String strErros;
        Integer intAtual = 0;

        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(false);

            for(Map.Entry<Integer, Item> entry : mapItens.entrySet()) {
                intAtual = entry.getKey();
                Item objPatr = entry.getValue();
                strErros = "";
                if (StringUtils.isBlank(objPatr.getNomeItem())) {
                    strErros += "nomeItem; ";
                }
                if (objPatr.getIdCategoria() == null) {
                    strErros += "idCategoria; ";
                }

                if(!strErros.isBlank()) {
                    strErros += "faltando na requisição!";
                    mapErros.put(intAtual, strErros);
                } else {
                    Savepoint savepoint = con.setSavepoint();
                    try {
                        mapRetorno.put(intAtual, new ItemDTO(repo.salvar(objPatr, con)));//Construtor com apenas o id
                        } catch (Exception e) {                                         //usa o id para consultar o item depois
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
            for (Map.Entry<Integer, ItemDTO> entry : mapRetorno.entrySet()) {
                mapRetorno.replace(entry.getKey(), repo.retornaPorId(entry.getValue().getId()));
            }
            return mapRetorno;
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco!", e);
        }
    }

    @Override
    public List<ItemDTO> retornarTodos() {
        List<ItemDTO> lstRetorno = new ArrayList<>();
        lstRetorno = repo.retornaTodos();
        if (lstRetorno.isEmpty()) {
            throw new SimplesHttpException(
                    HttpStatus.NOT_FOUND,
                    "Não foram encontrados Itens no banco!");
        }
        return lstRetorno;
    }

    @Override
    public ItemDTO retornaPorId(Long id) {
        ItemDTO objRetorno = repo.retornaPorId(id);
        if (objRetorno == null) {
            throw new SimplesHttpException(
                    HttpStatus.NOT_FOUND,
                    "Não foram encontrados Itens com id = " + id);
        }
        return objRetorno;
    }

    @Override
    public List<ItemDTO> retornarPorIdCategoria(Long idCategoria) {
        List<ItemDTO> lstRetorno = new ArrayList<>();
        lstRetorno = repo.retornaPorIdCategoria(idCategoria);
        if (lstRetorno.isEmpty()) {
            throw new SimplesHttpException(
                    HttpStatus.NOT_FOUND,
                    "Não foram encontrados itens com idCategoria = " + idCategoria);
        }
        return lstRetorno;
    }

    @Override
    public ItemDTO atualizar(Item obj) {
    Map<Integer, String> mapErros = new HashMap<>();
        if (validaItem(obj, mapErros)) {
            throw new ValidacaoException(HttpStatus.BAD_REQUEST, "Erro(s) de validação, verifique a requisição.", mapErros);
        }
        if (repo.retornaPorId(obj.getId()) == null) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Item com id: " + obj.getId() + " não encontrado.");
        }
        return repo.atualizar(obj);
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        if (repo.retornaPorId(id) == null) {
            throw new SimplesHttpException(HttpStatus.NOT_FOUND, "Erro ao deletar Item! Item com id = " +
                    id + " não encontrado.");
        }
        repo.deletar(id);
        return ResponseEntity
                .noContent().build();
    }

    boolean validaItem(Item objPatr, Map<Integer, String> mapErros) {
        Integer intQtdErros = 0;
        try {
            if (StringUtils.isBlank(objPatr.getNomeItem())) {
                intQtdErros++;
                mapErros.put(intQtdErros, "nomeItem em branco!");
            }
            if (objPatr.getIdCategoria() == null) {
                intQtdErros++;
                mapErros.put(intQtdErros, "idCategoria em branco!");
            }
            return mapErros.isEmpty();
        } catch (Exception e) {
            intQtdErros++;
            mapErros.put(intQtdErros, "Erro ao processar item! " + e.getMessage());
            return false;
        }
    }
}
