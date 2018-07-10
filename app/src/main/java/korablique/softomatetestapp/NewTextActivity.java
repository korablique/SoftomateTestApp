package korablique.softomatetestapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewTextActivity extends BaseActivity {
    public static final String SHOW_LANGUAGE = "SHOW_LANGUAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_text);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            IdentificationLangDialog dialog = new IdentificationLangDialog();
            dialog.show(getSupportFragmentManager(), SHOW_LANGUAGE);
        });
    }
}
