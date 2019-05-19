package id.psbokelompok7.matiin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Toast;


public class DialogSoal extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("SOAL")
                .setMessage("Ini soal")
                .setPositiveButton("JAWAB", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        matikanWaktuAlarm();
                        Toast.makeText(getContext(), "anggap alarm mati", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }
}