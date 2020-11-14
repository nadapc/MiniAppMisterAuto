package nei.nada.miniappmisterauto.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nei.nada.miniappmisterauto.R;
import nei.nada.miniappmisterauto.adapter.UserTasks_adapter;
import nei.nada.miniappmisterauto.databinding.Activity02UserTasksBinding;
import nei.nada.miniappmisterauto.model.UserTask;
import nei.nada.miniappmisterauto.utils.Utils;
import nei.nada.miniappmisterauto.viewModel.UserTasksActivityViewModel;

public class Activity_02_UserTasks extends AppCompatActivity {

    private String TAG = "Activity_02_UserTasks";
    private UserTasksActivityViewModel userTasksActivityViewModel;
    private Activity02UserTasksBinding activity02UserTasksBinding;
    private List<UserTask> listTasks;
    private RecyclerView rv_userTasks;
    private UserTasks_adapter adapter;

    private int iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //recup de l'id user
        Intent i = getIntent();
        if(i.hasExtra("iduser")) iduser = i.getIntExtra("iduser",0);
        else iduser = 0;

        //enregistrement de l'id pour l'utiliser dans androidviewmodel de l'activite pour ne pas envoye une requette au service a chaque changement de l'orientation
        SharedPreferences sharedPref = this.getSharedPreferences("AppPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("preference_user_id", iduser);
        editor.commit();

        //Gestion du databindig et viewmodel de l activity
        activity02UserTasksBinding = DataBindingUtil.setContentView(this,R.layout.activity_02_user_tasks);
        userTasksActivityViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(UserTasksActivityViewModel.class);

        //Gestion du RecyclerView
        listTasks= new ArrayList<>();
        rv_userTasks = activity02UserTasksBinding.rvUserTasks;
        adapter = new UserTasks_adapter(Activity_02_UserTasks.this);
        adapter.setUserTasksList(listTasks);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(Activity_02_UserTasks.this);
        rv_userTasks.setLayoutManager(layoutManager);
        rv_userTasks.setItemAnimator(new DefaultItemAnimator());
        rv_userTasks.setAdapter(adapter);

        if(iduser!=0){
            //recuperer les tasks d'user
            getUserTasks(iduser);

        } else {

            //gestion de cas ou l'id user n'est pas disponible
            activity02UserTasksBinding.tvListVide.setVisibility(View.VISIBLE);
            activity02UserTasksBinding.tvListVide.setText(getResources().getString(R.string.liste_user_tasks_ko));
            activity02UserTasksBinding.rvUserTasks.setVisibility(View.GONE);

        }

    }

    private void getUserTasks(int iduser){

        //gestion du progress lors de la recup data
        activity02UserTasksBinding.progressBar.setVisibility(View.VISIBLE);
        activity02UserTasksBinding.rvUserTasks.setVisibility(View.GONE);

        if(!Utils.IsNetworkConnected(this)){

            Toast.makeText(Activity_02_UserTasks.this,getResources().getString(R.string.Erreur_connexion_internet),Toast.LENGTH_LONG).show();

            //si la connexion n'est pas activé recuperer la data depuis la bdd room
            userTasksActivityViewModel.userTasksBdd.observe(this, new Observer<List<UserTask>>() {
                @Override
                public void onChanged(List<UserTask> userTasks) {

                    activity02UserTasksBinding.progressBar.setVisibility(View.GONE);

                    listTasks = userTasks;
                    if(listTasks.size()==0) {
                        activity02UserTasksBinding.tvListVide.setVisibility(View.VISIBLE);
                        activity02UserTasksBinding.rvUserTasks.setVisibility(View.GONE);
                    } else {
                        activity02UserTasksBinding.tvListVide.setVisibility(View.GONE);
                        activity02UserTasksBinding.rvUserTasks.setVisibility(View.VISIBLE);
                        adapter.setUserTasksList(listTasks);

                    }

                }
            });
        } else {
            //si la connexion est activé recuperer la data depuis le web service

            userTasksActivityViewModel.userTasks.observe(this, new Observer<List<UserTask>>() {
                @Override
                public void onChanged(List<UserTask> userTasks) {

                    if(userTasks.size()!=0){
                        activity02UserTasksBinding.progressBar.setVisibility(View.GONE);
                        activity02UserTasksBinding.rvUserTasks.setVisibility(View.VISIBLE);

                        listTasks = userTasks;
                        adapter.setUserTasksList(listTasks);
                        //insertion des task dans la bdd apres la recup pour la consultation horsligne
                        userTasksActivityViewModel.insert(listTasks);
                    }else {
                        activity02UserTasksBinding.progressBar.setVisibility(View.GONE);
                        activity02UserTasksBinding.rvUserTasks.setVisibility(View.GONE);
                        activity02UserTasksBinding.tvListVide.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

    }

}