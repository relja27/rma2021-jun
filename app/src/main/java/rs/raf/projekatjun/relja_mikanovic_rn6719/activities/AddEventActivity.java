package rs.raf.projekatjun.relja_mikanovic_rn6719.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import rs.raf.projekatjun.relja_mikanovic_rn6719.R;
import rs.raf.projekatjun.relja_mikanovic_rn6719.retrofit.WorldClockService;


public class AddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE = "EXTRA_DATE";
    public static final String EXTRA_TIME = "EXTRA_TIME";
    public static final String EXTRA_PRIORITY = "EXTRA_PRIORITY";
    public static final String EXTRA_URL = "EXTRA_URL";

    private Button btnTimeForLocation;
    private Button btnSetDate;
    private Button btnSetTime;
    private Button btnSave;
    private EditText textName;
    private EditText textDescription;
    private AutoCompleteTextView autoCompleteTextViewLocation;
    private EditText textURL;
    private Spinner spinner;
    final WorldClockService worldClockService = new WorldClockService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        initView();
        initListeners();
    }

    private void initListeners() {
        btnSetDate.setOnClickListener(v -> {
                showDatePickerDialog();
        });

        btnSetTime.setOnClickListener(v -> {
            showTimePickerDialog();
        });

        btnTimeForLocation.setOnClickListener(v -> {
            String city = autoCompleteTextViewLocation.getText().toString();
            if(city.isEmpty() == false){
                worldClockService.invokeCityService(city);
                worldClockService.getEasternStandardTime().observe(
                        this, time-> {
                            btnTimeForLocation.setText(time.getDatetime());
                        }
                );
            }
        });

       btnSave.setOnClickListener(v ->{
           saveEvent();
       });
    }

    private void saveEvent() {
        String title = textName.getText().toString();
        String description = textDescription.getText().toString();
        String date = btnSetDate.getText().toString();
        String time = btnSetTime.getText().toString();
        String priority = spinner.getSelectedItem().toString();
        String url = textURL.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()
            || date.equals("SET DATE") || time.equals("SET TIME") || url.trim().isEmpty()) {
            Toast.makeText(this, "Please insert all data", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_TIME, time);
        data.putExtra(EXTRA_PRIORITY, priority);
        data.putExtra(EXTRA_URL, url);

        setResult(RESULT_OK, data);

        finish();
    }


    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );
timePickerDialog.show();
    }

    private void initView() {
        btnTimeForLocation = findViewById(R.id.buttonTimeForLocation);
        btnSetDate = findViewById(R.id.buttonSetDate);
        btnSetTime = findViewById(R.id.buttonSetTime);
        btnSave = findViewById(R.id.buttonSave);
        textName = findViewById(R.id.textEventName);
        textDescription = findViewById(R.id.textDescription);

        autoCompleteTextViewLocation = findViewById(R.id.autoCompleteLocation);
        String[] cities = getResources().getStringArray(R.array.cities);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        autoCompleteTextViewLocation.setAdapter(adapter);

        textURL = findViewById(R.id.textURL);
        spinner = findViewById(R.id.spinner);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "" + dayOfMonth + '/' + (month+1) + '/' + year;
        btnSetDate.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if(hourOfDay < 10) {
            if (minute < 10) {
               String time = String.valueOf(0) + hourOfDay + ":" + 0 + minute;
                btnSetTime.setText(time);
            } else {
                String time = String.valueOf(0) + hourOfDay + ":" + minute;
                btnSetTime.setText(time);
            }
        } else if (minute < 10){
            String time = hourOfDay + ":" + 0 + minute;
            btnSetTime.setText(time);
        } else {
            String time = hourOfDay + ":" + minute;
            btnSetTime.setText(time);
        }
    }


}