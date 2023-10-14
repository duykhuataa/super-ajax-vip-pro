package model;

import java.sql.Timestamp;

public class Notification {

    private int notificationId;
    private int fromUserId;
    private int toUserId;
    private String title;
    private String content;
    private byte isRead;
    private Timestamp dateCreated;
    private byte isDeleted;

    public Notification() {
    }

    public Notification(int notificationId, int fromUserId, int toUserId, String title, String content, byte isRead, Timestamp dateCreated, byte isDeleted) {
        this.notificationId = notificationId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.title = title;
        this.content = content;
        this.isRead = isRead;
        this.dateCreated = dateCreated;
        this.isDeleted = isDeleted;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public byte getIsRead() {
        return isRead;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

}
