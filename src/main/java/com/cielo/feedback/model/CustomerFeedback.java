public class CustomerFeedback {
    private String id;
    private FeedbackType type;
    private String message;
    private FeedbackStatus status;

    public CustomerFeedback(String id, FeedbackType type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.status = FeedbackStatus.RECEBIDO;
    }
}
