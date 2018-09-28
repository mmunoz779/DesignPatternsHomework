public enum Direction {
    FORWARD(100, "Forward"), BACKWARD (1, "Backward"), RIGHT(1, "Right"), LEFT(1, "Left");

    private final int rating;
    private final String name;

    Direction(int rating, String name) {
        this.rating = rating;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return this.rating;
    }

    @Override
    public String toString() {
        return getName();
    }
}