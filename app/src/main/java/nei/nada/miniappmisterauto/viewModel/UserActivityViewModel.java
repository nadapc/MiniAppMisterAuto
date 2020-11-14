package nei.nada.miniappmisterauto.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import nei.nada.miniappmisterauto.model.User;
import nei.nada.miniappmisterauto.repository.UserRepository;

public class UserActivityViewModel extends AndroidViewModel {

    public LiveData<List<User>> users; //liste des user depuis le webservice
    public LiveData<List<User>> usersBdd; // liste des users depuis la bdd
    private UserRepository userRepository;

    public UserActivityViewModel(@NonNull Application application) {
        super(application);
        userRepository= new UserRepository(application);
        //recuperation des liste user pour eviter d evnoyer de nouvelle reqyuette a chaque changement d orientation
        users = userRepository.getMutableLiveDataUsers();
        usersBdd = userRepository.getUsersFromBDD();
    }

    //insertion de la liste dans la bdd
    public void insert(List<User> users){
        userRepository.insertUsers(users);
    }

    //recup users from ws
    public LiveData<List<User>> getAllUsers(){
        users = userRepository.getMutableLiveDataUsers();
        return users;
    }

    //recup users from bdd
    public LiveData<List<User>> getUsersFromBDD(){
        usersBdd = userRepository.getUsersFromBDD();
        return usersBdd;
    }

}
