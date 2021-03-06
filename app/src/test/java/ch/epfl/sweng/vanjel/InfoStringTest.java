package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.patientInfo.InfoString;

import static org.junit.Assert.assertEquals;

/**
 * @author Aslam CADER
 * @reviewer
 */
public class InfoStringTest {

    private final String info = "my Info";

    @Test
    public void testEntityEmptyConstructor(){
        InfoString infoString = new InfoString();

        infoString.setInfo(info);

        assertEquals("Unexpected info in InfoString", info, infoString.getInfo());
        assertEquals("Unexpected android  in InfoString", info, infoString.getAndroidInfo());
    }

    @Test
    public void testEntityParameterizedConstructor(){
        InfoString infoString = new InfoString(info);

        assertEquals("Unexpected info in InfoString", info, infoString.getInfo());
        assertEquals("Unexpected android  in InfoString", info, infoString.getAndroidInfo());
    }



}