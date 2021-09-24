package entity;

public class Review {
    private long id;
    private String text;
    private int rating;
    private long repairmanId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getRepairmanId() {
        return repairmanId;
    }

    public void setRepairmanId(long repairmanId) {
        this.repairmanId = repairmanId;
    }
}
