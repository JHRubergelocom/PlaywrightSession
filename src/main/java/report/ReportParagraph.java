package report;

import java.util.List;

public class ReportParagraph {
    private final List<String> reportHeader;
    private final List<String> reportText;
    private final ReportTable reportTable;

    public ReportParagraph(List<String> reportHeader, List<String> reportText, ReportTable reportTable) {
        this.reportHeader = reportHeader;
        this.reportText = reportText;
        this.reportTable = reportTable;
    }

    public List<String> getReportHeader() {
        return reportHeader;
    }

    public List<String> getReportText() {
        return reportText;
    }

    public ReportTable getReportTable() {
        return reportTable;
    }

    @Override
    public String toString() {
        return "ReportParagraph{" +
                "reportHeader=" + reportHeader +
                ", reportText=" + reportText +
                ", reportTable=" + reportTable +
                '}';
    }
}
