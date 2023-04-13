package test;

import org.junit.jupiter.api.Test;
import report.HtmlReport;
import report.ReportData;
import report.ReportParagraph;
import report.ReportTable;
import session.BaseFunctions;

import java.util.ArrayList;
import java.util.List;

public class PlaywrightReportTest {
    private ReportData createReportData() {

        final String reportTitle = "Report Test";
        final List<ReportParagraph> reportParagraphs = new ArrayList<>();

        // Paragraph1
        List<String> reportHeader = new ArrayList<>();
        reportHeader.add("Absatz1");

        List<String> reportText = new ArrayList<>();
        reportText.add("Beschreibung Absatz1");

        List<String> tableCols = new ArrayList<>();
        tableCols.add("Spalte11");
        tableCols.add("Spalte12");

        List<List<String>> tableCells = new ArrayList<>();

        List<String> tableLineCells = new ArrayList<>();
        tableLineCells.add("Zelle11");
        tableLineCells.add("Zelle12");
        tableCells.add(tableLineCells);

        tableLineCells = new ArrayList<>();
        tableLineCells.add("Zelle21");
        tableLineCells.add("Zelle22");
        tableCells.add(tableLineCells);

        tableLineCells = new ArrayList<>();
        tableLineCells.add("Zelle31");
        tableLineCells.add("Zelle32");
        tableCells.add(tableLineCells);

        ReportTable reportTable = new ReportTable(tableCols, tableCells);

        ReportParagraph reportParagraph = new ReportParagraph(reportHeader, reportText, reportTable);
        reportParagraphs.add(reportParagraph);

        // Paragraph2
        reportHeader = new ArrayList<>();
        reportHeader.add("Absatz2");

        reportText = new ArrayList<>();
        reportText.add("Beschreibung Absatz2");

        tableCols = new ArrayList<>();
        tableCols.add("Spalte21");
        tableCols.add("Spalte22");
        tableCols.add("Spalte23");

        tableCells = new ArrayList<>();

        tableLineCells = new ArrayList<>();
        tableLineCells.add("Zelle11");
        tableLineCells.add("Zelle12");
        tableLineCells.add("Zelle13");
        tableCells.add(tableLineCells);

        tableLineCells = new ArrayList<>();
        tableLineCells.add("Zelle21");
        tableLineCells.add("Zelle22");
        tableLineCells.add("Zelle23");
        tableCells.add(tableLineCells);

        tableLineCells = new ArrayList<>();
        tableLineCells.add("Zelle31");
        tableLineCells.add("Zelle32");
        tableLineCells.add("Zelle33");
        tableCells.add(tableLineCells);

        tableLineCells = new ArrayList<>();
        tableLineCells.add("Zelle41");
        tableLineCells.add("Zelle42");
        tableLineCells.add("Zelle43");
        tableCells.add(tableLineCells);

        reportTable = new ReportTable(tableCols, tableCells);

        reportParagraph = new ReportParagraph(reportHeader, reportText, reportTable);
        reportParagraphs.add(reportParagraph);

        // Grafik
        reportHeader = new ArrayList<>();
        reportHeader.add("<span>Einbetten Grafik</span>");

        reportText = new ArrayList<>();
        reportText.add("<img src=\"example.png\" alt=\"Example.png\" loading=\"lazy\">");

        tableCols = new ArrayList<>();
        tableCells = new ArrayList<>();

        reportTable = new ReportTable(tableCols, tableCells);

        reportParagraph = new ReportParagraph(reportHeader, reportText, reportTable);
        reportParagraphs.add(reportParagraph);

        return new ReportData(reportTitle, reportParagraphs);
    }
    @Test
    public void testPlaywrightReport() {
        ReportData reportData = createReportData();
        System.out.println(reportData);

        String htmlDoc = HtmlReport.createReport(reportData);
        HtmlReport.showReport(BaseFunctions.getTestReportDir(), htmlDoc);
    }
}
