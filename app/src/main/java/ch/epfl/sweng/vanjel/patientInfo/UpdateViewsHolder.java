package ch.epfl.sweng.vanjel.patientInfo;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.epfl.sweng.vanjel.R;

public class UpdateViewsHolder {

    private EditText androidName;
    private EditText additionalField1;
    private EditText additionalField2;
    private Button buttonUpdate;
    private Button buttonDelete;

    private UpdateViewsHolder(View androidName, View additionalField1, View additionalField2, View buttonUpdate, View buttonDelete) {
        this.androidName = (EditText) androidName;
        this.additionalField1 = (EditText) additionalField1;
        this.additionalField2 = (EditText) additionalField2;
        this.buttonUpdate = (Button) buttonUpdate;
        this.buttonDelete = (Button) buttonDelete;
    }

    EditText getAndroidName() {
        return androidName;
    }

    EditText getAdditionalField1() {
        return additionalField1;
    }

    EditText getAdditionalField2() {
        return additionalField2;
    }

    Button getButtonUpdate() {
        return buttonUpdate;
    }

    Button getButtonDelete() {
        return buttonDelete;
    }

    static UpdateViewsHolder forSingleInfo(View dialogView) {
        return new UpdateViewsHolder(dialogView.findViewById(R.id.patientInfoUpdateEditView),
                null,
                null,
                dialogView.findViewById(R.id.buttonPatientInfoUpdate),
                dialogView.findViewById(R.id.buttonPatientInfoDelete));
    }

    static UpdateViewsHolder forSurgery(View dialogView) {
        return new UpdateViewsHolder(dialogView.findViewById(R.id.patientInfoUpdateSurgeryType),
                dialogView.findViewById(R.id.patientInfoUpdateSurgeryYear),
                null,
                dialogView.findViewById(R.id.buttonPatientInfoUpdateSurgery),
                dialogView.findViewById(R.id.buttonPatientInfoDeleteSurgery));
    }

    /*static UpdateViewsHolder forDrugReaction(View dialogView) {
        return new UpdateViewsHolder(dialogView.findViewById(R.id.patientInfoUpdateDrugReactionDrug),
                dialogView.findViewById(R.id.patientInfoUpdateDrugReactionReaction),
                null,
                dialogView.findViewById(R.id.buttonPatientInfoUpdateDrugReaction),
                dialogView.findViewById(R.id.buttonPatientInfoDeleteDrugReaction));
    }*/

    static UpdateViewsHolder forDrug(View dialogView) {
        return new UpdateViewsHolder(dialogView.findViewById(R.id.patientInfoUpdateDrugDrug),
                dialogView.findViewById(R.id.patientInfoUpdateDrugDosage),
                dialogView.findViewById(R.id.patientInfoUpdateDrugFrequency),
                dialogView.findViewById(R.id.buttonPatientInfoUpdateDrug),
                dialogView.findViewById(R.id.buttonPatientInfoDeleteDrug));
    }




}
