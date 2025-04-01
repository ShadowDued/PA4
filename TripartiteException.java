public class TripartiteException extends Exception {
    private String pattern;
    private int index;

    public TripartiteException(String pattern, int index) {
        this.pattern = pattern;
        this.index = index;
    }

    @Override
    public String getMessage() {
        return pattern + " is a balanced tripartite string found at index " + index + "!";
    }
}
