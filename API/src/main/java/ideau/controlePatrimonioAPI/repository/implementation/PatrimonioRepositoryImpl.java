package ideau.controlePatrimonioAPI.repository.implementation;

import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.model.PatrimonioDTO;
import ideau.controlePatrimonioAPI.repository.GenericRepository;
import ideau.controlePatrimonioAPI.repository.PatrimonioRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

@Repository
public class PatrimonioRepositoryImpl implements GenericRepository<Patrimonio, PatrimonioDTO> {
    private final DataSource ds;

    PatrimonioRepositoryImpl(DataSource ds) {
        this.ds   = ds;
    }

    @Override
    public Long salvar(Patrimonio objeto, Connection con) {
        long idRetornado = 0;
        String strSQL = "INSERT INTO " +
                "patrimonio (" +
                "  id_item," +
                "  id_local," +
                "  id_status," +
                "  id_nota," +
                "  num_patr," +
                "  val_compra," +
                "  aliq_deprec_mes," +
                "  dt_aquisicao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = con.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, objeto.getIdItem());
            stmt.setLong(2, objeto.getIdLocal());
            stmt.setLong(3, objeto.getIdStatus());
            stmt.setObject(4, objeto.getIdNota(), Types.BIGINT);
            stmt.setString(5, objeto.getNumPatr());
            stmt.setBigDecimal(6, objeto.getvalCompra());
            stmt.setBigDecimal(7, objeto.getAliqDeprecMes());
            if (objeto.getDtAquisicao() != null) {
                stmt.setDate(8, java.sql.Date.valueOf(objeto.getDtAquisicao()));
            } else {
                stmt.setNull(8, java.sql.Types.DATE);
            }


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
    public List<PatrimonioDTO> retornaTodos() {
        List<PatrimonioDTO> lstRetorno = new ArrayList<>();
        String sql = "select" +
                "  p.id as id," +
                "  i.nome_item as nomeItem," +
                "  s.nome as nomeStatus," +
                "  l.nome as nomeLocal," +
                "  n.n_nfe as numNota," +
                "  n.n_serie as serieNota," +
                "  p.num_patr as numPatr," +
                "  p.val_compra as valCompra," +
                "  p.aliq_deprec_mes as aliquota," +
                "  p.dt_aquisicao as dtAquisicao " +
                "from" +
                "  patrimonio p " +
                "left outer join" +
                "  item as i on i.id = p.id_item " +
                "left outer join" +
                "  status_item as s on s.id = p.id_status " +
                "left outer join" +
                "  locais as l on l.id = p.id_local " +
                "left outer join " +
                "  notas_f as n on n.id = p.id_nota " +
                "order by p.id;";
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate dtAquisicao = (rs.getDate("dtAquisicao") == null) ? null : rs.getDate("dtAquisicao").toLocalDate();
                    lstRetorno.add(new PatrimonioDTO(
                            rs.getLong("id"),
                            rs.getString("nomeItem"),
                            rs.getString("nomeStatus"),
                            rs.getString("nomeLocal"),
                            rs.getString("numNota"),
                            rs.getString("serieNota"),
                            rs.getString("numPatr"),
                            rs.getBigDecimal("valCompra"),
                            rs.getBigDecimal("aliquota"),
                            dtAquisicao
                    ));
                }
                return lstRetorno;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PatrimonioDTO retornaPorId(Long id) {
        List<PatrimonioDTO> lstRetorno = new ArrayList<>();
        String sql = "select" +
                "  p.id as id," +
                "  i.nome_item as nomeItem," +
                "  s.nome as nomeStatus," +
                "  l.nome as nomeLocal," +
                "  n.n_nfe as numNota," +
                "  n.n_serie as serieNota," +
                "  p.num_patr as numPatr," +
                "  p.val_compra as valCompra," +
                "  p.aliq_deprec_mes as aliquota," +
                "  p.dt_aquisicao as dtAquisicao " +
                "from " +
                "  patrimonio p " +
                "left outer join" +
                "  item as i on i.id = p.id_item " +
                "left outer join" +
                "  status_item as s on s.id = p.id_status " +
                "left outer join" +
                "  locais as l on l.id = p.id_local " +
                "left outer join " +
                "  notas_f as n on n.id = p.id_nota " +
                "where p.id = ?;";

        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate dtAquisicao = (rs.getDate("dtAquisicao") == null) ? null : rs.getDate("dtAquisicao").toLocalDate();
                    return new PatrimonioDTO(
                            rs.getLong("id"),
                            rs.getString("nomeItem"),
                            rs.getString("nomeStatus"),
                            rs.getString("nomeLocal"),
                            rs.getString("numNota"),
                            rs.getString("serieNota"),
                            rs.getString("numPatr"),
                            rs.getBigDecimal("valCompra"),
                            rs.getBigDecimal("aliquota"),
                            dtAquisicao
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    @Override
    public PatrimonioDTO atualizar(Patrimonio objeto) {
        String sql = "UPDATE" +
                " patrimonio" +
                " SET" +
                " id_local = ?," +
                " id_status = ?," +
                " id_nota = ?," +
                " num_patr = ?," +
                " val_compra = ?," +
                " aliq_deprec_mes = ?," +
                " dt_aquisicao = ?" +
                " WHERE" +
                " id = ?;";
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, objeto.getIdLocal());
            stmt.setLong(2, objeto.getIdStatus());
            stmt.setLong(3, objeto.getIdNota());
            stmt.setString(4, objeto.getNumPatr());
            stmt.setBigDecimal(5, objeto.getvalCompra());
            stmt.setBigDecimal(6, objeto.getAliqDeprecMes());
            if (objeto.getDtAquisicao() != null) {
                stmt.setDate(7, java.sql.Date.valueOf(objeto.getDtAquisicao()));
            } else {
                stmt.setNull(7, java.sql.Types.DATE);
            }
            stmt.setLong(8, objeto.getId());

            stmt.executeUpdate();

            return retornaPorId(objeto.getId());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM " +
                        "patrimonio " +
                    "WHERE " +
                        "id = ?";
        try (PreparedStatement stmt = ds.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean patrJaExiste(String numPatr) {
        String sql = "SELECT num_patr FROM patrimonio WHERE num_patr = ? and num_patr <> ''";
        try (Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, numPatr);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }
}
