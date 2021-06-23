package rs.raf.projekatjun.relja_mikanovic_rn6719.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import rs.raf.projekatjun.relja_mikanovic_rn6719.R;
import rs.raf.projekatjun.relja_mikanovic_rn6719.database.Event;
import rs.raf.projekatjun.relja_mikanovic_rn6719.recycler.viewModel.RecyclerViewModel;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    private Button btnAdd;
    private Button btnShow;
    private RecyclerViewModel recyclerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewModel = ViewModelProviders.of(this).get(RecyclerViewModel.class);
        init();
    }

    private void init() {
        initView();
        initListeners();
    }

    private void initView() {
        btnAdd = findViewById(R.id.buttonAdd);
        btnShow = findViewById(R.id.buttonShow);
    }

    private void initListeners() {
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        });

        btnShow.setOnClickListener(v -> {
            Intent i = new Intent(this, ShowEventsActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEventActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEventActivity.EXTRA_DESCRIPTION);
            String date = data.getStringExtra(AddEventActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddEventActivity.EXTRA_TIME);
            String priority = data.getStringExtra(AddEventActivity.EXTRA_PRIORITY);
            String url = data.getStringExtra(AddEventActivity.EXTRA_URL);
            Event event = new Event(title, description, date, time, url, priority);

            recyclerViewModel.insert(event);

            Toast.makeText(this, "Event " + title + " successfully added!", Toast.LENGTH_SHORT).show();
        }
    }
}