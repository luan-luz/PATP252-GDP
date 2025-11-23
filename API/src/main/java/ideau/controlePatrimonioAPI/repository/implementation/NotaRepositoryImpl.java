package ideau.controlePatrimonioAPI.repository.implementation;

import ideau.controlePatrimonioAPI.model.Nota;
import ideau.controlePatrimonioAPI.model.NotaDTO;
import ideau.controlePatrimonioAPI.repository.NotaRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NotaRepositoryImpl implements NotaRepository {
    private final DataSource ds;

    public NotaRepositoryImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Long salvar(Nota objeto, Connection con) {
        String sql = "insert into " +
                        "notas_f (chave_acesso, n_serie, n_nfe, dt_emissao, vlr_total, fornecedor_id) " +
                        "values " +
                        "(?, ?, ?, ?, ?, ?);";
        try(PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ) {
            stmt.setString(1, objeto.getChaveNota());
            stmt.setString(2, objeto.getSerieNota());
            stmt.setString(3, objeto.getNumNota());
            if (objeto.getDtEmissao() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(objeto.getDtEmissao()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            stmt.setBigDecimal(5, objeto.getVlrTotal());
            stmt.setLong(6, objeto.getIdFornecedor());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<NotaDTO> retornaTodos() {
        List<NotaDTO> lstResult = new ArrayList<>();
        String sql = "SELECT " +
                       "n.id as id, " +
                       "n.chave_acesso as chaveNota, " +
                       "n.n_serie as serieNota, " +
                       "n.n_nfe as numNota, " +
                       "n.dt_emissao as dtEmissao, " +
                       "n.vlr_total as vlrTotal, " +
                       "f.razao_social as nomeFornecedor " +
                       "from  " +
                       "  notas_f n " +
                       "left outer join " +
                       "  fornecedor f on n.fornecedor_id = f.id " +
                       "order by id;";
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                LocalDate dtEmissao = (rs.getDate("dtEmissao") == null) ? null : rs.getDate("dtEmissao").toLocalDate();
                lstResult.add(new NotaDTO(
                        rs.getLong("id"),
                        rs.getString("numNota"),
                        rs.getString("serieNota"),
                        rs.getString("chaveNota"),
                        rs.getBigDecimal("vlrTotal"),
                        dtEmissao,
                        rs.getString("nomeFornecedor")
                ));
            }
            return lstResult;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao retornar notas do banco: " + e.getMessage());
        }
    }

    @Override
    public NotaDTO retornaPorId(Long id) {
        String sql = "SELECT " +
                "n.id as id, " +
                "n.chave_acesso as chaveNota, " +
                "n.n_serie as serieNota, " +
                "n.n_nfe as numNota, " +
                "n.dt_emissao as dtEmissao, " +
                "n.vlr_total as vlrTotal, " +
                "f.razao_social as nomeFornecedor " +
                "from  " +
                "  notas_f n " +
                "left outer join " +
                "  fornecedor f on n.fornecedor_id = f.id " +
                "where n.id = ? "+
                "order by id;";
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                LocalDate dtEmissao = (rs.getDate("dtEmissao") == null) ? null : rs.getDate("dtEmissao").toLocalDate();
                return new NotaDTO(
                        rs.getLong("id"),
                        rs.getString("numNota"),
                        rs.getString("serieNota"),
                        rs.getString("chaveNota"),
                        rs.getBigDecimal("vlrTotal"),
                        dtEmissao,
                        rs.getString("nomeFornecedor")
                );
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao retornar nota do banco: " + e.getMessage());
        }
    }

    @Override
    public NotaDTO atualizar(Nota objeto) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }
}
