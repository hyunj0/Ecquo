package abassawo.c4q.nyc.ecquo.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class UserDatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    public static final int DATABASE_VERSION = 1;

    private static UserDatabaseHelper INSTANCE;

    Context context;

    public static synchronized UserDatabaseHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserDatabaseHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDefaultUsers(List<String> names) throws SQLException {
        Dao<User, ?> userDao = getDao(User.class);
        userDao.delete(userDao.deleteBuilder().prepare());
        for (String name : names) {
            User user = new User();
            user.setName(name);
            user.setPoints(100);
            user.setLikes(0);
            user.setDislikes(0);
            userDao.create(user);
        }
    }

    public void createUser(String name) throws SQLException {
        Dao<User, ?> userDao = getDao(User.class);
        userDao.delete(userDao.deleteBuilder().prepare());
        User user = new User();
        user.setName(name);
        user.setPoints(100);
        user.setLikes(0);
        user.setDislikes(0);
        userDao.create(user);
    }

    public User readUser(String name) throws SQLException {
        Dao<User, ?> userDao = getDao(User.class);
        return userDao.query(userDao.queryBuilder().where().eq("name", name).prepare()).get(0);
    }

    public void deleteUser(String name) throws SQLException {
        Dao<User, ?> userDao = getDao(User.class);
        DeleteBuilder<User, ?> deleteBuilder = userDao.deleteBuilder();
        deleteBuilder.where().eq("name", name);
        deleteBuilder.delete();
    }

}
