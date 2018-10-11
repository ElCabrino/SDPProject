package ch.epfl.sweng.vanjel;

import android.media.tv.TvView;

public class Drug {
    private String id;
    private String drug;
    private String dosage;
    private String frequency;

    public Drug() {}

    public Drug(String id, String drug, String dosage, String frequency) {
        this.id = id;
        this.drug = drug;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public String getId() {
        return id;
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
}
