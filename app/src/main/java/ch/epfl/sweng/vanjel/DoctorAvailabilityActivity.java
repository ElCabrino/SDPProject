package ch.epfl.sweng.vanjel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;


public class DoctorAvailabilityActivity extends AppCompatActivity {

    private static final String TAG = "DoctorAvailability";

    private int NUMBER_OF_SLOTS = TimeAvailability.getIdLength();

    private Button valid;

    private ToggleButton[] buttons;

    private Boolean[] slots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_availability);

        initButtons();

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }

    private void initButtons() {

        buttons = new ToggleButton[NUMBER_OF_SLOTS];

        for (int i=0; i<NUMBER_OF_SLOTS; i++) {
            buttons[i] = findViewById(TimeAvailability.times[i]);
        }

/*        buttons[0] = findViewById(R.id.monday8);
        buttons[1] = findViewById(R.id.monday8_3);
        buttons[2] = findViewById(R.id.monday9);
        buttons[3] = findViewById(R.id.monday9_3);
        buttons[4] = findViewById(R.id.monday10);
        buttons[5] = findViewById(R.id.monday10_3);
        buttons[6] = findViewById(R.id.monday11);
        buttons[7] = findViewById(R.id.monday11_3);
        buttons[8] = findViewById(R.id.monday12);
        buttons[9] = findViewById(R.id.monday12_3);
        buttons[10] = findViewById(R.id.monday13);
        buttons[11] = findViewById(R.id.monday13_3);
        buttons[12] = findViewById(R.id.monday14);
        buttons[13] = findViewById(R.id.monday14_3);
        buttons[14] = findViewById(R.id.monday15);
        buttons[15] = findViewById(R.id.monday15_3);
        buttons[16] = findViewById(R.id.monday16);
        buttons[17] = findViewById(R.id.monday16_3);
        buttons[18] = findViewById(R.id.monday17);
        buttons[19] = findViewById(R.id.monday17_3);
        buttons[20] = findViewById(R.id.monday18);
        buttons[21] = findViewById(R.id.monday18_3);

        buttons[22] = findViewById(R.id.tuesday8);
        buttons[23] = findViewById(R.id.tuesday8_3);
        buttons[24] = findViewById(R.id.tuesday9);
        buttons[25] = findViewById(R.id.tuesday9_3);
        buttons[26] = findViewById(R.id.tuesday10);
        buttons[27] = findViewById(R.id.tuesday10_3);
        buttons[28] = findViewById(R.id.tuesday11);
        buttons[29] = findViewById(R.id.tuesday11_3);
        buttons[30] = findViewById(R.id.tuesday12);
        buttons[31] = findViewById(R.id.tuesday12_3);
        buttons[32] = findViewById(R.id.tuesday13);
        buttons[33] = findViewById(R.id.tuesday13_3);
        buttons[34] = findViewById(R.id.tuesday14);
        buttons[35] = findViewById(R.id.tuesday14_3);
        buttons[36] = findViewById(R.id.tuesday15);
        buttons[37] = findViewById(R.id.tuesday15_3);
        buttons[38] = findViewById(R.id.tuesday16);
        buttons[39] = findViewById(R.id.tuesday16_3);
        buttons[40] = findViewById(R.id.tuesday17);
        buttons[41] = findViewById(R.id.tuesday17_3);
        buttons[42] = findViewById(R.id.tuesday18);
        buttons[43] = findViewById(R.id.tuesday18_3);

        buttons[44] = findViewById(R.id.wednesday8);
        buttons[45] = findViewById(R.id.wednesday8_3);
        buttons[46] = findViewById(R.id.wednesday9);
        buttons[47] = findViewById(R.id.wednesday9_3);
        buttons[48] = findViewById(R.id.wednesday10);
        buttons[49] = findViewById(R.id.wednesday10_3);
        buttons[50] = findViewById(R.id.wednesday11);
        buttons[51] = findViewById(R.id.wednesday11_3);
        buttons[52] = findViewById(R.id.wednesday12);
        buttons[53] = findViewById(R.id.wednesday12_3);
        buttons[54] = findViewById(R.id.wednesday13);
        buttons[55] = findViewById(R.id.wednesday13_3);
        buttons[56] = findViewById(R.id.wednesday14);
        buttons[57] = findViewById(R.id.wednesday14_3);
        buttons[58] = findViewById(R.id.wednesday15);
        buttons[59] = findViewById(R.id.wednesday15_3);
        buttons[60] = findViewById(R.id.wednesday16);
        buttons[61] = findViewById(R.id.wednesday16_3);
        buttons[62] = findViewById(R.id.wednesday17);
        buttons[63] = findViewById(R.id.wednesday17_3);
        buttons[64] = findViewById(R.id.wednesday18);
        buttons[65] = findViewById(R.id.wednesday18_3);

        buttons[66] = findViewById(R.id.thursday8);
        buttons[67] = findViewById(R.id.thursday8_3);
        buttons[68] = findViewById(R.id.thursday9);
        buttons[69] = findViewById(R.id.thursday9_3);
        buttons[70] = findViewById(R.id.thursday10);
        buttons[71] = findViewById(R.id.thursday10_3);
        buttons[72] = findViewById(R.id.thursday11);
        buttons[73] = findViewById(R.id.thursday11_3);
        buttons[74] = findViewById(R.id.thursday12);
        buttons[75] = findViewById(R.id.thursday12_3);
        buttons[76] = findViewById(R.id.thursday13);
        buttons[77] = findViewById(R.id.thursday13_3);
        buttons[78] = findViewById(R.id.thursday14);
        buttons[79] = findViewById(R.id.thursday14_3);
        buttons[80] = findViewById(R.id.thursday15);
        buttons[81] = findViewById(R.id.thursday15_3);
        buttons[82] = findViewById(R.id.thursday16);
        buttons[83] = findViewById(R.id.thursday16_3);
        buttons[84] = findViewById(R.id.thursday17);
        buttons[85] = findViewById(R.id.thursday17_3);
        buttons[86] = findViewById(R.id.thursday18);
        buttons[87] = findViewById(R.id.thursday18_3);

        buttons[88] = findViewById(R.id.friday8);
        buttons[89] = findViewById(R.id.friday8_3);
        buttons[90] = findViewById(R.id.friday9);
        buttons[91] = findViewById(R.id.friday9_3);
        buttons[92] = findViewById(R.id.friday10);
        buttons[93] = findViewById(R.id.friday10_3);
        buttons[94] = findViewById(R.id.friday11);
        buttons[95] = findViewById(R.id.friday11_3);
        buttons[96] = findViewById(R.id.friday12);
        buttons[97] = findViewById(R.id.friday12_3);
        buttons[98] = findViewById(R.id.friday13);
        buttons[99] = findViewById(R.id.friday13_3);
        buttons[100] = findViewById(R.id.friday14);
        buttons[101] = findViewById(R.id.friday14_3);
        buttons[102] = findViewById(R.id.friday15);
        buttons[103] = findViewById(R.id.friday15_3);
        buttons[104] = findViewById(R.id.friday16);
        buttons[105] = findViewById(R.id.friday16_3);
        buttons[106] = findViewById(R.id.friday17);
        buttons[107] = findViewById(R.id.friday17_3);
        buttons[108] = findViewById(R.id.friday18);
        buttons[109] = findViewById(R.id.friday18_3);

        buttons[110] = findViewById(R.id.saturday8);
        buttons[111] = findViewById(R.id.saturday8_3);
        buttons[112] = findViewById(R.id.saturday9);
        buttons[113] = findViewById(R.id.saturday9_3);
        buttons[114] = findViewById(R.id.saturday10);
        buttons[115] = findViewById(R.id.saturday10_3);
        buttons[116] = findViewById(R.id.saturday11);
        buttons[117] = findViewById(R.id.saturday11_3);
        buttons[118] = findViewById(R.id.saturday12);
        buttons[119] = findViewById(R.id.saturday12_3);
        buttons[120] = findViewById(R.id.saturday13);
        buttons[121] = findViewById(R.id.saturday13_3);
        buttons[122] = findViewById(R.id.saturday14);
        buttons[123] = findViewById(R.id.saturday14_3);
        buttons[124] = findViewById(R.id.saturday15);
        buttons[125] = findViewById(R.id.saturday15_3);
        buttons[126] = findViewById(R.id.saturday16);
        buttons[127] = findViewById(R.id.saturday16_3);
        buttons[128] = findViewById(R.id.saturday17);
        buttons[129] = findViewById(R.id.saturday17_3);
        buttons[130] = findViewById(R.id.saturday18);
        buttons[131] = findViewById(R.id.saturday18_3);*/

        valid = findViewById(R.id.valid);
    }

    private void validate() {
        slots = new Boolean[NUMBER_OF_SLOTS];
        for (int i = 0; i < NUMBER_OF_SLOTS; i++) {
            slots[i] = buttons[i].isChecked();
        }

        //String DoctorId =FirebaseAuth.getInstance().getUid();

    }

}
