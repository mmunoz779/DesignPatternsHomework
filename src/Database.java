import java.util.Calendar;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.stream.Collectors;

class Database {

    private static Database database = null;

    private static Database disconnectedBackup = null;

    private static Grade grade;

    private static ArrayList<Metric> metrics;

    private Database() {
        metrics = new ArrayList<>();
    }

    static Database connect() {
        if (database == null && disconnectedBackup == null) database = new Database();
        else if (database == null) database = disconnectedBackup;
        return database;
    }

    String disconnect() throws NotYetConnectedException {
        if (database == null) throw new NotYetConnectedException();
        disconnectedBackup = database;
        database = null;
        return "Disconnected successfully";
    }

    void uploadMetric(Metric m) throws NotYetConnectedException {
        if (database == null) throw new NotYetConnectedException();
        metrics.add(m);
    }

    ArrayList<Metric> getMetrics()throws NotYetConnectedException {
        if (database == null) throw new NotYetConnectedException();
        return metrics;
    }

    ArrayList<Metric> getMetricsForDate(Calendar date)throws NotYetConnectedException {
        if (database == null) throw new NotYetConnectedException();
        return metrics.stream().filter(metric ->
                (metric.getDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH))
                && (metric.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH))
                && (metric.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    Grade getGrade(ArrayList<Metric> metrics) throws NotYetConnectedException {
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
