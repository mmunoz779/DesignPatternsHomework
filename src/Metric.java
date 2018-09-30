import java.util.Calendar;
import java.util.Date;

class Metric {
    private Direction direction;
    private Calendar date;
    private int speed;

    Calendar getDate() {
        return date;
    }

    private Metric(int seed) {
        this.speed = (int) Accelerometer.getAccelerometer().getSpeed();
        this.direction = Accelerometer.getAccelerometer().getDirection();
        date = new Calendar.Builder().setDate(2018, 9, seed).build();
    }

    static Metric getMetric(int seed) {
        return new Metric(seed);
    }

    Direction getDirection() {
        return direction;
    }

    int getSpeed() {
        return speed;
    }
}
