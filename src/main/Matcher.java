import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Matcher {
    private final int maxTimeMillis;

    public Matcher() {
        this(1000);
    }

    public Matcher(int maxTimeMillis) {
        this.maxTimeMillis = maxTimeMillis;
    }

    public boolean matches(String text, String regex) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            return false;
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> handler = executor.submit(() -> pattern.matcher(text).matches());
        boolean result;
        try {
            result = handler.get(maxTimeMillis, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            result = false;
        }
        handler.cancel(true);
        executor.shutdownNow();
        return result;
    }
}
