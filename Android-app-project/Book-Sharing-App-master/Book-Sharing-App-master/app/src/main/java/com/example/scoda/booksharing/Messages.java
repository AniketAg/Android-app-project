package com.example.scoda.booksharing;

/**
 * Created by scoda on 11/25/2016.
 */
public class Messages {

    String fromUser;
    String toUser;
    String message;
    String time;

    public Messages() {}

    public Messages(String fromUser,String toUser,String message,String time) {

        this.fromUser = fromUser;
        this.toUser = toUser;
        this.message = message;
        this.time = time;
    }

    public String getfromUser() {
        return fromUser;
    }

    public void setfromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String gettoUser() {
        return toUser;
    }

    public void settoUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
