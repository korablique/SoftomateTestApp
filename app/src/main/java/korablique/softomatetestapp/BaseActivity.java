package korablique.softomatetestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import korablique.softomatetestapp.history.HistoryActivity;
import korablique.softomatetestapp.new_text.NewTextActivity;


public abstract class BaseActivity extends AppCompatActivity {
    private Drawer drawer;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.drawer_header_layout)
                .withSavedInstance(savedInstanceState)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withToolbar(toolbar)
                .withSelectedItem(-1)
                .withOnDrawerNavigationListener(clickedView -> {
                    BaseActivity.this.finish();
                    return true;
                });

        IDrawerItem itemPrimary1 = new PrimaryDrawerItem()
                .withName(R.string.new_text)
                .withSelectable(false)
                .withIcon(R.drawable.ic_add)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    Intent intent = new Intent(BaseActivity.this, NewTextActivity.class);
                    BaseActivity.this.startActivity(intent);
                    return true;
                });
        IDrawerItem itemPrimary2 = new PrimaryDrawerItem()
                .withName(R.string.history)
                .withSelectable(false)
                .withIcon(R.drawable.ic_history)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    Intent intent = new Intent(BaseActivity.this, HistoryActivity.class);
                    BaseActivity.this.startActivity(intent);
                    return true;
                });

        List<IDrawerItem> drawerItems = new ArrayList<>();
        drawerItems.add(itemPrimary1);
        drawerItems.add(itemPrimary2);
        drawerBuilder.withDrawerItems(drawerItems);
        drawer = drawerBuilder.build();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = drawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
