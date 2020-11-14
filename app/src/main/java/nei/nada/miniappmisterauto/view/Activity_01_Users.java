package nei.nada.miniappmisterauto.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nei.nada.miniappmisterauto.R;
import nei.nada.miniappmisterauto.adapter.User_adapter;
import nei.nada.miniappmisterauto.databinding.Activity01UsersBinding;
import nei.nada.miniappmisterauto.model.User;
import nei.nada.miniappmisterauto.utils.Utils;
import nei.nada.miniappmisterauto.viewModel.UserActivityViewModel;
import okhttp3.internal.Util;

public class Activity_01_Users extends AppCompatActivity {

    private String TAG = "UsersActivity";

    private UserActivityViewModel userActivityViewModel;
    private Activity01UsersBinding activity01UsersBinding;
    private List<User> listUsers;
    private RecyclerView rv_users;
    private User_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gestion du databindig et viewmodel de l'activity
        activity01UsersBinding = DataBindingUtil.setContentView(this,R.layout.activity_01_users);
        userActivityViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(UserActivityViewModel.class);

        //Gestion du RecyclerView
        listUsers= new ArrayList<>();
        rv_users = activity01UsersBinding.rvUsers;
        adapter = new User_adapter(Activity_01_Users.this);
        adapter.setUserList(listUsers);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(Activity_01_Users.this);
        rv_users.setLayoutManager(layoutManager);
        rv_users.setItemAnimator(new DefaultItemAnimator());
        rv_users.setAdapter(adapter);

        //gestion de details des task de l'user selectionné
        adapter.setOnItemClickListener(new User_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {

                Intent i = new Intent(Activity_01_Users.this,Activity_02_UserTasks.class);
                i.putExtra("iduser",user.getId());
                startActivity(i);
            }
        });

        //Recuperation de la liste des users
        getUserList();
    }

    private void getUserList(){

        //gestion du progress lors de la recuperation de la data
        activity01UsersBinding.progressBar.setVisibility(View.VISIBLE);
        activity01UsersBinding.rvUsers.setVisibility(View.GONE);

        if(!Utils.IsNetworkConnected(this)){

            Toast.makeText(Activity_01_Users.this,getResources().getString(R.string.Erreur_connexion_internet),Toast.LENGTH_LONG).show();

            //si la connexion n'est pas activé recuperer la data depuis la bdd room
            userActivityViewModel.usersBdd.observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(List<User> users) {

                    activity01UsersBinding.progressBar.setVisibility(View.GONE);

                    listUsers = users;
                    if(users.size()==0) {
                        activity01UsersBinding.tvListVide.setVisibility(View.VISIBLE);
                        activity01UsersBinding.rvUsers.setVisibility(View.GONE);
                    } else {
                        activity01UsersBinding.tvListVide.setVisibility(View.GONE);
                        activity01UsersBinding.rvUsers.setVisibility(View.VISIBLE);
                        adapter.setUserList(users);
                    }

                }
            });
        }else {

            //si la connexion est activé recuperer la data depuis le web service
            userActivityViewModel.users.observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(List<User> users) {

                    if(users.size()!=0){
                        activity01UsersBinding.progressBar.setVisibility(View.GONE);
                        activity01UsersBinding.rvUsers.setVisibility(View.VISIBLE);

                        listUsers = users;
                        adapter.setUserList(listUsers);
                        //insertion de la liste recuperer dans la bdd
                        userActivityViewModel.insert(listUsers);
                    }else {

                        activity01UsersBinding.progressBar.setVisibility(View.GONE);
                        activity01UsersBinding.rvUsers.setVisibility(View.GONE);
                        activity01UsersBinding.tvListVide.setVisibility(View.VISIBLE);

                    }

                }
            });
        }

    }

}