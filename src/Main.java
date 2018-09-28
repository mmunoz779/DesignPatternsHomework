import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    private static String helpMessage = "Type \"connect\" to connect to the database" +
            "\nCommands while connected: \n" +
            "disconnect - Terminates connection to the database\n" +
            "drive [hours] - Simulates a drive of length hours generating metrics, replace hours with an integer number of hours that were driven. 1 hour is default. Date is randomized per driving session\n" +
            "NOTE: date inputs are in the format mm/dd/yyyy" +
            "check [date] - Checks the driving metrics provided by the dashboard with optional date, if not provided will return complete database\n" +
            "detailed [date] - provides a completed detailed list of all metrics generated during the drive on the optional date,if not provided will return complete database\n" +
            "help - Show this message\n" +
            "exit - Ends this demo\n";

    public static void main(String[] args) {
        String command;
        Scanner userInput = new Scanner(System.in);
        System.out.print(helpMessage);
        command = userInput.nextLine();
        while (!command.equalsIgnoreCase("exit")) {
            if (!execute(command))
                System.out.println("Command resulted in error, please try again...\nCommon errors are not being connected to the database or entering in a wrong command.");
            command = userInput.nextLine();
        }
        System.out.println("Exited successfully");
    }

    private static boolean execute(String command) {
        try {
            if (command.startsWith("connect"))
                testConnectDB();
            else if (command.startsWith("drive")) {
                if (command.trim().equalsIgnoreCase("drive"))
                    // Default to 1 hour if not specified
                    generateMetrics(1);
                else
                    generateMetrics(Integer.parseInt(command.split(" ")[1]));
            } else if (command.startsWith("check")) {
                if (command.trim().equalsIgnoreCase("check"))
                    // Default to 1 hour if not specified
                    getMetrics(null, false);
                else
                    getMetrics(sdf.parse(command.split(" ", 2)[1]), false);
            } else if (command.startsWith("detailed")) {
                if (command.trim().equalsIgnoreCase("detailed"))
                    // Default to 1 hour if not specified
                    getMetrics(null, true);
                else
                    getMetrics(sdf.parse(command.split(" ", 2)[1]), true);
            } else if (command.startsWith("disconnect"))
                System.out.println(Database.disconnect());
            else if (command.startsWith("help"))
                System.out.print(helpMessage);
            else
                return false;
        } catch (Exception e) {
            return false;
        }
        System.out.println("Command executed successfully");
        return true;
    }

    private static void getMetrics(Date date, boolean detailed) {
        if (date == null) {
            ArrayList<Metric> metrics = Database.getMetrics();
            if (detailed) {
                for (Metric m : metrics) {
                    System.out.printf("Date: %s, Speed: %s, Direction: %s\n", (m.getDate().getMonth() + 1) + "/" + m.getDate().getDate() + "/" + (m.getDate().getYear() + 1900), m.getSpeed(), m.getDirection());
                }
            }
            Grade grade = Database.getGrade(metrics);
            System.out.println("The grade for this time period is: " + grade.getLetterGrade() + " with a rating of " + (int) grade.getNumericalRating());
        } else {
            ArrayList<Metric> metrics = Database.getMetricsForDate(date);
            if (detailed) {
                for (Metric m : metrics) {
                    System.out.printf("Date: %s, Speed: %s, Direction: %s\n", (m.getDate().getMonth() + 1) + "/" + m.getDate().getDate() + "/" + (m.getDate().getYear() + 1900), m.getSpeed(), m.getDirection());
                }
            }
            Grade grade = Database.getGrade(metrics);
            System.out.println("The grade for this time period is: " + grade.getLetterGrade() + " with a rating of " + (int) grade.getNumericalRating());
        }
    }

    private static void generateMetrics(int hours) {
        int seed = (int) ((Math.random() * 29)) + 1;
        for (int i = 0; i < 100 * hours; i++) {
            Database.uploadMetric(new Metric(seed));
        }
        Date date = new Date(2018, 9, seed);
        System.out.printf("Metrics uploaded successfully for the date %s\n", date.getMonth() + "/" + date.getDate() + "/" + date.getYear());
    }

    private static void testConnectDB() {
        Database.connect();
        System.out.println("Connection Successful");
    }

}
