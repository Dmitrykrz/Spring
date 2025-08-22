package fr.diginamic.hello;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VilleTest {

    @Test
    void getIdAndSetId() {
        Ville v = new Ville();
        v.setId(42);
        assertEquals(42, v.getId());
    }

    @Test
    void getNomAndSetNom() {
        Ville v = new Ville();
        v.setNom("Paris");
        assertEquals("Paris", v.getNom());
    }

    @Test
    void getNbHabitantsAndSetNbHabitants() {
        Ville v = new Ville();
        v.setNbHabitants(2000000);
        assertEquals(2000000, v.getNbHabitants());
    }

    @Test
    void getDepartementAndSetDepartement() {
        Ville v = new Ville();
        Departement d = new Departement();
        v.setDepartement(d);
        assertSame(d, v.getDepartement());
    }

    @Test
    void constructorWithArgs() {
        Ville v = new Ville("Lyon", 500000);
        assertEquals("Lyon", v.getNom());
        assertEquals(500000, v.getNbHabitants());
    }

    @Test
    void testToString() {
        Ville v = new Ville("Nice", 300000);
        v.setId(1);
        String str = v.toString();
        assertTrue(str.contains("Nice"));
        assertTrue(str.contains("300000"));
        assertTrue(str.contains("1"));
    }
}
