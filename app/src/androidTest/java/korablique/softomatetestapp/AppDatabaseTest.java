package korablique.softomatetestapp;


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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
        List<HistoryEntity> changed = new ArrayList<>();

        CountDownLatch mutex = new CountDownLatch(1);
        historyDao.getAll().observeForever(historyEntities -> {
            changed.addAll(historyEntities);
            mutex.countDown();
        });
        mutex.await();

        Assert.assertEquals(1, changed.size());
        Assert.assertEquals(historyEntity.getText(), changed.get(0).getText());
        Assert.assertEquals(historyEntity.getLanguage(), changed.get(0).getLanguage());
    }
}