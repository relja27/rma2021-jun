package rs.raf.projekatjun.relja_mikanovic_rn6719.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {

    private EventDao eventDao;
    private LiveData<List<Event>> allEvents;

    public EventRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getInstance(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAllEvents();
    }

    public void insert(Event event) {
        new InsertEventAsyncTask(eventDao).execute(event);
    }

    public void delete(Event event) {
        new DeleteEventAsyncTask(eventDao).execute(event);
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public static class InsertEventAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao eventDao;

        private InsertEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {

            eventDao.insert(events[0]);
            return null;
        }
    }

    public static class DeleteEventAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao eventDao;
        private DeleteEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {

            eventDao.delete(events[0]);
            return null;
        }
    }

}
