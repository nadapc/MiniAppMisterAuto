package nei.nada.miniappmisterauto.network;

import java.util.List;

import nei.nada.miniappmisterauto.model.User;
import nei.nada.miniappmisterauto.model.UserTask;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserDataService {

    @GET("users/")
    Call<List<User>> getUserList();

    @GET("todos")
    Call<List<UserTask>> getUserTaskList(@Query("userId") int userId);

}
