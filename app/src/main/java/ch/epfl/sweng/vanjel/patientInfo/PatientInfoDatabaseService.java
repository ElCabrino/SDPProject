package ch.epfl.sweng.vanjel.patientInfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;

/**
 * A utility class containing methods for the PatientInfo class.
 *
 * @author Nicolas BRANDT
 * @reviewer Vincent CABRINI
 */
class PatientInfoDatabaseService {

    private String UserID; //FirebaseAuth.getInstance().getUid();
    private AppCompatActivity activity;
    private DatabaseReference userDatabaseReference;
    final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    PatientInfoDatabaseService(AppCompatActivity activity, String patientID) {
        this.activity = activity;
        this.userDatabaseReference = database.getReference("Patient").child(patientID);
    }


    //LISTENERS

    /**
     * A generic method for creating listeners for medical informations that can be displayed in a list.
     *
     * @param typeList a list of the type of the patient information
     * @param listView the listView corresponding to the list
     * @param category the category of the patient information
     * @param c        the class used
     * @param adapter  the adapter for the list and the listView
     * @param <T>      the class used
     */
    //TODO: check if c param is needed considering T is given
    <T> void addListListener(final List<T> typeList, final ListView listView, final String category, final Class c, final ArrayAdapter<T> adapter) {
        DatabaseReference db = userDatabaseReference.child(category);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                typeList.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    T item = (T) snap.getValue(c);
                    typeList.add((T) item);

                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Method to add listeners for medical information that does not require a list.
     *
     * @param textView the textview of corresponding to the information
     * @param category the category of the information
     */
    void addAmountListener(final TextView textView, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category);
        dbCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String s = dataSnapshot.getValue(String.class);
                    textView.setText(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //LISTVIEW LISTENERS FOR UPDATING ITEMS
    void listViewListener(ListView listView, final List<? extends Info> typeList, final String category, final Context context) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String oldInfo = typeList.get(i).getAndroidInfo();
                View dialogView = getDialogView(category, inflater);
                showUpdateInfoString(oldInfo, category, dialogView, dialogBuilder);
            }
        });
    }

    /**
     * Method to return the appropriate inflated View for the update of a patient information
     *
     * @param category a category of patient information
     * @param inflater an inflater
     * @return the inflated update View corresponding to the category
     */
    private View getDialogView(String category, LayoutInflater inflater) {
        switch (category) {
            case "Condition":
            case "Allergy":
            case "Substance":
                return inflater.inflate(R.layout.activity_patient_info_update, null);
            case "Surgery":
            case "DrugReaction":
                return inflater.inflate(R.layout.activity_patient_info_update_surgery, null);
            //Drug
            default:
                return inflater.inflate(R.layout.activity_patient_info_update_drug, null);
        }
    }


    private void showUpdateInfoString(final String oldInfo, final String category, View dialogView, AlertDialog.Builder dialogBuilder) {
        dialogBuilder.setView(dialogView);
        final UpdateViewsHolder holder = getHolder(category, dialogView);
        dialogBuilder.setTitle(String.format("Updating %s", category.toLowerCase()));

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        holder.getButtonUpdate().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info info = getCorrectInfo(category, holder.getAndroidName(), holder.getAdditionalField1(), holder.getAdditionalField2());
                //TODO: check information present
                /*if (TextUtils.isDigitsOnly(info)) {
                    editTextName.setError("Information required");
                    return;
                }*/
                deleteItem(oldInfo, category);
                addItemToDatabase(category, info);

                alertDialog.dismiss();
            }
        });
        //}

        holder.getButtonDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(oldInfo, category);
                alertDialog.dismiss();
            }
        });
    }

    /**
     * Method to return an UpdateViewsHolder object containing the appropriate views for the patient information category
     *
     * @param category a category of patient information
     * @param dialogView a dialogView
     * @return UpdateViewsHolder object with the fields corresponding to the patient information category
     */
    private UpdateViewsHolder getHolder(String category, View dialogView) {
        switch (category) {
            case "Condition":
            case "Allergy":
            case "Substance":
                return UpdateViewsHolder.forSingleFieldInfo(dialogView);
            case "Surgery":
            case "DrugReaction":
                return UpdateViewsHolder.forDoubleFieldInfo(dialogView);
            //drug
            default:
                return UpdateViewsHolder.forDrug(dialogView);
        }
    }

    /**
     * Method to return the correct medical information from EditTexts given a category.
     *
     * @param category the category of the medical information
     * @param androidName the main information field, used by all informations
     * @param additionalField1 an additional information field, will be null if the category does not require it
     * @param additionalField2 an additional information field, will be null if the category does not require it
     * @return the medical information of the category
     */
    private Info getCorrectInfo(String category, EditText androidName, EditText additionalField1, EditText additionalField2) {
        switch (category) {
            case "Condition":
            case "Allergy":
            case "Substance":
                return new InfoString(PatientInfo.getTextFromField(androidName));
            case "Surgery":
                return new Surgery(PatientInfo.getTextFromField(androidName),
                        PatientInfo.getTextFromField(additionalField1));
            case "DrugReaction":
                return new DrugReaction(PatientInfo.getTextFromField(androidName),
                        PatientInfo.getTextFromField(additionalField1));
            default:
                return new Drug(PatientInfo.getTextFromField(androidName),
                        PatientInfo.getTextFromField(additionalField1), PatientInfo.getTextFromField(additionalField2));
        }
    }


    //SETTERS
    /**
     * Method for adding informations on firebase for medical informations that can be displayed in a list.
     *
     * @param category category of the medical information
     * @param info the information
     */
    void addItemToDatabase(String category, Info info) {
        String firebaseName = info.getAndroidInfo();
        DatabaseReference dbCat = userDatabaseReference.child(category);
        String toastText = category;
        // correct string format
        if (category.equals("DrugReaction")) {
            toastText = "Drug reaction";
        }

        if (!TextUtils.isEmpty(firebaseName)) {
            dbCat.child(firebaseName).setValue(info);
            Toast.makeText(this.activity, String.format("%s added.", toastText), Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity, String.format("Please enter the %s information you want to add.", toastText.toLowerCase()), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to add a non-list patient information to firebase.
     *
     * @param amount the value to be added
     * @param category the category of the patient information
     */
    void addAmount(String amount, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category);
        if (!TextUtils.isEmpty(amount)) {
            dbCat.setValue(amount);
            Toast.makeText(this.activity, String.format("%s amount added.", category), Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity, String.format("Please enter the %s amount.", category.toLowerCase()), Toast.LENGTH_LONG).show();
        }
    }

    //TODO: check if method needed
    /*void updateCondition(String info, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category).child(info);
        InfoString cond = new InfoString(info);
        dbCat.setValue(cond);
        Toast.makeText(this.activity,"Condition updated",Toast.LENGTH_LONG).show();

    }*/

    //TODO: fix delete toast display when updating

    /**
     * Method to delete an item from firebase.
     *
     * @param info the information to delete
     * @param category the category of the information
     */
    private void deleteItem(String info, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category).child(info);
        dbCat.removeValue();
        Toast.makeText(this.activity, String.format("%s deleted", category), Toast.LENGTH_LONG).show();
    }


}
