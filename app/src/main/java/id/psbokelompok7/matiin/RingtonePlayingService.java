package id.psbokelompok7.matiin;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RingtonePlayingService extends Service {

    //    @androidx.annotation.Nullable
    MediaPlayer ringtoneAlarm;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("local service", "recieved start id" + startId + ":" + intent);
        Log.e("nyala", "gan");

        //buat media player
        ringtoneAlarm = MediaPlayer.create(this, R.raw.ipb8it);
        ringtoneAlarm.start();

        return START_NOT_STICKY;
    }

    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "on destroy called", Toast.LENGTH_SHORT).show();
    }

}
