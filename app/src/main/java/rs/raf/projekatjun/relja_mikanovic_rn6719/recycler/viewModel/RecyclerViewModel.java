package rs.raf.projekatjun.relja_mikanovic_rn6719.recycler.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import rs.raf.projekatjun.relja_mikanovic_rn6719.database.Event;
import rs.raf.projekatjun.relja_mikanovic_rn6719.database.EventRepository;

public class RecyclerViewModel extends AndroidViewModel {

    private EventRepository eventRepository;
    private LiveData<List<Event>> allEvents;

    public RecyclerViewModel(@NonNull Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        allEvents = eventRepository.getAllEvents();
    }

    public void insert (Event event) {
        eventRepository.insert(event);
    }

    public void delete (Event event) {
        eventRepository.delete(event);
    }

    public LiveData<List<Event>> getEvents() {
      return allEvents;
    }
}
