package main.ui;

import enums.BloodType;
import enums.Gender;
import java.util.Locale;
import java.util.Scanner;

public class Input {

    private final Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    public String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    public String readOptionalString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            String raw = readNonEmptyString(prompt);
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public int readInt(String prompt, int minValue) {
        while (true) {
            int value = readInt(prompt);
            if (value >= minValue) {
                return value;
            }
            System.out.println("Value must be at least " + minValue + ".");
        }
    }

    public int readIntInRange(String prompt, int minValue, int maxValue) {
        while (true) {
            int value = readInt(prompt);
            if (value >= minValue && value <= maxValue) {
                return value;
            }
            System.out.println("Please enter a number between " + minValue + " and " + maxValue + ".");
        }
    }

    public double readPositiveDouble(String prompt) {
        while (true) {
            String raw = readNonEmptyString(prompt);
            try {
                double value = Double.parseDouble(raw);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Value must be positive or zero.");
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public Gender readGender(String prompt) {
        while (true) {
            String raw = readNonEmptyString(prompt + " (MALE/FEMALE): ");
            String normalized = raw.trim().toUpperCase(Locale.ROOT);

            if (normalized.equals("M")) {
                return Gender.MALE;
            }
            if (normalized.equals("F")) {
                return Gender.FEMALE;
            }

            try {
                return Gender.valueOf(normalized);
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid gender. Valid options: MALE, FEMALE.");
            }
        }
    }

    public BloodType readBloodType(String prompt) {
        while (true) {
            System.out.println(prompt + " options:");
            BloodType[] values = BloodType.values();
            for (int i = 0; i < values.length; i++) {
                System.out.println((i + 1) + ". " + values[i].name() + " (" + values[i].getLabel() + ")");
            }

            String raw = readNonEmptyString("Choose by number, enum name, or label: ");
            String normalized = raw.trim().toUpperCase(Locale.ROOT).replace(' ', '_');

            try {
                int index = Integer.parseInt(normalized);
                if (index >= 1 && index <= values.length) {
                    return values[index - 1];
                }
            } catch (NumberFormatException ignored) {
                // Continue with textual parsing below.
            }

            for (BloodType value : values) {
                if (value.name().equals(normalized) || value.getLabel().equalsIgnoreCase(raw.trim())) {
                    return value;
                }
            }

            System.out.println("Invalid blood type. Please choose one of the listed options.");
        }
    }

    public boolean readYesNo(String prompt) {
        while (true) {
            String raw = readNonEmptyString(prompt + " (y/n): ");
            String normalized = raw.toLowerCase(Locale.ROOT);
            if (normalized.equals("y") || normalized.equals("yes")) {
                return true;
            }
            if (normalized.equals("n") || normalized.equals("no")) {
                return false;
            }
            System.out.println("Please answer with y or n.");
        }
    }

    public void pause() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
