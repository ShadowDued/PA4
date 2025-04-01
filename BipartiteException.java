public class BipartiteException extends Exception {
    private String pattern;
    private int index;

    public BipartiteException(String pattern, int index) {
        this.pattern = pattern;
        this.index = index;
    }

    @Override
    public String getMessage() {
        return pattern + " is a balanced bipartite string found at index " + index + "!";
    }
}
