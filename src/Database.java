import javafx.util.Pair;

import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class Database {

    private static Database database = null;

    private static Database disconnectedBackup = null;

    private static Grade grade;

    private static ArrayList<Metric> metrics;

    private Database() {
        metrics = new ArrayList<>();
    }

    public static Database connect() {
        if (database == null && disconnectedBackup == null) database = new Database();
        else if (database == null) database = disconnectedBackup;
        return database;
    }

    public static String disconnect() throws NotYetConnectedException {
        if (database == null) throw new NotYetConnectedException();
        disconnectedBackup = database;
        database = null;
        return "Disconnected successfully";
    }

    public static void uploadMetric(Metric m) throws NotYetConnectedException {
        if (database == null) throw new NotYetConnectedException();
        metrics.add(m);
    }

    public static ArrayList<Metric> getMetrics()throws NotYetConnectedException {
        if (database == null) throw new NotYetConnectedException();
        return metrics;
    }

    public static ArrayList<Metric> getMetricsForDate(Date date)throws NotYetConnectedException {
        if (database == null) throw new NotYetConnectedException();
        return metrics.stream().filter(metric -> metric.getDate().equals(date)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static Grade getGrade(ArrayList<Metric> metrics) throws NotYetConnectedException {
        if (database == null) throw new NotYetConnectedException();
        parseDrivingData(metrics);
        return grade;
    }

    private static void parseDrivingData(ArrayList<Metric> metrics) throws NotYetConnectedException {
        if (database == null) throw new NotYetConnectedException();
        double netRating = 0;
        for (Metric m : metrics)
            netRating += (m.getSpeed() > 30 && (m.getDirection() == Direction.BACKWARD
                    || m.getDirection() == Direction.RIGHT || m.getDirection() == Direction.LEFT) ? (-1) : 1)
                    * m.getDirection().getRating()
                    * (m.getSpeed() > 50 ? (1 / m.getSpeed()) : 2);
        grade = new Grade(netRating, metrics.size());
    }

}
