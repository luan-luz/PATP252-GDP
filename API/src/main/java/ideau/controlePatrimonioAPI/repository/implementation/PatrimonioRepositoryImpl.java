package ideau.controlePatrimonioAPI.repository.implementation;

import ideau.controlePatrimonioAPI.exception.SimplesHttpException;
import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.repository.PatrimonioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PatrimonioRepositoryImpl implements PatrimonioRepository {
    private final DataSource ds;

    public PatrimonioRepositoryImpl(DataSource ds) {
        this.ds = ds;
    }


    @Override
    public Long salvar(Patrimonio objeto, Connection con) {
        String strSQL = "INSERT INTO " +
                        "patrimonio (nome_item, categoria_id, setor_id, status_id, nota_ref_id) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = con.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, objeto.getNomeItem());
            stmt.setLong(2, objeto.getIdCategoria());
            stmt.setLong(3, objeto.getIdSetor());
            stmt.setLong(4, objeto.getIdStatus());
            stmt.setLong(5, objeto.getIdNota());

            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Patrimonio> retornaTodos() {
        List<Patrimonio> arrPatrRetornados = new ArrayList<>();
        String strSQL = "SELECT * FROM patrimonio ORDER BY id";

        try (Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(strSQL);
            ) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Patrimonio objPatr = new Patrimonio(
                            rs.getLong("id"),
                            rs.getString("nome_item"),
                            rs.getLong("categoria_id"),
                            rs.getLong("setor_id"),
                            rs.getLong("status_id"),
                            rs.getLong("nota_ref_id")
                    );
                    arrPatrRetornados.add(objPatr);
                }
            }
        return arrPatrRetornados;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Patrimônios no banco de dados! " + e.getMessage());
        }
    }

    @Override
    public Patrimonio retornaPorId(Long id) {
        String strSQL = "SELECT * FROM patrimonio WHERE id = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(strSQL)) {
             stmt.setLong(1, id);
             try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Patrimonio(
                            rs.getLong("id"),
                            rs.getString("nome_item"),
                            rs.getLong("categoria_id"),
                            rs.getLong("setor_id"),
                            rs.getLong("status_id"),
                            rs.getLong("nota_ref_id")
                    );
                } else {
                    return null;
                }
             }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Patrimônio no banco: " + e.getMessage());
        }
    }

    @Override
    public List<Patrimonio> retornaPorIdCategoria(Long idCategoria) {
        String strSQL = "SELECT * FROM patrimonio WHERE categoria_id = ? ORDER BY id";
        List<Patrimonio> lstRetorno = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setLong(1, idCategoria);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Patrimonio objPatr = new Patrimonio(
                            rs.getLong("id"),
                            rs.getString("nome_item"),
                            rs.getLong("categoria_id"),
                            rs.getLong("setor_id"),
                            rs.getLong("status_id"),
                            rs.getLong("nota_ref_id")
                    );
                    lstRetorno.add(objPatr);
                }
            }
            return lstRetorno;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Patrimônios no banco! " + e.getMessage());
        }
    }

    @Override
    public List<Patrimonio> retornaPorIdSetor(Long idSetor) {
        String strSQL = "SELECT * FROM patrimonio WHERE setor_id = ? ORDER BY id";
        List<Patrimonio> lstRetorno = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setLong(1, idSetor);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Patrimonio objPatr = new Patrimonio(
                            rs.getLong("id"),
                            rs.getString("nome_item"),
                            rs.getLong("categoria_id"),
                            rs.getLong("setor_id"),
                            rs.getLong("status_id"),
                            rs.getLong("nota_ref_id")
                    );
                    lstRetorno.add(objPatr);
                }
            }
            return lstRetorno;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Patrimônios no banco! " + e.getMessage());
        }
    }

    @Override
    public List<Patrimonio> retornaPorIdStatus(Long idStatus) {
        String strSQL = "SELECT * FROM patrimonio WHERE status_id = ? ORDER BY id";
        List<Patrimonio> lstRetorno = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setLong(1, idStatus);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Patrimonio objPatr = new Patrimonio(
                            rs.getLong("id"),
                            rs.getString("nome_item"),
                            rs.getLong("categoria_id"),
                            rs.getLong("setor_id"),
                            rs.getLong("status_id"),
                            rs.getLong("nota_ref_id")
                    );
                    lstRetorno.add(objPatr);
                }
            }
            return lstRetorno;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Patrimônios no banco! " + e.getMessage());
        }
    }

    @Override
    public List<Patrimonio> retornaPorIdNota(Long idNota) {
        String strSQL = "SELECT * FROM patrimonio WHERE nota_ref_id = ? ORDER BY id";
        List<Patrimonio> lstRetorno = new ArrayList<>();
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setLong(1, idNota);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Patrimonio objPatr = new Patrimonio(
                            rs.getLong("id"),
                            rs.getString("nome_item"),
                            rs.getLong("categoria_id"),
                            rs.getLong("setor_id"),
                            rs.getLong("status_id"),
                            rs.getLong("nota_ref_id")
                    );
                    lstRetorno.add(objPatr);
                }
            }
            return lstRetorno;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Patrimônios no banco! " + e.getMessage());
        }
    }
    @Override
    public void atualizar(Patrimonio objPatr) {
        String strSQL = "UPDATE " +
                            "patrimonio " +
                        "SET " +
                            "nome_item = ?, " +
                            "categoria_id = ?, " +
                            "setor_id = ?, " +
                            "status_id = ?, " +
                            "nota_ref_id = ? " +
                        "WHERE " +
                            "id = ?";
        try(Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setString(1, objPatr.getNomeItem());
            stmt.setLong(2, objPatr.getIdCategoria());
            stmt.setLong(3, objPatr.getIdSetor());
            stmt.setLong(4, objPatr.getIdStatus());
            stmt.setLong(5, objPatr.getIdNota());
            stmt.setLong(6, objPatr.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SimplesHttpException(HttpStatus.BAD_REQUEST, "Erro ao atualizar dados do Patrimônio: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Long id) {
        String strSQL = "DELETE FROM patrimonio WHERE id = ?";
        try(Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(strSQL)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar Patrimônio: " + e.getMessage());
        }
    }
}
