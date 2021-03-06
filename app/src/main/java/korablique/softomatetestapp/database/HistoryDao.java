package korablique.softomatetestapp.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM HistoryEntity")
    LiveData<List<HistoryEntity>> getAll();

    @Insert
    void insert(HistoryEntity entry);
}