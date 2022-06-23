package modele.donnee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class LieuTest {
    @Test
    public void testToLatLong() {
        Lieu lieu = new Lieu(246868.29577763, 6742521.64830076, true);
        lieu.toLatLong();
        assertEquals(-3.03924533, lieu.getxCoord(), 0.0001);
        assertEquals(47.62704650, lieu.getyCoord(), 0.0001);
        lieu.toLatLong();
        assertEquals(-3.03924533, lieu.getxCoord(), 0.0001);
        assertEquals(47.62704650, lieu.getyCoord(), 0.0001);
    }

    @Test
    public void testLambert() {
        Lieu lieu = new Lieu(-3.03924533, 47.62704650, false);
        lieu.toLambert();
        assertEquals(246868.29577763, lieu.getxCoord(), 0.01);
        assertEquals(6742521.64830076, lieu.getyCoord(), 0.01);
        lieu.toLambert();
        assertEquals(246868.29577763, lieu.getxCoord(), 0.01);
        assertEquals(6742521.64830076, lieu.getyCoord(), 0.01);
    }
}
