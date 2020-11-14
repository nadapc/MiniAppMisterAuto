package nei.nada.miniappmisterauto.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserTaskDao {

    @Insert
    void insertUserTask(UserTask userTask);

    @Insert
    void insertUserTaskList(List<UserTask> userTasklist);

    @Delete
    void deleteUserTask(UserTask userTask);

    @Query("DELETE FROM userTaskTable WHERE userId == :id")
    public void deleteAllUsersTask(int id);

    @Query("SELECT * FROM userTaskTable WHERE userId == :id")
    LiveData<List<UserTask>> getAllUserTaskByID(int id);

}
