package interfaces;

public interface Schedulable {
    void schedule(String dateTime);
    void cancel();
    boolean isAvailable(String dateTime);
}