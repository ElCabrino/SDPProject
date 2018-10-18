package ch.epfl.sweng.vanjel;

import android.media.tv.TvView;

public class Drug extends Info{
    private String drug;
    private String dosage;
    private String frequency;

    public Drug() {}

    public Drug(String drug, String dosage, String frequency) {
        this.drug = drug;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public String getDrug() {
        return drug;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }


    @Override
    String getAndroidInfo() {
        return getDrug();
    }
}
