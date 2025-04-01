public class PalindromeException extends Exception {
    private String pattern;
    private int index;

    public PalindromeException(String pattern, int index) {
        this.pattern = pattern;
        this.index = index;
    }

    @Override
    public String getMessage() {
        return pattern + " is a palindrome found at index " + index + "!";
    }
}
