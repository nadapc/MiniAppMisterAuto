package nei.nada.miniappmisterauto.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import nei.nada.miniappmisterauto.model.User;
import nei.nada.miniappmisterauto.model.UserDao;
import nei.nada.miniappmisterauto.model.UserDatabase;
import nei.nada.miniappmisterauto.model.UserTask;
import nei.nada.miniappmisterauto.model.UserTaskDao;
import nei.nada.miniappmisterauto.network.RetrofitInstance;
import nei.nada.miniappmisterauto.network.UserDataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private Context context;
    private UserDatabase userDatabase;

    private UserDao userDao;
    private UserTaskDao userTaskDao;

    private MutableLiveData<List<User>> mutableLiveDataUsers = new MutableLiveData<>();
    private MutableLiveData<List<UserTask>> mutableLiveDataUserTasks = new MutableLiveData<>();

    public UserRepository(Application application) {
        userDatabase = UserDatabase.getInstance(application);
        context = application.getApplicationContext();
        userDao = userDatabase.userDao();
        userTaskDao = userDatabase.userTaskDao();
    }

    //Recup data a l aide de retrofit
    //users
    public MutableLiveData<List<User>> getMutableLiveDataUsers() {

        UserDataService userDataService = RetrofitInstance.getService(context);
        Call<List<User>> call = userDataService.getUserList();
        call.enqueue(new Callback<List<User>>() {
           @Override
           public void onResponse(Call<List<User>> call, Response<List<User>> response) {

               List<User> list = response.body();
               if(list!=null && list.size()!=0) {
                   mutableLiveDataUsers.setValue(list);
               }
           }

           @Override
           public void onFailure(Call<List<User>> call, Throwable t) {
               Log.d("TAG", "onFailure: failed to fetch data list from server");
               List<User> list = new ArrayList<>();
               mutableLiveDataUsers.setValue(list);


           }
       });

        return mutableLiveDataUsers;
    }

    //userTasks
    public MutableLiveData<List<UserTask>> getMutableLiveDataUserTasks(int idUser) {

        UserDataService userDataService = RetrofitInstance.getService(context);
        Call<List<UserTask>> call = userDataService.getUserTaskList(idUser);
        call.enqueue(new Callback<List<UserTask>>() {
            @Override
            public void onResponse(Call<List<UserTask>> call, Response<List<UserTask>> response) {
                List<UserTask> list = response.body();
                if(list!=null && list.size()!=0) {
                    mutableLiveDataUserTasks.setValue(list);
                }
            }

            @Override
            public void onFailure(Call<List<UserTask>> call, Throwable t) {
                Log.d("TAG", "onFailure: failed to fetch data list from server");


            }
        });

        return mutableLiveDataUserTasks;
    }

    //recup users depuis la bdd
    public LiveData<List<User>> getUsersFromBDD(){
        return userDao.getAllUsers();

    }

    //recup user tasks depuis la bdd
    public LiveData<List<UserTask>> getUserTasksFromBDD(int idUser){
        return userTaskDao.getAllUserTaskByID(idUser);

    }

    //Insert data users
    public void insertUsers(List<User> userList){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.deleteAllUsers();
                userDao.insertUserList(userList);
            }
        });
    }

    //insertion data userTasks
    public void insertUserTask(List<UserTask> userTaskList){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userTaskDao.deleteAllUsersTask(userTaskList.get(0).getUserId());
                userTaskDao.insertUserTaskList(userTaskList);
            }
        });
    }

}
