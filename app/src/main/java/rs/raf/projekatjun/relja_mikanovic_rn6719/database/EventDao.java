package rs.raf.projekatjun.relja_mikanovic_rn6719.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {

    @Insert
    void insert(Event event);

    @Delete
    void delete(Event note);

    @Query("SELECT * FROM event_table")
    public LiveData<List<Event>> getAllEvents();


}
