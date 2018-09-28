import java.util.Date;

public class Metric {
    private Direction direction;
    private Date date;
    private int speed;

    public Date getDate() {
        return date;
    }

    public Metric(int seed) {
        this.speed = (int) Accelerometer.getAccelerometer().getSpeed();
        this.direction = Accelerometer.getAccelerometer().getDirection();
        this.date = new Date(2018 - 1900, 9 - 1, seed);
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }
}
