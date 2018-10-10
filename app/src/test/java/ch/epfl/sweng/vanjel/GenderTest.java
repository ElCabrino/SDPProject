package ch.epfl.sweng.vanjel;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenderTest {

    @Test
    public void testToString() {

        Gender male = Gender.Male;
        Gender female = Gender.Female;
        Gender other = Gender.Other;

        assertEquals("Unexpected Gender male toString", "Male", male.toString());
        assertEquals("Unexpected Gender female toString", "Female", female.toString());
        assertEquals("Unexpected Gender other toString", "Other", other.toString());
    }
}