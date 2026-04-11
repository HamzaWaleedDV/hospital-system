package main.ui;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private static final int PANEL_WIDTH = 84;

    public void clearScreen() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }

    public void header(String title) {
        line('=');
        System.out.println(center(title));
        line('=');
    }

    public void section(String title) {
        line('-');
        System.out.println(title);
        line('-');
    }

    public void menuItem(int number, String label) {
        System.out.printf("%d) %s%n", number, label);
    }

    public void info(String message) {
        System.out.println("[INFO] " + message);
    }

    public void success(String message) {
        System.out.println("[OK] " + message);
    }

    public void warning(String message) {
        System.out.println("[WARN] " + message);
    }

    public void error(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void keyValue(String key, String value) {
        System.out.printf("%-24s : %s%n", key, value);
    }

    public void table(String[] headers, List<String[]> rows) {
        if (headers == null || headers.length == 0) {
            return;
        }

        int[] widths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            widths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < headers.length; i++) {
                String value = getCellValue(row, i);
                if (value.length() > widths[i]) {
                    widths[i] = value.length();
                }
            }
        }

        printRow(headers, widths);
        printDivider(widths);
        for (String[] row : rows) {
            printRow(row, widths);
        }
    }

    private void printDivider(int[] widths) {
        StringBuilder builder = new StringBuilder();
        for (int width : widths) {
            builder.append("+");
            builder.append("-".repeat(width + 2));
        }
        builder.append("+");
        System.out.println(builder);
    }

    private void printRow(String[] row, int[] widths) {
        List<String> cells = new ArrayList<>();
        for (int i = 0; i < widths.length; i++) {
            cells.add(getCellValue(row, i));
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < widths.length; i++) {
            builder.append("| ");
            builder.append(padRight(cells.get(i), widths[i]));
            builder.append(" ");
        }
        builder.append("|");
        System.out.println(builder);
    }

    private String getCellValue(String[] row, int index) {
        if (row == null || index >= row.length || row[index] == null) {
            return "";
        }
        return row[index];
    }

    private String padRight(String value, int width) {
        if (value.length() >= width) {
            return value;
        }
        return value + " ".repeat(width - value.length());
    }

    private void line(char symbol) {
        System.out.println(String.valueOf(symbol).repeat(PANEL_WIDTH));
    }

    private String center(String text) {
        if (text.length() >= PANEL_WIDTH) {
            return text;
        }
        int padding = (PANEL_WIDTH - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
}
