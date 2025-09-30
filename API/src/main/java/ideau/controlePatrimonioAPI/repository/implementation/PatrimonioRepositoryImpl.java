package ideau.controlePatrimonioAPI.repository.implementation;

import ideau.controlePatrimonioAPI.model.Patrimonio;
import ideau.controlePatrimonioAPI.model.PatrimonioDTO;
import ideau.controlePatrimonioAPI.repository.GenericRepository;
import ideau.controlePatrimonioAPI.repository.PatrimonioRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                "  id_setor," +
                "  id_status," +
                "  id_nota," +
                "  num_patr," +
                "  val_compr," +
                "  aliq_deprec_mes," +
                "  dt_aquisicao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = con.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, objeto.getIdItem());
            stmt.setLong(2, objeto.getIdSetor());
            stmt.setLong(2, objeto.getIdStatus());
            stmt.setLong(2, objeto.getIdNota());
            stmt.setString(2, objeto.getNumPatr());
            stmt.setBigDecimal(2, objeto.getvalCompra());
            stmt.setBigDecimal(2, objeto.getAliqDeprecMes());
            stmt.setDate(2, java.sql.Date.valueOf(objeto.getDtAquisicao()));

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
        return List.of();
    }

    @Override
    public PatrimonioDTO retornaPorId(Long id) {
        return null;
    }

    @Override
    public PatrimonioDTO atualizar(Patrimonio objeto) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }
}
