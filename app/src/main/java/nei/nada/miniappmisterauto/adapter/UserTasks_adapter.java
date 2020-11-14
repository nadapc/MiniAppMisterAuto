package nei.nada.miniappmisterauto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nei.nada.miniappmisterauto.R;
import nei.nada.miniappmisterauto.databinding.ItemUserBinding;
import nei.nada.miniappmisterauto.databinding.ItemUserTaskBinding;
import nei.nada.miniappmisterauto.model.User;
import nei.nada.miniappmisterauto.model.UserTask;

public class UserTasks_adapter extends RecyclerView.Adapter<UserTasks_adapter.UserTasksViewHolder>{

    private List<UserTask> userTasks;
    private Context context;

    public UserTasks_adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UserTasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserTaskBinding itemUserTaskBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_user_task,parent,false);
        return new UserTasksViewHolder(itemUserTaskBinding);      }

    @Override
    public void onBindViewHolder(@NonNull UserTasksViewHolder holder, int position) {
        UserTask userTask = userTasks.get(position);
        holder.itemUserTaskBinding.setUserTask(userTask);
        //changement d icon et la couleur de text  selon le statu completed
        if(userTask.isCompleted()){
            holder.itemUserTaskBinding.ivTaskCompleted.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_done_on));
            holder.itemUserTaskBinding.tvUsertaskTitle.setTextColor(context.getResources().getColor(R.color.mister_auto_orange));
        }
        else {
            holder.itemUserTaskBinding.ivTaskCompleted.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check_off));
            holder.itemUserTaskBinding.tvUsertaskTitle.setTextColor(context.getResources().getColor(R.color.mister_auto_gris));

        }
    }

    @Override
    public int getItemCount() {
        return userTasks.size();
    }


    class UserTasksViewHolder extends RecyclerView.ViewHolder{

        private ItemUserTaskBinding itemUserTaskBinding;

        public UserTasksViewHolder(ItemUserTaskBinding itemUserTaskBinding) {
            super(itemUserTaskBinding.getRoot());
            this.itemUserTaskBinding = itemUserTaskBinding;

        }
    }

    public void setUserTasksList(List<UserTask> userTaskList){
        this.userTasks=userTaskList;
        notifyDataSetChanged();
    }


}
