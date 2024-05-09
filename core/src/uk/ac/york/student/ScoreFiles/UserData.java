package uk.ac.york.student.ScoreFiles;

public class UserData {
    private String username;
    private float score;
    private String degree;

    public UserData(String username, float score, String degree) {
        this.username = username;
        this.score = score;
        this.degree = degree;
    }

    public String getUsername() {
        return username;
    }

    public float getScore() {
        return score;
    }

    public String getDegree() {
        return degree;
    }
}