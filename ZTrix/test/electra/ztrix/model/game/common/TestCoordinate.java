package electra.ztrix.model.game.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        // Check the X and Y of Coordinates with various X and Y components.
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
        // Check equality of the origin against various objects.
        final Coordinate origin = new Coordinate( 0, 0 );
        assertTrue( origin.equals( origin ),
                origin + ".equals(" + origin + ") did not return True." );
        assertFalse( origin.equals( null ),
                origin + ".equals(null) did not return False." );
        assertFalse( origin.equals( new Object() ),
                origin + ".equals(Object) did not return False." );
        assertTrue( origin.equals( Coordinate.ORIGIN ),
                origin + ".equals(" + Coordinate.ORIGIN + ") did not return True." );
        // Create Coordinates with various X and Y components.
        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                final Coordinate coord = new Coordinate( x, y );
                // Check equality of the Coordinate against other Coordinates.
                final Coordinate coordEq = new Coordinate( x, y );
                assertTrue( coord.equals( coordEq ),
                        coord + ".equals(" + coordEq + ") did not return True." );
                final Coordinate coordX = new Coordinate( x + 1, y );
                assertFalse( coord.equals( coordX ),
                        coord + ".equals(" + coordX + ") did not return False." );
                final Coordinate coordY = new Coordinate( x, y + 1 );
                assertFalse( coord.equals( coordY ),
                        coord + ".equals(" + coordY + ") did not return False." );
            }
        }
    }

    /**
     * Tests that hashCode() works properly.
     */
    @Test
    public void testCoordinateHashCode () {
        // Check the hashCode() of the origin.
        final Coordinate origin = new Coordinate( 0, 0 );
        assertEquals( origin.hashCode(), origin.hashCode(),
                origin + ".hashCode() did not match itself" );
        assertEquals( origin.hashCode(), Coordinate.ORIGIN.hashCode(),
                Coordinate.ORIGIN + ".hashCode() did not match " + origin + ".hashCode()." );
        // Check the hashCode() of Coordinates with various X and Y components.
        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                final Coordinate coord = new Coordinate( x, y );
                final Coordinate coordEq = new Coordinate( x, y );
                assertEquals( coord.hashCode(), coordEq.hashCode(),
                        coordEq + ".hashCode() did not match " + coord + ".hashCode()." );
            }
        }
    }

    /**
     * Tests that toString() works properly.
     */
    @Test
    public void testCoordinateToString () {
        // Check the toString() of various Coordinates.
        assertEquals( "(0, 0)", Coordinate.ORIGIN.toString(),
                Coordinate.ORIGIN + ".toString() was not correct." );
        final Coordinate coord1 = new Coordinate( 1, 4 );
        assertEquals( "(1, 4)", coord1.toString(),
                coord1 + ".toString() was wrong." );
        final Coordinate coord2 = new Coordinate( -3, 5 );
        assertEquals( "(-3, 5)", coord2.toString(),
                coord2 + ".toString() was wrong." );
        final Coordinate coord3 = new Coordinate( 4, -20 );
        assertEquals( "(4, -20)", coord3.toString(),
                coord3 + ".toString() was wrong." );
        final Coordinate coord4 = new Coordinate( -70, -3 );
        assertEquals( "(-70, -3)", coord4.toString(),
                coord4 + ".toString() was wrong." );
    }
}
