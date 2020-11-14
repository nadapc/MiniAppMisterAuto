package nei.nada.miniappmisterauto.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import nei.nada.miniappmisterauto.BR;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "userTaskTable",foreignKeys = @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "userId",onDelete = CASCADE))
public class UserTask extends BaseObservable {

    @SerializedName("userId")
    private int userId;

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("completed")
    private boolean completed;

    @Ignore
    public UserTask() {
    }

    @Ignore
    public UserTask(int userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public UserTask(int userId, int id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    @Bindable
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);

    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);

    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);

    }

    @Bindable
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        notifyPropertyChanged(BR.completed);

    }
}
