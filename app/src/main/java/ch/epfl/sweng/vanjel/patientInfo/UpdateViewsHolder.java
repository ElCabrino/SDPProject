package ch.epfl.sweng.vanjel.patientInfo;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.epfl.sweng.vanjel.R;

/**
 * A utility class to create an object holding views initialized for a given patient information category.
 *
 * @author Nicolas BRANDT
 */
class UpdateViewsHolder {

    private final EditText androidName;
    private final EditText additionalField1;
    private final EditText additionalField2;
    private final Button buttonUpdate;
    private final Button buttonDelete;

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

    /**
     * Method to create an UpdateViewsHolder corresponding to a patient information containing a single field of information
     *
     * @param dialogView a dialogView
     * @return a new UpdateViewsHolder with views corresponding to a patient information containing a single field of information
     */
    static UpdateViewsHolder forSingleFieldInfo(View dialogView) {
        return new UpdateViewsHolder(dialogView.findViewById(R.id.patientInfoUpdateEditView),
                null,
                null,
                dialogView.findViewById(R.id.buttonPatientInfoUpdate),
                dialogView.findViewById(R.id.buttonPatientInfoDelete));
    }

    /**
     * Method to create an UpdateViewsHolder corresponding to a patient information containing two fields of information
     *
     * @param dialogView a dialogView
     * @return a new UpdateViewsHolder with views corresponding to a patient information containing two fields of information
     */
    static UpdateViewsHolder forDoubleFieldInfo(View dialogView) {
        return new UpdateViewsHolder(dialogView.findViewById(R.id.patientInfoUpdateMainInfo),
                dialogView.findViewById(R.id.patientInfoUpdateSecondaryInfo),
                null,
                dialogView.findViewById(R.id.buttonPatientInfoUpdateDoubleInfo),
                dialogView.findViewById(R.id.buttonPatientInfoDeleteDoubleInfo));
    }

    /**
     * Method to create an UpdateViewsHolder corresponding to a patient drug information containing
     *
     * @param dialogView a dialogView
     * @return a new UpdateViewsHolder with views corresponding to a patient drug information
     */
    static UpdateViewsHolder forDrug(View dialogView) {
        return new UpdateViewsHolder(dialogView.findViewById(R.id.patientInfoUpdateDrugDrug),
                dialogView.findViewById(R.id.patientInfoUpdateDrugDosage),
                dialogView.findViewById(R.id.patientInfoUpdateDrugFrequency),
                dialogView.findViewById(R.id.buttonPatientInfoUpdateDrug),
                dialogView.findViewById(R.id.buttonPatientInfoDeleteDrug));
    }




}
