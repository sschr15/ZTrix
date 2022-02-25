package electra.ztrix.model.game.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the Coordinate class.
 *
 * @author Electra
 */
public class TestCoordinate {

    /**
     * Tests the Coordinate X and Y fields.
     */
    @Test
    public void testCoordinateXY () {
        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                final Coordinate coord = new Coordinate( x, y );
                assertEquals( x, coord.x,
                        "Coordinate X was wrong." );
                assertEquals( y, coord.y,
                        "Coordinate Y was wrong." );
            }
        }
    }

    /**
     * Tests that equals() works properly.
     */
    @Test
    public void testCoordinateEquals () {
        final Coordinate origin = new Coordinate( 0, 0 );
        assertEquals( origin, origin,
                origin + " was not equal to itself." );
        assertNotEquals( origin, null,
                origin + " was incorrectly equal to null." );
        assertNotEquals( origin, "Test",
                origin + " was incorrectly equal to a String." );
        assertEquals( origin, Coordinate.ORIGIN,
                origin + " was not equal to " + Coordinate.ORIGIN );

        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                final Coordinate coord = new Coordinate( x, y );
                final Coordinate coordEq = new Coordinate( x, y );
                assertEquals( coord, coordEq,
                        coord + " was not equal to " + coordEq + "." );
                final Coordinate coordX = new Coordinate( x + 1, y );
                assertNotEquals( coord, coordX,
                        coord + " was incorrectly equal to " + coordX + "." );
                final Coordinate coordY = new Coordinate( x, y + 1 );
                assertNotEquals( coord, coordY,
                        coord + " was incorrectly equal to " + coordY + "." );
            }
        }
    }

    /**
     * Tests that hashCode() works properly.
     */
    @Test
    public void testCoordinateHashCode () {
        final Coordinate origin = new Coordinate( 0, 0 );
        assertEquals( origin.hashCode(), origin.hashCode(),
                origin + ".hashCode() was not equal to itself" );
        assertEquals( origin.hashCode(), Coordinate.ORIGIN.hashCode(),
                origin + ".hashCode() was not equal to " + Coordinate.ORIGIN + ".hashCode()" );

        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                final Coordinate coord = new Coordinate( x, y );
                final Coordinate coordEq = new Coordinate( x, y );
                assertEquals( coord.hashCode(), coordEq.hashCode(),
                        coord + ".hashCode() was not equal to " + coordEq + ".hashCode()." );
            }
        }
    }

    /**
     * Tests that toString() works properly.
     */
    @Test
    public void testCoordinateToString () {
        assertEquals( "(0, 0)", Coordinate.ORIGIN.toString(),
                Coordinate.ORIGIN + ".toString() was not correct." );

        final Coordinate coord1 = new Coordinate( 1, 4 );
        assertEquals( "(1, 4)", coord1.toString(),
                coord1 + ".toString() was not correct." );

        final Coordinate coord2 = new Coordinate( -3, 5 );
        assertEquals( "(-3, 5)", coord2.toString(),
                coord2 + ".toString() was not correct." );

        final Coordinate coord3 = new Coordinate( 4, -20 );
        assertEquals( "(4, -20)", coord3.toString(),
                coord3 + ".toString() was not correct." );

        final Coordinate coord4 = new Coordinate( -70, -3 );
        assertEquals( "(-70, -3)", coord4.toString(),
                coord4 + ".toString() was not correct." );
    }
}
