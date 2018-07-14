package korablique.softomatetestapp;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import korablique.softomatetestapp.database.AppDatabase;
import korablique.softomatetestapp.database.HistoryDao;
import korablique.softomatetestapp.database.HistoryEntity;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {
    private HistoryDao historyDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        historyDao = db.historyDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeHistoryEntityAndReadInList() throws Exception {
        String text = "some text";
        String language = "English";
        HistoryEntity historyEntity = new HistoryEntity(text, language);
        historyDao.insert(historyEntity);
        List<HistoryEntity> historyEntityList = historyDao.getAll().getValue();

        Assert.assertEquals(historyEntityList.size(), 1);
        Assert.assertEquals(historyEntityList.get(0).getText(), historyEntity.getText());
        Assert.assertEquals(historyEntityList.get(0).getLanguage(), historyEntity.getLanguage());
    }
}