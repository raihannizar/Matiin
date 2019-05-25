package id.psbokelompok7.matiin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
    //Bikin listener buat nyambungin fungsi matikanWaktuAlarm ke dialog.
    DialogSoalListener listener;

    //Basic View
    TextView textViewPertanyaan;
    EditText editTextJawaban;
    Button buttonOK;

    //Variabel untuk soal.
    char operatorArray[];
    int angkaPertama;
    int angkaKedua;
    int jawaban;
    Random rand = new Random();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_soal, null);

        textViewPertanyaan = view.findViewById(R.id.text_view_pertanyaan);
        editTextJawaban = view.findViewById(R.id.edit_text_jawaban);
        buttonOK = view.findViewById(R.id.button_ok);

        //Set pertanyaan ketika dialog dibuat.
        setPertanyaan();

        //Tombol OK diklik, verifikasi jawaban
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifikasiJawaban();
            }
        });

        //Build dialog
        builder.setTitle("Pertanyaan")
                .setView(view);

        return builder.show();
    }

    //Fungsi untuk nyambungin listener ke fungsi yang diinginkan.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogSoalListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogSoalListener");
        }
    }

    //Interface listener-nya.
    public interface DialogSoalListener {

        //Fungsi matiin alarm dari MainActivity.
        void matikanWaktuAlarm();
    }

    private void setPertanyaan() {
        //Deklarasikan operasi yang dikeluarkan dalam dialog tersebut.
        operatorArray = new char[]{'+', '-', '*'};

        //Lakukan keluaran secara acak.
        char operator = operatorArray[rand.nextInt(3)];
        angkaPertama = rand.nextInt(10) + 19;
        angkaKedua = rand.nextInt(10) + 10;

        //Generate operasi
        switch (operator) {
            case '+':
                jawaban = angkaPertama + angkaKedua;
                break;
            case '-':
                jawaban = angkaPertama - angkaKedua;
                break;
            case '*':
                jawaban = angkaPertama * angkaKedua;
                break;
            default:
        }

        textViewPertanyaan.setText(angkaPertama + " " + operator + " " + angkaKedua + " = ?");
    }

    private void verifikasiJawaban() {
        String stringJawaban = editTextJawaban.getText().toString();

        if (stringJawaban.isEmpty()) {
            Toast.makeText(getContext(), "Masukkan jawabanmu.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (stringJawaban.equals(Integer.toString(jawaban))) {
                //Panggil fungsi matikanWaktuAlarm melalui listener.
                listener.matikanWaktuAlarm();

                //Kirim intent hide button ke MainActivity melalui broadcast. Jalan kalo jawabaan benar.
                Intent intentHideButtonMatikan = new Intent("anim button");
                intentHideButtonMatikan.putExtra("visibility", "gone");
                getContext().sendBroadcast(intentHideButtonMatikan);        //Kirim intent kalo jawaban benar sembunyiin tombol "MATIIN!".

                //Close dialog.
                getDialog().dismiss();
            }

            else {
                Toast.makeText(getContext(), "Jawabanmu salah, ulangi lagi!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}