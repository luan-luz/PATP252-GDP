package ideau.controlePatrimonioAPI.services;

import ideau.controlePatrimonioAPI.model.PatrimonioDTO;
import ideau.controlePatrimonioAPI.model.Relatorio;
import ideau.controlePatrimonioAPI.model.RelatorioDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RelatorioService extends GenericService<Relatorio, RelatorioDTO> {
    List<RelatorioDTO> RetornarRequest(Long idStatus);
}
