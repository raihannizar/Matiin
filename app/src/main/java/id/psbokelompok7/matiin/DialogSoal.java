package id.psbokelompok7.matiin;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class DialogSoal extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.framesoal,null);
        TextView question = (TextView) view.findViewById(R.id.textView);
        final EditText answer =  view.findViewById(R.id.edit_text);
        Button ok = (Button) view.findViewById(R.id.button);
        //menampulkan dialog
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        //deklarasikan operasi yang dikeluarkan dalam dialog tersebut
        char operator[]={'+','-','*'};
        //lakukan keluaran secara acak
        Random question1 = new Random();
        char operasi = operator[question1.nextInt(3 - 0) + 0];

        Random question2 = new Random();
        int tanya1 = question2.nextInt(10 - 1) + 1;

        Random question3 = new Random();
        int tanya2 = question3.nextInt(10 - 1) + 10;
        // variabel tanya3 di set 0 sebagai jawaban
        int tanya3=0;

        switch(operasi)
        {
            case '+':
                tanya3 = tanya1 + tanya2;
                break;
            case '-':
                tanya3 = tanya1 - tanya2;
                break;
            case '*':
                tanya3 = tanya1 * tanya2;
                break;
            default:
        }
        //set untuk jawaban yang benar
        final String jawaban = String.valueOf(tanya3);
        question.setText(tanya1+" "+operasi+" "+tanya2+" = ?");

        //apabila tombol ok diklik
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Masukkan Jawabanmu.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //deklarasikan string jawaban2 untuk mendapatkan string jawaban
                    String jawaban2 = answer.getText().toString();

                    if(jawaban2.equals(jawaban)) {

                        // stop the ringtone, masi belum bisa
                       // Intent intentStop = new Intent(this, AlarmReceiver.class);
                       /// PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1253, intentStop, 0);
                       // AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                       // alarmManager.cancel(pendingIntent);

                        //dialog berakhir
                        dialog.dismiss();
                    }
                    else  {
                        Toast.makeText(getContext(), "Jawabanmu Salah! Ulangi lagi!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
       return builder.create();
    }
}

//kodingan lama,sebelum gw edit
//builder.setTitle("SOAL")
// .setMessage("Ini soal")
//.setPositiveButton("JAWAB", new DialogInterface.OnClickListener() {
// @Override
// public void onClick(DialogInterface dialogInterface, int i) {
//                        matikanWaktuAlarm();
//     Toast.makeText(getContext(), "anggap alarm mati", Toast.LENGTH_SHORT).show();
//    }
// });