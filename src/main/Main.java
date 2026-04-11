package main;

import main.bootstrap.DemoDataSeeder;
import main.ui.ConsoleApp;
import services.HospitalService;

public class Main {

    public static void main(String[] args) {
        HospitalService hospitalService = new HospitalService();
        DemoDataSeeder.seed(hospitalService);

        ConsoleApp app = new ConsoleApp(hospitalService);
        app.run();
    }
}
