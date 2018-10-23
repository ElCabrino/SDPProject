package ch.epfl.sweng.vanjel;

public class TimeAvailability {

    public static int MONDAY = 0;
    public static int TUESDAY = 22;
    public static int WEDNESDAY = 44;
    public static int THURSDAY = 66;
    public static int FRIDAY = 88;
    public static int SATURDAY = 110;

    static int[] times = {

    R.id.monday8,
    R.id.monday8_3,
    R.id.monday9,
    R.id.monday9_3,
    R.id.monday10,
    R.id.monday10_3,
    R.id.monday11,
    R.id.monday11_3,
    R.id.monday12,
    R.id.monday12_3,
    R.id.monday13,
    R.id.monday13_3,
    R.id.monday14,
    R.id.monday14_3,
    R.id.monday15,
    R.id.monday15_3,
    R.id.monday16,
    R.id.monday16_3,
    R.id.monday17,
    R.id.monday17_3,
    R.id.monday18,
    R.id.monday18_3,

    R.id.tuesday8,
    R.id.tuesday8_3,
    R.id.tuesday9,
    R.id.tuesday9_3,
    R.id.tuesday10,
    R.id.tuesday10_3,
    R.id.tuesday11,
    R.id.tuesday11_3,
    R.id.tuesday12,
    R.id.tuesday12_3,
    R.id.tuesday13,
    R.id.tuesday13_3,
    R.id.tuesday14,
    R.id.tuesday14_3,
    R.id.tuesday15,
    R.id.tuesday15_3,
    R.id.tuesday16,
    R.id.tuesday16_3,
    R.id.tuesday17,
    R.id.tuesday17_3,
    R.id.tuesday18,
    R.id.tuesday18_3,

    R.id.wednesday8,
    R.id.wednesday8_3,
    R.id.wednesday9,
    R.id.wednesday9_3,
    R.id.wednesday10,
    R.id.wednesday10_3,
    R.id.wednesday11,
    R.id.wednesday11_3,
    R.id.wednesday12,
    R.id.wednesday12_3,
    R.id.wednesday13,
    R.id.wednesday13_3,
    R.id.wednesday14,
    R.id.wednesday14_3,
    R.id.wednesday15,
    R.id.wednesday15_3,
    R.id.wednesday16,
    R.id.wednesday16_3,
    R.id.wednesday17,
    R.id.wednesday17_3,
    R.id.wednesday18,
    R.id.wednesday18_3,

    R.id.thursday8,
    R.id.thursday8_3,
    R.id.thursday9,
    R.id.thursday9_3,
    R.id.thursday10,
    R.id.thursday10_3,
    R.id.thursday11,
    R.id.thursday11_3,
    R.id.thursday12,
    R.id.thursday12_3,
    R.id.thursday13,
    R.id.thursday13_3,
    R.id.thursday14,
    R.id.thursday14_3,
    R.id.thursday15,
    R.id.thursday15_3,
    R.id.thursday16,
    R.id.thursday16_3,
    R.id.thursday17,
    R.id.thursday17_3,
    R.id.thursday18,
    R.id.thursday18_3,

    R.id.friday8,
    R.id.friday8_3,
    R.id.friday9,
    R.id.friday9_3,
    R.id.friday10,
    R.id.friday10_3,
    R.id.friday11,
    R.id.friday11_3,
    R.id.friday12,
    R.id.friday12_3,
    R.id.friday13,
    R.id.friday13_3,
    R.id.friday14,
    R.id.friday14_3,
    R.id.friday15,
    R.id.friday15_3,
    R.id.friday16,
    R.id.friday16_3,
    R.id.friday17,
    R.id.friday17_3,
    R.id.friday18,
    R.id.friday18_3,

    R.id.saturday8,
    R.id.saturday8_3,
    R.id.saturday9,
    R.id.saturday9_3,
    R.id.saturday10,
    R.id.saturday10_3,
    R.id.saturday11,
    R.id.saturday11_3,
    R.id.saturday12,
    R.id.saturday12_3,
    R.id.saturday13,
    R.id.saturday13_3,
    R.id.saturday14,
    R.id.saturday14_3,
    R.id.saturday15,
    R.id.saturday15_3,
    R.id.saturday16,
    R.id.saturday16_3,
    R.id.saturday17,
    R.id.saturday17_3,
    R.id.saturday18,
    R.id.saturday18_3

    };

    public static int getIdLength() {
        return times.length;
    }

    public static boolean[] getDayAvailability(int start, String day, boolean[] slots) {
        if (day != null) {
            String[] groups = day.split(" / ");
            for (int i = 0; i < groups.length; i++) {
                int indStart = 999;
                int indEnd = 999;
                String[] subgroups = groups[i].split("-");
                String[] timeStart = subgroups[0].split(":");
                String[] timeEnd = subgroups[1].split(":");
                if (Integer.parseInt(timeStart[1]) == 0) {
                    indStart = start + (2 * Integer.parseInt(timeStart[0]) - 16);
                } else {
                    indStart = start + (2 * Integer.parseInt(timeStart[0]) - 15);
                }
                if (Integer.parseInt(timeEnd[1]) == 0) {
                    indEnd = start + (2 * Integer.parseInt(timeEnd[0]) - 16);
                } else {
                    indEnd = start + (2 * Integer.parseInt(timeEnd[0]) - 15);
                }
                if (indStart < slots.length && indEnd < slots.length) {
                    for (int n = indStart; n < indEnd; n++) {
                        slots[n] = true;
                    }
                }
            }
        }
        return slots;
    }
}
