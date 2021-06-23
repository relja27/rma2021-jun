package rs.raf.projekatjun.relja_mikanovic_rn6719.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rs.raf.projekatjun.relja_mikanovic_rn6719.R;
import rs.raf.projekatjun.relja_mikanovic_rn6719.database.Event;
import rs.raf.projekatjun.relja_mikanovic_rn6719.recycler.adapter.EventAdapter;
import rs.raf.projekatjun.relja_mikanovic_rn6719.recycler.viewModel.RecyclerViewModel;

public class ShowEventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textEvents;
    final EventAdapter adapter = new EventAdapter();
    private RecyclerViewModel recyclerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);

        RecyclerView recyclerView = findViewById(R.id.listRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerViewModel = ViewModelProviders.of(this).get(RecyclerViewModel.class);
        recyclerViewModel.getEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {

                adapter.submitList(events);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        initView();
    }


    private void initView() {
        textEvents = findViewById(R.id.textEvents);
        recyclerView = findViewById(R.id.listRv);

    }




}