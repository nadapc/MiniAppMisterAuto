package nei.nada.miniappmisterauto.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class,UserTask.class},version =3)
public abstract class UserDatabase extends RoomDatabase{

    public abstract UserDao userDao();
    public abstract UserTaskDao userTaskDao();
    public static UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,"UsersDatabase")
                    .fallbackToDestructiveMigration()// to recreate database table when we change database version
                    .addCallback(callback)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new initialDataAsyncktask(instance).execute();
        }
    };

    private static class initialDataAsyncktask extends AsyncTask<Void,Void,Void> {

        private UserDao userDao;
        private UserTaskDao userTaskDao;

        public initialDataAsyncktask(UserDatabase userDatabase){

            userDao = userDatabase.userDao();
            userTaskDao = userDatabase.userTaskDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

}
