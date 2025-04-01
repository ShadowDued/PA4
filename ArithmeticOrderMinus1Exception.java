public class ArithmeticOrderMinus1Exception extends Exception {
    private String pattern;
    private int index;

    public ArithmeticOrderMinus1Exception(String pattern, int index) {
        this.pattern = pattern;
        this.index = index;
    }

    @Override
    public String getMessage() {
        return pattern + " is an arithmetic string of order -1 found at index " + index + "!";
    }
}
