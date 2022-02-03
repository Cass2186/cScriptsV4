package scripts.Listeners;

public interface MuleListener {
    void onMuleNearby(String muleName);

    void onMuleLeave(String muleName);
}