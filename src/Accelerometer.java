public class Accelerometer {

    private double acceleration;
    private double speed;
    private Direction direction;
    private static Accelerometer accelerometer = null;

    private Accelerometer() {
        this.acceleration = Math.random() * 30; // MPH/H
        this.speed = Math.random() * 100; // MPH
        switch ((int) (Math.random() * 6)) {
            case 0:
                this.direction = Direction.FORWARD;
                break;
            case 1:
                this.direction = Direction.BACKWARD;
                break;
            case 2:
                this.direction = Direction.RIGHT;
                break;
            case 3:
                this.direction = Direction.LEFT;
                break;
            case 4:
                this.direction = Direction.FORWARD; // increase weight towards forward driving
                break;
            case 5:
                this.direction = Direction.FORWARD; // increase weight towards forward driving
                break;
            case 6:
                this.direction = Direction.FORWARD; // increase weight towards forward driving
                break;
        }
    }

    public static Accelerometer getAccelerometer() {
        if (accelerometer == null) accelerometer = new Accelerometer();
        return accelerometer;
    }

    public double getSpeed() {
        randomize();
        return speed;
    }

    public Direction getDirection() {
        randomize();
        return direction;
    }

    public double getAcceleration() {
        randomize();
        return acceleration;
    }

    private void randomize() {
        this.acceleration = Math.random() * 30; // MPH/H
        this.speed = Math.random() * 80; // MPH
        switch ((int) (Math.random() * 6)) {
            case 0:
                this.direction = Direction.FORWARD;
                break;
            case 1:
                this.direction = Direction.BACKWARD;
                break;
            case 2:
                this.direction = Direction.RIGHT;
                break;
            case 3:
                this.direction = Direction.LEFT;
                break;
            case 4:
                this.direction = Direction.FORWARD; // increase weight towards forward driving
                break;
            case 5:
                this.direction = Direction.FORWARD; // increase weight towards forward driving
                break;
            case 6:
                this.direction = Direction.FORWARD; // increase weight towards forward driving
                break;
        }
    }
}