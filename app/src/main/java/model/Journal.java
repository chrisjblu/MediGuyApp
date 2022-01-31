package model;

import com.google.firebase.Timestamp;

public class Journal {
    private String title;
    private String JournalEntry;
    private  String imageUrl;
    private String userId;
    private Timestamp timedAdded;
    private String userName;

    public Journal() {
    }

    public Journal(String title, String journalEntry, String imageUrl, String userId, Timestamp timedAdded, String userName) {
        this.title = title;
        JournalEntry = journalEntry;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.timedAdded = timedAdded;
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJournalEntry() {
        return JournalEntry;
    }

    public void setJournalEntry(String journalEntry) {
        JournalEntry = journalEntry;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTimedAdded() {
        return timedAdded;
    }

    public void setTimedAdded(Timestamp timedAdded) {
        this.timedAdded = timedAdded;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
