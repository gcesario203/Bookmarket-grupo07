package servico.shared;

public class IdGenerator {
    private static IdGenerator instance;
    private int reviewId;

    private IdGenerator() {
        reviewId = 1;
    }
    
    public void reset() {
    	reviewId = 1;
    }

    public static synchronized IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }

    public synchronized int getNextReviewId() {
        return ++reviewId;
    }

    public static synchronized void resetInstance() {
        instance = null;
    }
}