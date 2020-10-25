package com.duytv.i_rate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final Context context = this;
    final Calendar myCalendar = Calendar.getInstance();

    private RatingBar foodQualityRating;
    private RatingBar cleanlinessRating;
    private RatingBar serviceRating;
    private EditText edt_reporter;
    private EditText edt_restaurant_name;
    private EditText edt_restaurant_type;
    private EditText edt_time_visit;
    private EditText edt_price;
    private EditText edt_notes;
    private Button btn_addFeedback;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initRatingBar();

        this.btn_addFeedback = findViewById(R.id.add_feedback);
        btn_addFeedback.setOnClickListener(this);

        this.edt_time_visit = findViewById(R.id.time_visit);
        edt_time_visit.setOnClickListener(this);

        this.edt_reporter = findViewById(R.id.reporter);
        this.edt_restaurant_name = findViewById(R.id.restaurant_Name);
        this.edt_restaurant_type = findViewById(R.id.restaurant_type);
        this.edt_price = findViewById(R.id.price);
        this.edt_notes = findViewById(R.id.notes);


    }

    private void initRatingBar(){
        this.foodQualityRating = findViewById(R.id.food_quality_rating);
        this.cleanlinessRating = findViewById(R.id.cleanliness_rating);
        this.serviceRating = findViewById(R.id.service_rating);
    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        this.edt_time_visit.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onClick(View v) {
        int food_quality_rating = (int) this.foodQualityRating.getRating();
        int cleanliness_rating = (int) this.cleanlinessRating.getRating();
        int service_rating = (int) this.serviceRating.getRating();

        String edt_reporter= this.edt_reporter.getText().toString();
        String edt_restaurant_name = this.edt_restaurant_name.getText().toString();
        String edt_restaurant_type = this.edt_restaurant_type.getText().toString();
        String edt_time_visit = this.edt_time_visit.getText().toString();
        String edt_price = this.edt_price.getText().toString();
        String edt_notes = this.edt_notes.getText().toString();
        switch (v.getId()){

            case R.id.add_feedback:
                if (edt_reporter.isEmpty() || edt_restaurant_name.isEmpty() || edt_restaurant_type.isEmpty() || edt_time_visit.isEmpty() || edt_price.isEmpty()){
                    customDialog(edt_reporter, edt_restaurant_name, edt_restaurant_type, edt_time_visit, edt_price);
                }else {
                    showFeedback(edt_reporter, edt_restaurant_name,edt_restaurant_type, edt_time_visit, edt_price,edt_notes, food_quality_rating, cleanliness_rating, service_rating);
                }
                break;
            case R.id.time_visit:
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

        }

    }

    private void customDialog(String edt_reporter, String edt_restaurant_name, String edt_restaurant_type, String edt_time_visit, String edt_price){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);


        TextView dialog_reporter = (TextView) dialog.findViewById(R.id.dialog_reporter);
        TextView dialog_restaurant_name = (TextView) dialog.findViewById(R.id.dialog_restaurant_name);
        TextView dialog_restaurant_type = (TextView) dialog.findViewById(R.id.dialog_restaurant_type);
        TextView dialog_time_visit = (TextView) dialog.findViewById(R.id.dialog_time_visit);
        TextView dialog_price = (TextView) dialog.findViewById(R.id.dialog_price);

        if(edt_reporter.isEmpty()) dialog_reporter.setText("Reporter name");
        if(edt_restaurant_name.isEmpty() ) dialog_restaurant_name.setText("Restaurant name");
        if(edt_restaurant_type.isEmpty() ) dialog_restaurant_type.setText("Restaurant type");
        if(edt_time_visit.isEmpty() ) dialog_time_visit.setText("Date and time of the visit");
        if(edt_price.isEmpty() ) dialog_price.setText("Price");

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void showFeedback(String edt_reporter, String edt_restaurant_name, String edt_restaurant_type, String edt_time_visit, String edt_price,String edt_notes, int food_quality_rating, int cleanliness_rating, int service_rating){

        final Dialog feedback = new Dialog(context);
        feedback.setContentView(R.layout.your_feedback);


        TextView feedback_reporter = (TextView) feedback.findViewById(R.id.feedback_reporter);
        TextView feedback_restaurant_name = (TextView) feedback.findViewById(R.id.feedback_restaurant_name);
        TextView feedback_restaurant_type = (TextView) feedback.findViewById(R.id.feedback_restaurant_type);
        TextView feedback_time_visit = (TextView) feedback.findViewById(R.id.feedback_time_visit);
        TextView feedback_price = (TextView) feedback.findViewById(R.id.feedback_price);
        TextView feedback_notes = (TextView) feedback.findViewById(R.id.feedback_notes);
        RatingBar feedback_food_quality_rating = (RatingBar) feedback.findViewById(R.id.feedback_food_quality_rating);
        RatingBar feedback_cleanliness_rating = (RatingBar) feedback.findViewById(R.id.feedback_cleanliness_rating);
        RatingBar feedback_service_rating = (RatingBar) feedback.findViewById(R.id.feedback_service_rating);

        feedback_reporter.setText(edt_reporter);
        feedback_restaurant_name.setText(edt_restaurant_name);
        feedback_restaurant_type.setText(edt_restaurant_type);
        feedback_time_visit.setText(edt_time_visit);
        feedback_price.setText(edt_price);
        feedback_notes.setText(edt_notes);
        feedback_food_quality_rating.setRating(food_quality_rating);
        feedback_cleanliness_rating.setRating(cleanliness_rating);
        feedback_service_rating.setRating(service_rating);

        Button btnfeedbackCancel = (Button) feedback.findViewById(R.id.feedbackCancel);
        btnfeedbackCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback.dismiss();
            }
        });
        Button btnfeedbackSend = (Button) feedback.findViewById(R.id.feedbackSend);
        btnfeedbackSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback.dismiss();
            }
        });

        feedback.show();
    }

}