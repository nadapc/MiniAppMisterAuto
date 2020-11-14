package nei.nada.miniappmisterauto.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import nei.nada.miniappmisterauto.model.User;
import nei.nada.miniappmisterauto.model.UserTask;
import nei.nada.miniappmisterauto.repository.UserRepository;

public class UserTasksActivityViewModel extends AndroidViewModel {

    public LiveData<List<UserTask>> userTasks;
    public LiveData<List<UserTask>> userTasksBdd;
    private UserRepository userRepository;
    private int id;
    private SharedPreferences sharedpreferences ;

    public UserTasksActivityViewModel(@NonNull Application application) {
        super(application);
        userRepository= new UserRepository(application);

        //j'utilise le shared pref pour la recup d'un argument additionnel puisq le constructeur androidviewmodel ne le permet pas
        sharedpreferences =application.getApplicationContext().getSharedPreferences("AppPreferences",Context.MODE_PRIVATE);
        id = sharedpreferences.getInt("preference_user_id",0);

        //recupe de la data une seul fois afin  d'eviter l'envoie de plusieur requette lors de changement de la configuration
        userTasks = userRepository.getMutableLiveDataUserTasks(id);
        userTasksBdd = userRepository.getUserTasksFromBDD(id);
    }

    //insertion des tasks
    public void insert(List<UserTask> userTasks){
        userRepository.insertUserTask(userTasks);
    }

    //recup des task from ws
    public LiveData<List<UserTask>> getAllUserTasks(int id){
        userTasks = userRepository.getMutableLiveDataUserTasks(id);
        return userTasks;
    }

    //recup des task from bdd
    public LiveData<List<UserTask>> getUserTasksFromBDD(int id){
        userTasksBdd = userRepository.getUserTasksFromBDD(id);
        return userTasksBdd;
    }
}
