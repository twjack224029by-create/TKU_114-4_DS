interface ReportExporter {
    void export(String title, int[] values);
}

class CsvExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("CSV 格式報表");
        System.out.println("Title: " + title);
        System.out.print("Values: ");
        if (values == null || values.length == 0) {
            System.out.println("(無資料)");
            return;
        }
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + (i < values.length - 1 ? "," : ""));
        }
        System.out.println("\n");
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("JSON 格式報表");
        System.out.println("{");
        System.out.println("  \"title\": \"" + title + "\",");
        System.out.print("  \"values\": ");
        if (values == null) {
            System.out.println("null");
        } else {
            System.out.print("[");
            for (int i = 0; i < values.length; i++) {
                System.out.print(values[i] + (i < values.length - 1 ? ", " : ""));
            }
            System.out.println("]");
        }
        System.out.println("}\n");
    }
}

class TextExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("Text 格式報表");
        System.out.println("標題: " + title);
        System.out.print("數據清單: ");
        if (values == null || values.length == 0) {
            System.out.println("尚無數據");
            return;
        }
        for (int val : values) {
            System.out.print("[" + val + "] ");
        }
        System.out.println("\n");
    }
}

public class ReportExporterFactory {

    public static ReportExporter createExporter(String format) {
        if (format == null) {
            return new TextExporter();
        }

        switch (format.trim().toUpperCase()) {
            case "CSV":
                return new CsvExporter();
            case "JSON":
                return new JsonExporter();
            case "TEXT":
            default:
                return new TextExporter();
        }
    }

    public static void exportReport(ReportExporter exporter, String title, int[] values) {
        if (exporter == null) {
            exporter = new TextExporter(); 
        }
        
        exporter.export(title, values);
    }

    public static void main(String[] args) {
        System.out.println("test \n");

        String title = "2026年第一季銷售數據";
        int[] scores = {120, 350, 240, 410};

        ReportExporter csvExporter = createExporter("CSV");
        exportReport(csvExporter, title, scores);

        ReportExporter jsonExporter = createExporter("json");
        exportReport(jsonExporter, title, scores);

        ReportExporter unknownExporter = createExporter("PDF");
        exportReport(unknownExporter, title, scores);

        System.out.println("測試邊界條件：values 為 null");
        ReportExporter textExporter = createExporter("TEXT");
        exportReport(textExporter, "空白銷售報告", null);
    }
}
