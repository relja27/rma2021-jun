package rs.raf.projekatjun.relja_mikanovic_rn6719.recycler.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import rs.raf.projekatjun.relja_mikanovic_rn6719.R;
import rs.raf.projekatjun.relja_mikanovic_rn6719.database.Event;
import rs.raf.projekatjun.relja_mikanovic_rn6719.recycler.viewModel.RecyclerViewModel;

public class EventAdapter extends ListAdapter<Event, EventAdapter.EventHolder> {
    private RecyclerViewModel recyclerViewModel;




    private OnItemClickListener listener;

    public EventAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Event> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Event>() {

                @Override
                public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                    return oldItem.getName().equals(newItem.getName()) &&
                            oldItem.getDescription().equals(newItem.getDescription()) &&
                            oldItem.getDate().equals(newItem.getDate()) &&
                            oldItem.getTime().equals(newItem.getTime()) &&
                            oldItem.getPriority().equals(newItem.getPriority()) &&
                            oldItem.getUrl().equals(newItem.getUrl());
                }
            };

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_list_item, viewGroup, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder viewHolder, int position) {
        Event currentEvent = getItem(position);
        viewHolder.textViewName.setText(currentEvent.getName());
        viewHolder.textViewDescription.setText(currentEvent.getDescription());
        viewHolder.textViewDateAndTime.setText(currentEvent.getDate() + " @ " + currentEvent.getTime());
        viewHolder.textViewUrl.setText(currentEvent.getUrl());

        if (currentEvent.getPriority().equals("High \uD83D\uDD34")){                                         // High
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#CCDA1919"));
        } else if (currentEvent.getPriority().equals("Medium \uD83D\uDFE2")){                                // Medium
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#CC03B200"));
        } else {                                                                                             // Low
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#CCFFFFFF"));
        }

        viewHolder.btnShare.setOnClickListener(v -> {

            String invite = currentEvent.getName() + '\n' + currentEvent.getDescription() + '\n' +
                    currentEvent.getDate() + " @ " + currentEvent.getTime() + '\n' + currentEvent.getUrl();

            Context context = v.getContext();

            final Intent intent = new Intent(Intent.ACTION_SEND);

            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share Event");

            intent.putExtra(Intent.EXTRA_TEXT, invite);

            context.startActivity(Intent.createChooser(intent, "Share Event"));
        });

        viewHolder.btnDelete.setOnClickListener(v -> {
            recyclerViewModel = ViewModelProviders.of((FragmentActivity) v.getContext()).get(RecyclerViewModel.class);
            recyclerViewModel.delete(currentEvent);
            Toast.makeText(v.getContext(), "Event " + currentEvent.getName() + " deleted", Toast.LENGTH_SHORT).show();
        });


    }

    public Event getEventAt(int position) {
        return getItem(position);
    }

    class EventHolder extends RecyclerView.ViewHolder {



        private TextView textViewName;
        private TextView textViewDescription;
        private TextView textViewDateAndTime;
        private int position;
        private TextView textViewUrl;
        private Button btnDelete;
        private Button btnShare;


        private EventHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDateAndTime = itemView.findViewById(R.id.textViewDateAndTime);
            textViewUrl = itemView.findViewById(R.id.textViewUrl);
            btnDelete = itemView.findViewById(R.id.buttonDelete);
            btnShare = itemView.findViewById(R.id.buttonShare);
        }



    }

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
