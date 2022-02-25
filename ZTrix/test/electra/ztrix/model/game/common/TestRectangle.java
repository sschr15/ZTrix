package electra.ztrix.model.game.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests the Rectangle class.
 *
 * @author Electra
 */
public class TestRectangle {

    /** The Rectangle used for testing. */
    public static final Rectangle    RECTANGLE         = new Rectangle( -1, -1, 2, 2 );

    /** An Array of positions not inside the Rectangle. */
    public static final Coordinate[] INVALID_POSITIONS = {
            new Coordinate( 2, -1 ),
            new Coordinate( 2, 0 ),
            new Coordinate( 2, 1 ),
            new Coordinate( -1, 2 ),
            new Coordinate( 0, 2 ),
            new Coordinate( 1, 2 ),
            new Coordinate( 2, 2 ),
            new Coordinate( -2, -2 ),
            new Coordinate( 4, 5 ),
    };

    /** An Array of positions inside the Rectangle. */
    public static final Coordinate[] VALID_POSITIONS   = {
            new Coordinate( -1, -1 ),
            new Coordinate( 0, -1 ),
            new Coordinate( 1, -1 ),
            new Coordinate( -1, 0 ),
            new Coordinate( 0, 0 ),
            new Coordinate( 1, 0 ),
            new Coordinate( -1, 1 ),
            new Coordinate( 0, 1 ),
            new Coordinate( 1, 1 ),
    };

    /** An Array of Regions not inside the Rectangle. */
    public static final Region[]     INVALID_REGIONS   = {
            new Rectangle( -2, -2, 1, 1 ),
            new Rectangle( -2, 0, 0, 1 ),
            new Rectangle( 0, -2, 1, 0 ),
            new Rectangle( 0, 1, 1, 3 ),
            new Rectangle( 1, 0, 3, 1 ),
            new Rectangle( -4, -4, 5, 5 ),
    };

    /**
     * Tests that a Rectangle cannot be created with an invalid minimum or
     * maximum.
     */
    @Test
    public void testRectangleConstructorInvalid () {
        assertThrows( NullPointerException.class,
                () -> new Rectangle( null, Coordinate.ORIGIN ),
                "Rectangle(null, " + Coordinate.ORIGIN + ") did not throw an Exception." );
        assertThrows( NullPointerException.class,
                () -> new Rectangle( Coordinate.ORIGIN, null ),
                "Rectangle(" + Coordinate.ORIGIN + ", null) did not throw an Exception." );

        for ( int i = -10; i <= 0; i++ ) {
            final Coordinate coordX = new Coordinate( i, 1 );
            assertThrows( IllegalArgumentException.class,
                    () -> new Rectangle( Coordinate.ORIGIN, coordX ),
                    "Rectangle(" + Coordinate.ORIGIN + ", " + coordX + ") did not throw an Exception." );
            final Coordinate coordY = new Coordinate( 1, i );
            assertThrows( IllegalArgumentException.class,
                    () -> new Rectangle( Coordinate.ORIGIN, coordY ),
                    "Rectangle(" + Coordinate.ORIGIN + ", " + coordY + ") did not throw an Exception." );

        }
    }

