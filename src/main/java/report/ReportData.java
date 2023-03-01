package report;

import java.util.List;

public class ReportData {
    private final String reportTitle;
    private final List<ReportParagraph> reportParagraphs;

    public ReportData(String reportTitle, List<ReportParagraph> reportParagraphs) {
        this.reportTitle = reportTitle;
        this.reportParagraphs = reportParagraphs;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public List<ReportParagraph> getReportParagraphs() {
        return reportParagraphs;
    }

    @Override
    public String toString() {
        return "ReportData{" +
                "reportTitle='" + reportTitle + '\'' +
                ", reportParagraphs=" + reportParagraphs +
                '}';
    }
}
