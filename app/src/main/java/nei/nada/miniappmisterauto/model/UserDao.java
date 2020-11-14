package nei.nada.miniappmisterauto.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Insert
    void insertUserList(List<User> userlist);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM userTable")
    public void deleteAllUsers();

    @Query("SELECT * FROM userTable")
    LiveData<List<User>> getAllUsers();
}
