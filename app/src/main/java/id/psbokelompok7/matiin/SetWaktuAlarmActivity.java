package id.psbokelompok7.matiin;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetWaktuAlarmActivity extends AppCompatActivity {
    private Button buttonSetWaktuAlarm;
    private Button buttonBatalGantiAlarm;
    int hour;
    int minute;
    int second;
    String hourStr;
    String minuteStr;

    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_waktu_alarm);

        calendar = Calendar.getInstance();
        buttonSetWaktuAlarm = findViewById(R.id.button_set_waktu_alarm);
        buttonBatalGantiAlarm = findViewById(R.id.button_batal_ganti_alarm);
        alarmTimePicker = findViewById(R.id.time_picker_alarm);

        buttonSetWaktuAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTextWaktuAlarm();
            }
        });

        buttonBatalGantiAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    private void updateTextWaktuAlarm() {
        //Buat support Android Marshmallow ke atas. getHour() nya beda bro
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = alarmTimePicker.getHour();
            minute = alarmTimePicker.getMinute();
        }

        //Buat support Android di bawah Marshmallow.
        else {
            hour = alarmTimePicker.getCurrentHour();
            minute = alarmTimePicker.getCurrentMinute();
        }

        //11:3 -> 11:03
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        hourStr = String.valueOf(hour);
        if (minute < 10) {
            minuteStr = "0" + String.valueOf(minute);
        } else {
            minuteStr = String.valueOf(minute);
        }

        //Kirim extra buat ganti text waktu alarm di MainActivity.java
        Intent intentExtra = new Intent(this, MainActivity.class);
        intentExtra.putExtra("ganti", hourStr + ":" + minuteStr);
        intentExtra.putExtra("calendar", calendar);
        startActivity(intentExtra);
    }

    private void openMainActivity() {
        Intent intentOpen = new Intent(this, MainActivity.class);
        startActivity(intentOpen);
    }
}

