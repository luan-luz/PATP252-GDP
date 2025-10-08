package ideau.controlePatrimonioAPI.controller.implementation;

import ideau.controlePatrimonioAPI.model.DashboardDTO;
import ideau.controlePatrimonioAPI.services.implementation.DashboardServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final DashboardServiceImpl dashboardService;

    public DashboardController(DashboardServiceImpl dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/api/dashboard")
    public DashboardDTO getDashboardData() {
        return dashboardService.getDashboardData();
    }
}
