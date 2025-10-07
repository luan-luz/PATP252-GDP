package ideau.controlePatrimonioAPI.services.implementation;

import ideau.controlePatrimonioAPI.model.DashboardDTO;
import ideau.controlePatrimonioAPI.model.DepreciacaoDashboardDTO;
import ideau.controlePatrimonioAPI.repository.implementation.DashboardRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardServiceImpl {

    private final DashboardRepository dashboardRepository;

    public DashboardServiceImpl(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public DashboardDTO getDashboardData() {
        long qtdItens = dashboardRepository.countItens();
        long qtdCategorias = dashboardRepository.countCategorias();
        BigDecimal valorTotal = dashboardRepository.sumValorTotal();
        List<DepreciacaoDashboardDTO> depreciacoes = dashboardRepository.findDepreciacaoUltimos6Anos();

        return new DashboardDTO(qtdItens, qtdCategorias, valorTotal, depreciacoes);
    }
}
