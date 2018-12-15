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

    //TEMPORARY ID
    /*TODO: put the user ID of the logged user*/
    PatientInfoDatabaseService(AppCompatActivity activity, String patientID) {
        this.activity = activity;
        //String s = auth.getCurrentUser().getUid();
        //String s = patientID;
        this.userDatabaseReference = database.getReference("Patient").child(patientID);
    }


    //LISTENERS

    /**
     * A generic method for creating listeners.
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
     * Adds listeners for informations that do not require a list
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
                addItemToDatabase(info.getAndroidInfo(), category, info);

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

    private UpdateViewsHolder getHolder(String category, View dialogView) {
        switch (category) {
            case "Condition":
            case "Allergy":
            case "Substance":
                return UpdateViewsHolder.forSingleInfo(dialogView);
            case "Surgery":
            case "DrugReaction":
                return UpdateViewsHolder.forSurgery(dialogView);
            //drug
            default:
                return UpdateViewsHolder.forDrug(dialogView);
        }
    }

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

    <T> void addItemToDatabase(String item, String category, T itemObject) {
        DatabaseReference dbCat = userDatabaseReference.child(category);
        String toastText = category;
        // correct string format
        if (category.equals("DrugReaction")) {
            toastText = "Drug reaction";
        }

        if (!TextUtils.isEmpty(item)) {
            dbCat.child(item).setValue(itemObject);
            Toast.makeText(this.activity, String.format("%s added.", toastText), Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity, String.format("Please enter the %s information you want to add.", toastText.toLowerCase()), Toast.LENGTH_LONG).show();
        }
    }

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
    private void deleteItem(String info, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category).child(info);
        dbCat.removeValue();
        Toast.makeText(this.activity, String.format("%s deleted", category), Toast.LENGTH_LONG).show();
    }


}
