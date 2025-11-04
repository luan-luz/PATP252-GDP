package ideau.controlePatrimonioAPI.services;

import com.itextpdf.layout.element.Table;
import ideau.controlePatrimonioAPI.model.PatrimonioDTO;
import ideau.controlePatrimonioAPI.repository.implementation.PatrimonioRepositoryImpl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final PatrimonioRepositoryImpl patrimonioRepository;

    public RelatorioService(PatrimonioRepositoryImpl patrimonioRepository) {
        this.patrimonioRepository = patrimonioRepository;
    }

    public byte[] gerarRelatorio(LocalDate de, LocalDate ate, String setor, String categoria,
                                 String situacao, String imprimirValores, String exportar) throws Exception {

        List<PatrimonioDTO> patrimonios = filtrarPorStatus(situacao);

        patrimonios = patrimonios.stream()
                .filter(p -> (de == null || !p.getDtAquisicao().isBefore(de)))
                .filter(p -> (ate == null || !p.getDtAquisicao().isAfter(ate)))
                .filter(p -> (categoria == null || categoria.isEmpty() || p.getNomeItem().equalsIgnoreCase(categoria)))
                .filter(p -> (setor == null || setor.isEmpty() || p.getNomeLocal().equalsIgnoreCase(setor)))
                .collect(Collectors.toList());

        if ("PDF".equalsIgnoreCase(exportar)) {
            return gerarPdf(patrimonios, imprimirValores);
        } else {
            return gerarExcel(patrimonios, imprimirValores);
        }
    }

    private byte[] gerarExcel(List<PatrimonioDTO> patrimonios, String imprimirValores) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório");

        // Cabeçalho
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Item");
        header.createCell(2).setCellValue("Status");
        header.createCell(3).setCellValue("Local");
        header.createCell(4).setCellValue("Nota");
        if ("Sim".equalsIgnoreCase(imprimirValores)) {
            header.createCell(5).setCellValue("Valor");
            header.createCell(6).setCellValue("Depreciação");
            header.createCell(7).setCellValue("Data Aquisição");
        } else {
            header.createCell(5).setCellValue("Data Aquisição");
        }

        int rowNum = 1;
        for (PatrimonioDTO p : patrimonios) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getNomeItem());
            row.createCell(2).setCellValue(p.getNomeStatus());
            row.createCell(3).setCellValue(p.getNomeLocal());
            row.createCell(4).setCellValue(p.getNumNota());
            if ("Sim".equalsIgnoreCase(imprimirValores)) {
                row.createCell(5).setCellValue(p.getValCompra() != null ? p.getValCompra().doubleValue() : 0);
                row.createCell(6).setCellValue(p.getAliqDeprecMes() != null ? p.getAliqDeprecMes().doubleValue() : 0);
                row.createCell(7).setCellValue(p.getDtAquisicao() != null ? p.getDtAquisicao().toString() : "");
            } else {
                row.createCell(5).setCellValue(p.getDtAquisicao() != null ? p.getDtAquisicao().toString() : "");
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    public List<PatrimonioDTO> filtrarPorStatus(String situacao) {
        if (situacao == null || situacao.isEmpty()) {
            return patrimonioRepository.retornaTodos();
        }
//        List<Status> lstStatus =
//        Long idStatusParam = Long.valueOf(situacao);
//        return patrimonioRepository.retornaTodos()
//                .stream()
//                .filter(p -> p.getIdStatus().equals(idStatus))
//                .collect(Collectors.toList());
        return null;
    }

    private byte[] gerarPdf(List<PatrimonioDTO> patrimonios, String imprimirValores) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        int colCount = "Sim".equalsIgnoreCase(imprimirValores) ? 8 : 6;
        Table table = new Table(colCount);

        table.addHeaderCell("ID");
        table.addHeaderCell("Item");
        table.addHeaderCell("Status");
        table.addHeaderCell("Local");
        table.addHeaderCell("Nota");
        if ("Sim".equalsIgnoreCase(imprimirValores)) {
            table.addHeaderCell("Valor");
            table.addHeaderCell("Depreciação");
            table.addHeaderCell("Data Aquisição");
        } else {
            table.addHeaderCell("Data Aquisição");
        }

        for (PatrimonioDTO p : patrimonios) {
            table.addCell(String.valueOf(p.getId()));
            table.addCell(p.getNomeItem());
            table.addCell(p.getNomeStatus());
            table.addCell(p.getNomeLocal());
            table.addCell(p.getNumNota());
            if ("Sim".equalsIgnoreCase(imprimirValores)) {
                table.addCell(p.getValCompra() != null ? p.getValCompra().toString() : "0");
                table.addCell(p.getAliqDeprecMes() != null ? p.getAliqDeprecMes().toString() : "0");
                table.addCell(p.getDtAquisicao() != null ? p.getDtAquisicao().toString() : "");
            } else {
                table.addCell(p.getDtAquisicao() != null ? p.getDtAquisicao().toString() : "");
            }
        }

        document.add(table);
        document.close();
        return out.toByteArray();
    }
}
