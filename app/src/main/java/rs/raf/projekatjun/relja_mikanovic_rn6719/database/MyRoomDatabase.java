package rs.raf.projekatjun.relja_mikanovic_rn6719.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(version = 2,
        entities = {
                Event.class,
        },
        exportSchema = false
)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static MyRoomDatabase instance;

    public static synchronized MyRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyRoomDatabase.class, "event_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract EventDao eventDao();

    private static RoomDatabase.Callback roomCallback
            = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private EventDao eventDao;

        private PopulateDbAsyncTask(MyRoomDatabase db) {
            eventDao = db.eventDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            eventDao.insert(new Event("Event 2", "Description 1",
                    "11/1/2021", "11:51", "www.zoom.us/ev1", "Low ⚪"));
            eventDao.insert(new Event("Event 2", "Description 1",
                    "11/1/2021", "11:51", "www.zoom.us/ev1", "High \uD83D\uDD34"));
            eventDao.insert(new Event("Event 2", "Description 1",
                    "11/1/2021", "11:51", "www.zoom.us/ev1", "Low ⚪"));
            eventDao.insert(new Event("Event 2", "Description 1",
                    "11/1/2021", "11:51", "www.zoom.us/ev1", "Medium \uD83D\uDFE2"));
            eventDao.insert(new Event("Event 2", "Description 1",
                    "11/1/2021", "11:51", "www.zoom.us/ev1", "High \uD83D\uDD34"));

            return null;
        }
    }

}