    /**
     * Tests the Rectangle minimum and maximum fields.
     */
    @Test
    public void testRectangleMinMax () {
        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                final Coordinate min = new Coordinate( x, y );
                final Coordinate max = new Coordinate( x + 5, y + 5 );
                final Rectangle rect = new Rectangle( min, max );
                assertEquals( min, rect.minimum,
                        "Rectangle minimum was wrong." );
                assertEquals( max, rect.maximum,
                        "Rectangle maximum was wrong." );
            }
        }
    }

    /**
     * Tests that getBounds() works properly.
     */
    @Test
    public void testRectangleGetBounds () {
        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                final Coordinate min = new Coordinate( x, y );
                final Coordinate max = new Coordinate( x + 5, y + 5 );
                final Rectangle rect = new Rectangle( min, max );

                assertEquals( rect, rect.getBounds(),
                        "getBounds() was wrong." );
            }
        }
    }

    /**
     * Tests that contains() cannot be called with a null position.
     */
    @Test
    public void testRectangleContainsNull () {
        assertThrows( NullPointerException.class,
                () -> RECTANGLE.contains( null ),
                "contains(null) did not throw an Exception." );
    }

    /**
     * Tests that contains() works properly.
     */
    @Test
    public void testRectangleContains () {
        for ( final Coordinate pos : VALID_POSITIONS ) {
            assertTrue( RECTANGLE.contains( pos ),
                    "contains(" + pos + ") did not return True." );
        }

        for ( final Coordinate pos : INVALID_POSITIONS ) {
            assertFalse( RECTANGLE.contains( pos ),
                    "contains(" + pos + ") did not return False." );
        }
    }

    /**
     * Tests that containsRegion() cannot be called with a null Region.
     */
    @Test
    public void testRectangleContainsRegionNull () {
        assertThrows( NullPointerException.class,
                () -> RECTANGLE.containsRegion( null ),
                "containsRegion(null) did not throw an Exception." );
    }

    /**
     * Tests that containsRegion() works properly.
     */
    @Test
    public void testRectangleContainsRegion () {
        assertTrue( RECTANGLE.containsRegion( RECTANGLE ),
                "contains(" + RECTANGLE + ") did not return True." );

        for ( final Coordinate min : VALID_POSITIONS ) {
            final Coordinate max = new Coordinate( min.x + 1, min.y + 1 );
            final Region region = new Rectangle( min, max );
            assertTrue( RECTANGLE.containsRegion( region ),
                    "containsRegion(" + region + ") did not return True." );
        }

        for ( final Region region : INVALID_REGIONS ) {
            assertFalse( RECTANGLE.containsRegion( region ),
                    "containsRegion(" + region + ") did not return False." );
        }
    }

    /**
     * Tests that iterator() works properly.
     */
    @Test
    public void testRectangleIterator () {
        final Iterator<Coordinate> iter = RECTANGLE.iterator();

        for ( final Coordinate pos : VALID_POSITIONS ) {
            assertTrue( iter.hasNext(),
                    "iterator().hasNext() did not return True." );
            assertEquals( pos, iter.next(),
                    "iterator().next() did not return the correct position" + pos + "." );
        }

        assertFalse( iter.hasNext(),
                "iterator().hasNext() did not return False." );
        assertThrows( NoSuchElementException.class,
                () -> iter.next(),
                "iterator().next() did not throw an Exception." );
    }

    /**
     * Tests that equals() works properly.
     */
    @Test
    public void testRectangleEquals () {
        assertTrue( RECTANGLE.equals( RECTANGLE ),
                RECTANGLE + ".equals(" + RECTANGLE + ") did not return True." );
        assertFalse( RECTANGLE.equals( null ),
                RECTANGLE + ".equals(null) did not return False." );
        assertFalse( RECTANGLE.equals( new Object() ),
                RECTANGLE + ".equals(Object) did not return False." );

        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                final Coordinate min = new Coordinate( x, y );
                final Coordinate max = new Coordinate( x + 5, y + 5 );
                final Rectangle rect = new Rectangle( min, max );

                final Rectangle rectEq = new Rectangle( x, y, x + 5, y + 5 );
                assertTrue( rect.equals( rectEq ),
                        rect + ".equals(" + rectEq + ") did not return True." );
                final Rectangle rectMin = new Rectangle( x + 1, y + 1, x + 5, y + 5 );
                assertFalse( rect.equals( rectMin ),
                        rect + ".equals(" + rectMin + ") did not return False." );
                final Rectangle rectMax = new Rectangle( x, y, x + 6, y + 6 );
                assertFalse( rect.equals( rectMax ),
                        rect + ".equals(" + rectMax + ") did not return False." );
            }
        }
    }

    /**
     * Tests that hashCode() works properly.
     */
    @Test
    public void testCoordinateHashCode () {
        assertEquals( RECTANGLE.hashCode(), RECTANGLE.hashCode(),
                RECTANGLE + ".hashCode() did not match itself." );

        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                final Coordinate min = new Coordinate( x, y );
                final Coordinate max = new Coordinate( x + 5, y + 5 );
                final Rectangle rect = new Rectangle( min, max );

                final Rectangle rectEq = new Rectangle( x, y, x + 5, y + 5 );
                assertEquals( rect.hashCode(), rectEq.hashCode(),
                        rectEq + ".hashCode() did not match " + rect + ".hashCode()." );
            }
        }
    }

    /**
     * Tests that toString() works properly.
     */
    @Test
    public void testRectangleToString () {
        final Rectangle rect1 = new Rectangle( 0, 0, 1, 1 );
        assertEquals( "Rect[(0, 0) - (1, 1)]", rect1.toString(),
                rect1 + ".toString() was wrong." );

        final Rectangle rect2 = new Rectangle( -2, -3, 4, 5 );
        assertEquals( "Rect[(-2, -3) - (4, 5)]", rect2.toString(),
                rect2 + ".toString() was wrong." );

        final Rectangle rect3 = new Rectangle( 5, -14, 7, 8 );
        assertEquals( "Rect[(5, -14) - (7, 8)]", rect3.toString(),
                rect3 + ".toString() was wrong." );

        final Rectangle rect4 = new Rectangle( -69, -4, 20, -3 );
        assertEquals( "Rect[(-69, -4) - (20, -3)]", rect4.toString(),
                rect4 + ".toString() was wrong." );
    }
}
