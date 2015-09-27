package abassawo.c4q.nyc.ecquo.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by c4q-Abass on 8/18/15.
 */

@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private int points;

    @DatabaseField
    private int likes;

    @DatabaseField
    private int dislikes;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = this.points + points;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = this.likes + likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = this.dislikes + dislikes;
    }
}
