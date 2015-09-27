package abassawo.c4q.nyc.ecquo.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import abassawo.c4q.nyc.ecquo.Model.User;
import abassawo.c4q.nyc.ecquo.Model.UserDatabaseHelper;
import abassawo.c4q.nyc.ecquo.R;

public class LauncherActivity extends Activity {

    private static final int SPLASHTIME = 3000;
    private static final int STOPSPLASH = 0;
    private Handler mSplashHandler;

    UserDatabaseHelper userDatabaseHelper;
    UserAsyncTask userAsyncTask;
    List<User> users;
    List<String> names;

    // Handler start
    {
        mSplashHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case STOPSPLASH:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                }
            }
        };
    }// Handler end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        userDatabaseHelper = UserDatabaseHelper.getInstance(this);

        names = new ArrayList<>();
        names.add("Abass");
        names.add("Joshelyn");
        names.add("Hans");
        names.add("user");
        userAsyncTask = new UserAsyncTask();
        userAsyncTask.execute(names);

        // to start the handler
        mSplashHandler.sendEmptyMessageDelayed(STOPSPLASH, SPLASHTIME);

    }

    public class UserAsyncTask extends AsyncTask<List<String>, Void, List<User>> {

        @Override
        protected List<User> doInBackground(List<String>... lists) {
            try {
                userDatabaseHelper.createDefaultUsers(lists[0]);
                users = userDatabaseHelper.getDao(User.class).queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return users;
        }
    }
}