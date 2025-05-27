package Models;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String emailAddress;
    private String avatarIndex;

    private int score;
    private int kills;
    private int maxSurviveTime;



    public User(String username, String password, String emailAddress) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.score = 0;
        this.avatarIndex = String.valueOf(ThreadLocalRandom.current().nextInt(1, 6));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public String getAvatarIndex() {
        return avatarIndex;
    }

    public void setAvatarIndex(String avatarIndex) {
        this.avatarIndex = avatarIndex;
    }

    public int getKills() {
        return kills;
    }

    public void increaseKills(int kills) {
        this.kills += kills;
    }

    public int getMaxSurviveTime() {
        return maxSurviveTime;
    }

    public void setMaxSurviveTime(int maxSurviveTime) {
        this.maxSurviveTime = Math.max(maxSurviveTime, this.maxSurviveTime);
    }


}
