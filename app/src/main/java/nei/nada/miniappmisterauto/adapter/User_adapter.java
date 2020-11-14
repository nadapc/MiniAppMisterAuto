package nei.nada.miniappmisterauto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nei.nada.miniappmisterauto.R;
import nei.nada.miniappmisterauto.databinding.ItemUserBinding;
import nei.nada.miniappmisterauto.model.User;

public class User_adapter extends RecyclerView.Adapter<User_adapter.UserViewHolder>{
    private OnItemClickListener onItemClickListener ;

    private List<User> users;
    private Context context;

    public User_adapter( Context context1) {
        this.context = context1;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding itemUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_user,parent,false);
        return new UserViewHolder(itemUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = users.get(position);
        holder.itemUserBinding.setUser(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder{

        private ItemUserBinding itemUserBinding;

        public UserViewHolder(ItemUserBinding itemUserBinding) {
            super(itemUserBinding.getRoot());
            this.itemUserBinding = itemUserBinding;

            itemUserBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();

                    if(onItemClickListener!=null && clickedPosition!=RecyclerView.NO_POSITION)
                        onItemClickListener.onItemClick(users.get(clickedPosition));
                }
            });
        }
    }

    public void setUserList(List<User> userList){
        this.users=userList;
        notifyDataSetChanged();

    }

    //gestion du click sur l item
    public interface OnItemClickListener{
        void onItemClick(User user);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

}
