package ideau.controlePatrimonioAPI.repository.implementation;

import ideau.controlePatrimonioAPI.exception.SimplesHttpException;
import ideau.controlePatrimonioAPI.model.Item;
import ideau.controlePatrimonioAPI.model.ItemDTO;
import ideau.controlePatrimonioAPI.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final DataSource ds;

    public ItemRepositoryImpl(DataSource ds) {
        this.ds = ds;
    }


    @Override
    public Long salvar(Item objeto, Connection con) {
        long idRetornado = 0;
        String strSQL = "INSERT INTO " +
                        "item (nome_item, categoria_id, setor_id, status_id) " +
                        "VALUES (?, ?, ?, ?)";

        try(PreparedStatement stmt = con.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, objeto.getNomeItem());
            stmt.setLong(2, objeto.getIdCategoria());
            stmt.setLong(3, objeto.getIdSetor());
            stmt.setLong(4, objeto.getIdStatus());

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ItemDTO> retornaTodos() {
        List<ItemDTO> arrPatrRetornados = new ArrayList<>();
        String strSQL = "select " +
                        "i.id as id, " +
                        "i.nome_item as nome, " +
                        "c.nome as categoria, " +
                        "se.nome as setor, " +
                        "st.nome as status " +
                        "from item as i " +
                        "left outer join " +
                        "categorias as c on c.id = i.categoria_id " +
                        "left outer join " +
                        "setores as se on se.id = i.setor_id " +
                        "left outer join " +
                        "status_item as st on st.id = i.status_id " +
                        "order by " +
                        "i.id;";
        try (Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(strSQL);
            ) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemDTO objPatr = new ItemDTO(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("categoria"),
                            rs.getString("setor"),
                            rs.getString("status")
                    );
                    arrPatrRetornados.add(objPatr);
                }
            }
        return arrPatrRetornados;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Itens no banco de dados! " + e.getMessage());
        }
    }

    @Override
    public ItemDTO retornaPorId(Long id) {
        String strSQL = "select " +
                        "i.id as id, " +
                        "i.nome_item as nome, " +
                        "c.nome as categoria, " +
                        "se.nome as setor, " +
                        "st.nome as status " +
                        "from item as i " +
                        "left outer join " +
                        "categorias as c on c.id = i.categoria_id " +
                        "left outer join " +
                        "setores as se on se.id = i.setor_id " +
                        "left outer join " +
                        "status_item as st on st.id = i.status_id " +
                        "where i.id = ? " +
                        "order by " +
                        "i.id;";
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(strSQL)) {
             stmt.setLong(1, id);
             try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ItemDTO(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("categoria"),
                            rs.getString("setor"),
                            rs.getString("status")
                    );
                } else {
                    return null;
                }
             }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Itens no banco: " + e.getMessage());
        }
    }

    @Override
    public List<ItemDTO> retornaPorIdCategoria(Long idCategoria) {
        String strSQL = "select " +
                "i.id as id, " +
                "i.nome_item as nome, " +
                "c.nome as categoria, " +
                "se.nome as setor, " +
                "st.nome as status " +
                "from item as i " +
                "left outer join " +
                "categorias as c on c.id = i.categoria_id " +
                "left outer join " +
                "setores as se on se.id = i.setor_id " +
                "left outer join " +
                "status_item as st on st.id = i.status_id " +
                "where i.categoria_id = ? " +
                "order by " +
                "i.id;";
        List<ItemDTO> lstRetorno = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setLong(1, idCategoria);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemDTO objPatr = new ItemDTO(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("categoria"),
                            rs.getString("setor"),
                            rs.getString("status")
                    );
                    lstRetorno.add(objPatr);
                }
            }
            return lstRetorno;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Itens no banco! " + e.getMessage());
        }
    }

    @Override
    public List<ItemDTO> retornaPorIdSetor(Long idSetor) {
        String strSQL = "select " +
                "i.id as id, " +
                "i.nome_item as nome, " +
                "c.nome as categoria, " +
                "se.nome as setor, " +
                "st.nome as status " +
                "from item as i " +
                "left outer join " +
                "categorias as c on c.id = i.categoria_id " +
                "left outer join " +
                "setores as se on se.id = i.setor_id " +
                "left outer join " +
                "status_item as st on st.id = i.status_id " +
                "where i.setor_id = ? " +
                "order by " +
                "i.id;";
        List<ItemDTO> lstRetorno = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setLong(1, idSetor);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemDTO objPatr = new ItemDTO(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("categoria"),
                            rs.getString("setor"),
                            rs.getString("status")
                    );
                    lstRetorno.add(objPatr);
                }
            }
            return lstRetorno;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Itens no banco! " + e.getMessage());
        }
    }

    @Override
    public List<ItemDTO> retornaPorIdStatus(Long idStatus) {
        String strSQL = "select " +
                "i.id as id, " +
                "i.nome_item as nome, " +
                "c.nome as categoria, " +
                "se.nome as setor, " +
                "st.nome as status " +
                "from item as i " +
                "left outer join " +
                "categorias as c on c.id = i.categoria_id " +
                "left outer join " +
                "setores as se on se.id = i.setor_id " +
                "left outer join " +
                "status_item as st on st.id = i.status_id " +
                "where i.status_id = ? " +
                "order by " +
                "i.id;";
        List<ItemDTO> lstRetorno = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setLong(1, idStatus);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemDTO objPatr = new ItemDTO(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("categoria"),
                            rs.getString("setor"),
                            rs.getString("status")
                    );
                    lstRetorno.add(objPatr);
                }
            }
            return lstRetorno;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Itens no banco! " + e.getMessage());
        }
    }

    @Override
    public ItemDTO atualizar(Item objPatr) {
        String strSQL = "UPDATE " +
                            "item " +
                        "SET " +
                            "nome_item = ?, " +
                            "categoria_id = ?, " +
                            "setor_id = ?, " +
                            "status_id = ? " +
                        "WHERE " +
                            "id = ?";
        try(Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setString(1, objPatr.getNomeItem());
            stmt.setLong(2, objPatr.getIdCategoria());
            stmt.setLong(3, objPatr.getIdSetor());
            stmt.setLong(4, objPatr.getIdStatus());
            stmt.setLong(5, objPatr.getId());
            stmt.executeUpdate();

            return retornaPorId(objPatr.getId());
        } catch (SQLException e) {
            throw new SimplesHttpException(HttpStatus.BAD_REQUEST, "Erro ao atualizar dados do Patrimônio: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Long id) {
        String strSQL = "DELETE FROM item WHERE id = ?";
        try(Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar Patrimônio: " + e.getMessage());
        }
    }
}
