package id.psbokelompok7.matiin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogSoal.DialogSoalListener{

    //Basic View
    private Button buttonTambahWaktuAlarm;
    private Button buttonMatikanWaktuAlarm;
    private ArrayList<ItemWaktu> mCardWaktu;
    private String waktuAlarmBaru;

    //Usable variables
    private int currentPosition;
    private int positionSP;
    Calendar calendar = Calendar.getInstance();
    String buttonVisibility;

    //Recycle View
    private RecyclerView mRecyclerView;
    private CardWaktuAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Alarm Manager
    private PendingIntent pendingIntent;
    AlarmManager alarmManager;

    //Animasi
    Animation toVisible;
    Animation toGone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(broadcastReceiver, new IntentFilter("anim button"));

        loadStateCardWaktu();   //Load keadaan terbaru.
        buildCardWaktu();
        setViewUtama();
        gantiCardWaktu();       //Ganti isi Card Waktu dengan waktu yang di-set.
        setWaktuAlarm();

    }

    //Terima broadcast munculkan tombol 'MATIIN' kalo alarm nyala.
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                buttonVisibility = intent.getStringExtra("visibility");
                switch (buttonVisibility) {
                    case "visible":
                        showButtonMatikanAlarm();
                        break;
                    case "gone":
                        hideButtonMatikanAlarm();
                        break;
                    default:
                        //do nothing
                        break;
                }
            }
        }
    };

    public void loadStateCardWaktu() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("current card waktu", null);
        positionSP = sharedPreferences.getInt("current position", 0);
        Type type = new TypeToken<ArrayList<ItemWaktu>>() {
        }.getType();
        mCardWaktu = gson.fromJson(json, type);

        if (mCardWaktu == null) {
            mCardWaktu = new ArrayList<>();
        }
    }

    //Simpan keadaan terbaru, dipanggil saat pindah activity.
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
        Intent intentOpen = new Intent(this, SetWaktuAlarmActivity.class);
        startActivity(intentOpen);
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
            intentAlarm.putExtra("alarm is", "on");      //Kasih intent alarm on ke Alarm Reciever

            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);      // Get the alarm manager service

            calendar = (Calendar) getIntent().getSerializableExtra("calendar");     //Ambil extra calendar.
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            Toast.makeText(getApplicationContext(), "Alarm telah di-set!", Toast.LENGTH_SHORT).show();
        }
    }

    public void matikanWaktuAlarm() {
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        intentAlarm.putExtra("alarm is", "off");      //Kasih intent alarm off ke Alarm Reciever
        sendBroadcast(intentAlarm);

        alarmManager.cancel(pendingIntent);

        Toast.makeText(this, "Alarm telah dimatikan!", Toast.LENGTH_SHORT).show();
    }



    private void showButtonMatikanAlarm() {
        buttonMatikanWaktuAlarm.setVisibility(View.VISIBLE);
        buttonMatikanWaktuAlarm.startAnimation(toVisible);
        buttonTambahWaktuAlarm.startAnimation(toVisible);
    }

    private void hideButtonMatikanAlarm() {
        buttonMatikanWaktuAlarm.setVisibility(View.GONE);
        buttonMatikanWaktuAlarm.startAnimation(toGone);
        buttonTambahWaktuAlarm.startAnimation(toGone);
    }

    public void openDialogSoal() {
        DialogSoal exampleDialog = new DialogSoal();
        exampleDialog.show(getSupportFragmentManager(), "dialog soal");
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

    private void setViewUtama() {
        toVisible = AnimationUtils.loadAnimation(this, R.anim.to_visible_animation);
        toGone = AnimationUtils.loadAnimation(this, R.anim.to_gone_animation);
        buttonTambahWaktuAlarm = findViewById(R.id.button_tambah_waktu_alarm);
        buttonMatikanWaktuAlarm = findViewById(R.id.button_matikan_waktu_alarm);

        buttonTambahWaktuAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahWaktuAlarm();
            }
        });

        buttonMatikanWaktuAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogSoal();
            }
        });
    }
}