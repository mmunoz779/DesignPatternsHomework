public class Grade {

    private char letterGrade;
    private double numericalRating;

    public Grade(double numericalRating, long certainty) {
        this.numericalRating = (numericalRating / certainty);
        this.letterGrade = numberToLetterGrade(this.numericalRating);
    }

    public char getLetterGrade() {
        return this.letterGrade;
    }

    public double getNumericalRating() {
        return numericalRating;
    }

    private char numberToLetterGrade(double rating) {
        double percent = (rating / 70);
        if (((int) (percent * 100) % 100) > 90) {
            return 'A';
        } else if (isBetween(80, 89, (int) (percent * 100) % 100)) {
            return 'B';
        } else if (isBetween(70, 79, (int) (percent * 100) % 100)) {
            return 'C';
        } else if (isBetween(60, 69, (int) (percent * 100) % 100)) {
            return 'D';
        } else {
            return 'E';
        }
    }

    private boolean isBetween(int low, int high, int val) {
        return (val >= low) && (val <= high);
    }

}
