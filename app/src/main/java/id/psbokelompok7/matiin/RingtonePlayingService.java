package id.psbokelompok7.matiin;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class RingtonePlayingService extends Service {

    MediaPlayer ringtoneAlarm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        //Fetch kondisi alarm dari AlarmReceiver.
        String alarmState = intent.getStringExtra("alarm is");

//        SharedPreferences sharedPreferences = getSharedPreferences("SP_ALARM_STATE", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("alarm state", alarmState);
//        editor.apply();

//        context.sendBroadcast(intentShowButtonMatikan);        //Kirim intent kalo alarm nyala munculin tombol "MATIIN!".

        //Alarm "on", ringtone nyala. Alarm "off", ringtone matiin.
        switch (alarmState) {
            case "on":
                ringtoneAlarm = MediaPlayer.create(this, R.raw.ipb8it);
                ringtoneAlarm.setVolume(0.3f, 0.3f);
                ringtoneAlarm.setLooping(true);
                ringtoneAlarm.start();
                break;
            case "off":
                ringtoneAlarm.stop();
                ringtoneAlarm.reset();
                break;
            default:
                break;
        }

        return START_NOT_STICKY;
    }

    public void onDestroy() {
        Log.e("onDestroy", "called");
    }

}
