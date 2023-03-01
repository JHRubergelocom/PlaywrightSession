package report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class HtmlReport {

    private static String CreateHtmlHead(String reportTitle) {
        String htmlHead = "  <head>\n";
        htmlHead += "    <title>" + reportTitle + "</title>\n";
        htmlHead += "  </head>\n";
        return htmlHead;
    }
    private static String CreateHtmlStyle() {
        String htmlStyle = "  <style>\n";

        htmlStyle += "body {\n";
        htmlStyle += "  font-family: 'Courier', monospace;\n";
        htmlStyle += "  margin: 15px;\n";
        htmlStyle += "  font-size: 12px;\n";
        htmlStyle += "}\n";
        htmlStyle += "span {\n";
        htmlStyle += "  color: red;\n";
        htmlStyle += "  background-color: yellow;\n";
        htmlStyle += "}\n";
        htmlStyle += "table {\n";
        htmlStyle += "  font-size: 12px;\n";
        htmlStyle += "  padding-left: 10px;\n";
        htmlStyle += "  border-width: 0px;\n";
        htmlStyle += "  border-collapse: collapse;\n";
        htmlStyle += "  border-color: #A2A2A2;\n";
        htmlStyle += "}\n";
        htmlStyle += "table td {\n";
        htmlStyle += "  padding: 3px 7px;\n";
        htmlStyle += "}\n";
        htmlStyle += "table tr {\n";
        htmlStyle += "  white-space: nowrap;\n";
        htmlStyle += "}\n";
        htmlStyle += ".tdh {\n";
        htmlStyle += "  font-weight: bold;\n";
        htmlStyle += "  padding: 5px 5px 5px 5px;\n";
        htmlStyle += "  background-color: #A2A2A2;\n";
        htmlStyle += "  border-top-width: 1px;\n";
        htmlStyle += "  border-left-width: 1px;\n";
        htmlStyle += "  border-right-width: 1px;\n";
        htmlStyle += "  border-bottom-width: 1px;\n";
        htmlStyle += "  border-color: grey;\n";
        htmlStyle += "  border-style: solid;\n";
        htmlStyle += "  border-collapse: collapse;\n";
        htmlStyle += "}\n";
        htmlStyle += ".td1 {\n";
        htmlStyle += "  background-color: white;\n";
        htmlStyle += "  border-top-width: 0px;\n";
        htmlStyle += "  border-left-width: 1px;\n";
        htmlStyle += "  border-right-width: 1px;\n";
        htmlStyle += "  border-bottom-width: 0px;\n";
        htmlStyle += "  border-color: grey;\n";
        htmlStyle += "  border-style: solid;\n";
        htmlStyle += "  border-collapse: collapse;\n";
        htmlStyle += "}\n";
        htmlStyle += ".td2 {\n";
        htmlStyle += "  background-color: #A2A2A2;\n";
        htmlStyle += "  border-top-width: 0px;\n";
        htmlStyle += "  border-left-width: 1px;\n";
        htmlStyle += "  border-right-width: 1px;\n";
        htmlStyle += "  border-bottom-width: 0px;\n";
        htmlStyle += "  border-color: grey;\n";
        htmlStyle += "  border-style: solid;\n";
        htmlStyle += "  border-collapse: collapse;\n";
        htmlStyle += "}\n";
        htmlStyle += ".td1b {\n";
        htmlStyle += "  background-color: white;\n";
        htmlStyle += "  border-top-width: 0px;\n";
        htmlStyle += "  border-left-width: 1px;\n";
        htmlStyle += "  border-right-width: 1px;\n";
        htmlStyle += "  border-bottom-width: 1px;\n";
        htmlStyle += "  border-color: grey;\n";
        htmlStyle += "  border-style: solid;\n";
        htmlStyle += "  border-collapse: collapse;\n";
        htmlStyle += "}\n";
        htmlStyle += ".td2b {\n";
        htmlStyle += "  background-color: #A2A2A2;\n";
        htmlStyle += "  border-top-width: 0px;\n";
        htmlStyle += "  border-left-width: 1px;\n";
        htmlStyle += "  border-right-width: 1px;\n";
        htmlStyle += "  border-bottom-width: 1px;\n";
        htmlStyle += "  border-color: grey;\n";
        htmlStyle += "  border-style: solid;\n";
        htmlStyle += "  border-collapse: collapse;\n";
        htmlStyle += "}\n";
        htmlStyle += ".td1r {\n";
        htmlStyle += "  color: red;\n";
        htmlStyle += "  background-color: white;\n";
        htmlStyle += "  border-top-width: 0px;\n";
        htmlStyle += "  border-left-width: 1px;\n";
        htmlStyle += "  border-right-width: 1px;\n";
        htmlStyle += "  border-bottom-width: 0px;\n";
        htmlStyle += "  border-color: grey;\n";
        htmlStyle += "  border-style: solid;\n";
        htmlStyle += "  border-collapse: collapse;\n";
        htmlStyle += "}\n";
        htmlStyle += ".td2r {\n";
        htmlStyle += "  color: red;\n";
        htmlStyle += "  background-color: #A2A2A2;\n";
        htmlStyle += "  border-top-width: 0px;\n";
        htmlStyle += "  border-left-width: 1px;\n";
        htmlStyle += "  border-right-width: 1px;\n";
        htmlStyle += "  border-bottom-width: 0px;\n";
        htmlStyle += "  border-color: grey;\n";
        htmlStyle += "  border-style: solid;\n";
        htmlStyle += "  border-collapse: collapse;\n";
        htmlStyle += "}\n";
        htmlStyle += ".td1br {\n";
        htmlStyle += "  color: red;\n";
        htmlStyle += "  background-color: white;\n";
        htmlStyle += "  border-top-width: 0px;\n";
        htmlStyle += "  border-left-width: 1px;\n";
        htmlStyle += "  border-right-width: 1px;\n";
        htmlStyle += "  border-bottom-width: 1px;\n";
        htmlStyle += "  border-color: grey;\n";
        htmlStyle += "  border-style: solid;\n";
        htmlStyle += "  border-collapse: collapse;\n";
        htmlStyle += "}\n";
        htmlStyle += ".td2br {\n";
        htmlStyle += "  color: red;\n";
        htmlStyle += "  background-color: #A2A2A2;\n";
        htmlStyle += "  border-top-width: 0px;\n";
        htmlStyle += "  border-left-width: 1px;\n";
        htmlStyle += "  border-right-width: 1px;\n";
        htmlStyle += "  border-bottom-width: 1px;\n";
        htmlStyle += "  border-color: grey;\n";
        htmlStyle += "  border-style: solid;\n";
        htmlStyle += "  border-collapse: collapse;\n";
        htmlStyle += "}\n";
        htmlStyle += "h1 {\n";
        htmlStyle += "  padding: 30px 0px 0px 0px;\n";
        htmlStyle += "  font-size: 16px;\n";
        htmlStyle += "  font-weight: bold;\n";
        htmlStyle += "}\n";

        htmlStyle += "  </style>\n";
        return htmlStyle;
    }
    private static String CreateHtmlTable(ReportTable reportTable) {
        String htmlTable = "    <div class='container'>\n";
        htmlTable += "      <table border='2'>\n";
        htmlTable += "        <colgroup>\n";
        for (String col: reportTable.getTableCols()) {
            htmlTable += "          <col width='100'>\n";
        }
        htmlTable += "        </colgroup>\n";
        htmlTable += "        <tr>\n";
        for (String col: reportTable.getTableCols()) {
            htmlTable += "          <td class = 'tdh' align='left' valign='top'>" + col + "</td>\n";
        }
        htmlTable += "        </tr>\n";
        int i = 0;
        for (List<String> row : reportTable.getTableCells()) {
            String td = "td2";
            if ((i % 2) == 0) {
                td = "td1";
            }
            if (i == (reportTable.getTableCells().size() - 1)) {
                td += "b";
            }
            htmlTable += "        <tr>\n";

            td = row.stream().filter((cell) -> (cell.equals("false"))).map((_item) -> "r").reduce(td, String::concat);
            for (String cell : row) {
                htmlTable += "          <td class = '" + td + "' align='left' valign='top'>" + cell + "</td>\n";
            }

            htmlTable += "        </tr>\n";
            i++;
        }
        htmlTable += "      </table>\n";
        htmlTable += "    </div>\n";

        return htmlTable;
    }
    private static String CreateHtmlParagraph(ReportParagraph reportParagraph) {
        String htmlParagraph = "";
        for (String htmlHeader: reportParagraph.getReportHeader()) {
            htmlParagraph += "    <h1>" + htmlHeader + "</h1>\n";
        }
        for (String htmlText: reportParagraph.getReportText()) {
            htmlParagraph += "    <p>" + htmlText + "</p>\n";
        }
        htmlParagraph += CreateHtmlTable(reportParagraph.getReportTable());

        return htmlParagraph;
    }
    public static String createReport(ReportData reportData) {
        String htmlDoc = "<html>\n";
        String htmlHead = CreateHtmlHead(reportData.getReportTitle());
        String htmlStyle = CreateHtmlStyle();
        String htmlBody = "<body>\n";

        for (ReportParagraph reportParagraph : reportData.getReportParagraphs()) {
            String htmlParagraph = CreateHtmlParagraph(reportParagraph);
            htmlBody += htmlParagraph;
        }

        htmlBody += "</body>\n";
        htmlDoc += htmlHead;
        htmlDoc += htmlStyle;
        htmlDoc += htmlBody;
        htmlDoc += "</html>\n";

        return htmlDoc;
    }
    public static void showReport(String htmlDoc) {
        String reportPath = "Report.html";
        File reportFile = new File(reportPath);
        URI uri = reportFile.toURI();
        try {
            if (!reportFile.exists()) {
                reportFile.createNewFile();
            }
            FileWriter fw = new FileWriter(reportFile);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(htmlDoc);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        try {
            URL url = uri.toURL();
            OpenUrl(url.toString());
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
    }
    private static void OpenUrl(String url) {
        if(java.awt.Desktop.isDesktopSupported() ) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            java.net.URI uri;
            try {
                if(desktop.isSupported(java.awt.Desktop.Action.BROWSE) ) {
                    uri = new java.net.URI(url);
                    try {
                        desktop.browse(uri);
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            } catch (URISyntaxException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
