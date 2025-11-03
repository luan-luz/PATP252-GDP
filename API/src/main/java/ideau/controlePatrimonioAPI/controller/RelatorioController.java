package ideau.controlePatrimonioAPI.controller;

import ideau.controlePatrimonioAPI.services.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/relatorios")
    public ResponseEntity<byte[]> gerarRelatorio(
            @RequestParam(required = false) String de,
            @RequestParam(required = false) String ate,
            @RequestParam(required = false) String setor,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String situacao,
            @RequestParam(defaultValue = "Sim") String imprimirValores,
            @RequestParam(defaultValue = "Excel") String exportar
    ) throws Exception {
        LocalDate dataDe = de != null ? LocalDate.parse(de) : null;
        LocalDate dataAte = ate != null ? LocalDate.parse(ate) : null;
        byte[] relatorio = relatorioService.gerarRelatorio(
                dataDe, dataAte, setor, categoria, situacao, imprimirValores, exportar
        );

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=relatorio." + exportar.toLowerCase())
                .body(relatorio);
    }
}
