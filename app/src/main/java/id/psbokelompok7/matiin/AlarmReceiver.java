package id.psbokelompok7.matiin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Pass value state alarm dari MainActivity ke RingtoneService buat nentuin musik harus nyala/ga.
        String alarmState = intent.getStringExtra("alarm is");      //Fetch extra dari MainActivity penanda alarm nyala/ga.
        Intent intentRingtone = new Intent(context, RingtonePlayingService.class);      //Kasih intent ke RingtoneService
        intentRingtone.putExtra("alarm is", alarmState);      //Kasih extra penanda alarm nyala/ga ke RingtoneService.
        context.startService(intentRingtone);       //Start RingtoneService

        //Kirim intent show button ke MainActivity melalui broadcast. Jalan kalo alarm nyala.
        if (alarmState.equals("on")) {
            Intent intentShowButtonMatikan = new Intent("anim button");
            intentShowButtonMatikan.putExtra("visibility", "visible");
            context.sendBroadcast(intentShowButtonMatikan);        //Kirim intent kalo alarm nyala munculin tombol "MATIIN!".
        }
    }

}
