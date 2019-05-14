package id.psbokelompok7.matiin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CardWaktuAdapter extends RecyclerView.Adapter<CardWaktuAdapter.CardWaktuViewHolder> {
    private ArrayList<ItemWaktu> mCardWaktu;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onGantiClick(int position);
        void onHapusClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class CardWaktuViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextWaktu;
        public TextView mTextGanti;
        public TextView mTextMatikan;

        public CardWaktuViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextWaktu = itemView.findViewById(R.id.text_view_waktu_alarm);
            mTextGanti = itemView.findViewById(R.id.text_view_ganti);
            mTextMatikan = itemView.findViewById(R.id.text_view_hapus);

            mTextGanti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onGantiClick(position);
                        }
                    }
                }
            });

            mTextMatikan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onHapusClick(position);
                        }
                    }
                }
            });
        }
    }

    public CardWaktuAdapter(ArrayList<ItemWaktu> cardWaktu) {
        mCardWaktu = cardWaktu;
    }

    @Override
    public CardWaktuViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_waktu, parent, false);
        return new CardWaktuViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(CardWaktuViewHolder holder, int position) {
        ItemWaktu itemWaktuCurrent = mCardWaktu.get(position);
        holder.mTextWaktu.setText(itemWaktuCurrent.getTextWaktu());
    }

    @Override
    public int getItemCount() {
        return mCardWaktu.size();
    }
}
