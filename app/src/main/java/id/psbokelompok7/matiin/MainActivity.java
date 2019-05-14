package id.psbokelompok7.matiin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button buttonTambahWaktuAlarm;
    private ArrayList<ItemWaktu> mCardWaktu;
    private String waktuAlarmBaru;
    private int currentPosition;
    private int positionSP;
    Calendar calendar = Calendar.getInstance();

    private RecyclerView mRecyclerView;
    private CardWaktuAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadStateCardWaktu();   //Load keadaan terbaru.
        buildCardWaktu();
        setButtonTambahWaktuAlarm();
        gantiCardWaktu();       //Ganti isi Card Waktu.
        setWaktuAlarm();


    }

    public void loadStateCardWaktu() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("current card waktu", null);
        positionSP = sharedPreferences.getInt("current position", 0);
        Type type = new TypeToken<ArrayList<ItemWaktu>>() {}.getType();
        mCardWaktu = gson.fromJson(json, type);

        if (mCardWaktu == null) {
            mCardWaktu = new ArrayList<>();
        }
    }

    public void saveStateCardWaktu() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mCardWaktu);
        editor.putString("current card waktu", json);
        editor.putInt("current position", currentPosition);
        editor.apply();
    }

    private void tambahWaktuAlarm() {
        mCardWaktu.add(new ItemWaktu("00:00"));
        mAdapter.notifyItemInserted(mCardWaktu.size());
    }

    private void hapusWaktuAlarm(int position) {
        mCardWaktu.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void openSetWaktuAlarm() {
        Intent intent = new Intent(this, SetWaktuAlarmActivity.class);
        startActivity(intent);
    }

    private void gantiCardWaktu() {
        if (getIntent().getExtras() != null) {
            mCardWaktu.get(positionSP).gantiTextWaktu(getIntent().getStringExtra("ganti"));
            mAdapter.notifyItemChanged(positionSP);
        }
    }

    private void setWaktuAlarm() {
        if (getIntent().getExtras() != null) {
            Intent intentAlarm = new Intent(this, AlarmReceiver.class);

            // Get the alarm manager service
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            calendar = (Calendar) getIntent().getSerializableExtra("calendar");
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(getApplicationContext(), "alarm di-set", Toast.LENGTH_SHORT).show();
        }
    }

    private void buildCardWaktu() {
        mRecyclerView = findViewById(R.id.card_waktu);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CardWaktuAdapter(mCardWaktu);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CardWaktuAdapter.OnItemClickListener() {
            @Override
            public void onGantiClick(int position) {
                currentPosition = position;
                saveStateCardWaktu();       //Simpan keadaan terbaru pas mau ganti activity.
                openSetWaktuAlarm();        //Ganti activty ke SetWaktuAlarm.java
            }

            @Override
            public void onHapusClick(int position) {
                hapusWaktuAlarm(position);
            }
        });
    }

    private void setButtonTambahWaktuAlarm() {
        buttonTambahWaktuAlarm = findViewById(R.id.button_tambah_waktu_alarm);
        buttonTambahWaktuAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahWaktuAlarm();
            }
        });
    }
}
