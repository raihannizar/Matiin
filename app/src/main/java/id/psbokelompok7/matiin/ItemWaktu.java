package id.psbokelompok7.matiin;

public class ItemWaktu {
    private String mTextWaktu;

    public ItemWaktu(String textWaktu) {
        mTextWaktu = textWaktu;
    }

    public String getTextWaktu() {
        return mTextWaktu;
    }

    public void gantiTextWaktu(String textWaktuBaru) {
        mTextWaktu = textWaktuBaru;
    }
}