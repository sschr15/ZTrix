package electra.ztrix.model.game.common;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Rectangle class.
 *
 * @author Electra
 */
@SuppressWarnings({"SimplifiableAssertion", "EqualsWithItself", "ConstantConditions", "Convert2MethodRef"})
public class TestRectangle {

    /** The Rectangle used for testing. */
    public static Rectangle RECTANGLE = new Rectangle( -1, -1, 2, 2 );

    /** An Array of positions not inside the Rectangle. */
    public static Coordinate[] INVALID_POSITIONS = {
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
    public static Coordinate[] VALID_POSITIONS = {
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
    public static Region[] INVALID_REGIONS = {
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
        // Check NullPointerExceptions.
        assertThrows( NullPointerException.class,
                () -> new Rectangle( null, Coordinate.ORIGIN ),
                "Rectangle(null, ORIGIN) did not throw an Exception." );
        assertThrows( NullPointerException.class,
                () -> new Rectangle( Coordinate.ORIGIN, null ),
                "Rectangle(ORIGIN, null) did not throw an Exception." );
        // Check IllegalArgumentExceptions for various invalid sizes.
        for ( int i = -10; i <= 0; i++ ) {
            Coordinate coordX = new Coordinate( i, 1 );
            assertThrows( IllegalArgumentException.class,
                    () -> new Rectangle( Coordinate.ORIGIN, coordX ),
                    "Rectangle(ORIGIN, " + coordX + ") did not throw an Exception." );
            Coordinate coordY = new Coordinate( 1, i );
            assertThrows( IllegalArgumentException.class,
                    () -> new Rectangle( Coordinate.ORIGIN, coordY ),
                    "Rectangle(ORIGIN, " + coordY + ") did not throw an Exception." );

        }
    }

    /**
     * Tests that Rectangle getters work properly.
     */
    @Test
    public void testRectangleGetters () {
        // Check getters for Rectangles at various
        // positions.
        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                Coordinate min = new Coordinate( x, y );
                Coordinate max = new Coordinate( x + 5, y + 5 );
                Rectangle rect = new Rectangle( min, max );
                assertEquals( min, rect.getMinimum(),
                        "getMinimum() was wrong." );
                assertEquals( max, rect.getMaximum(),
                        "getMaximum() was wrong." );
                assertEquals( rect, rect.getBounds(),
                        "getBounds() was wrong." );
            }
        }
    }

    /**
     * Tests that contains() works properly.
     */
    @Test
    public void testRectangleContains () {
        // Check various positions inside the Rectangle.
        for ( Coordinate position : VALID_POSITIONS ) {
            assertTrue( RECTANGLE.contains( position ),
                    "contains(" + position + ") did not return True." );
        }
        // Check various positions outside the Rectangle.
        for ( Coordinate position : INVALID_POSITIONS ) {
            assertFalse( RECTANGLE.contains( position ),
                    "contains(" + position + ") did not return False." );
        }
    }

    /**
     * Tests that contains() cannot be called with a null position.
     */
    @Test
    public void testRectangleContainsNull () {
        // Check the NullPointerException.
        assertThrows( NullPointerException.class,
                () -> RECTANGLE.contains( null ),
                "contains(null) did not throw an Exception." );
    }

    /**
     * Tests that containsRegion() works properly.
     */
    @Test
    public void testRectangleContainsRegion () {
        // Check that the Rectangle contains itself.
        assertTrue( RECTANGLE.containsRegion( RECTANGLE ),
                "contains(" + RECTANGLE + ") did not return True." );
        // Check various 1x1 Rectangles at positions inside the Rectangle.
        for ( Coordinate minimum : VALID_POSITIONS ) {
            Coordinate size = new Coordinate( 1, 1 );
            Coordinate maximum = minimum.plus( size );
            Region region = new Rectangle( minimum, maximum );
            assertTrue( RECTANGLE.containsRegion( region ),
                    "containsRegion(" + region + ") did not return True." );
        }
        // Check various Regions not inside the Rectangle.
        for ( Region region : INVALID_REGIONS ) {
            assertFalse( RECTANGLE.containsRegion( region ),
                    "containsRegion(" + region + ") did not return False." );
        }
    }

    /**
     * Tests that containsRegion() cannot be called with a null Region.
     */
    @Test
    public void testRectangleContainsRegionNull () {
        // Check the NullPointerException.
        assertThrows( NullPointerException.class,
                () -> RECTANGLE.containsRegion( null ),
                "containsRegion(null) did not throw an Exception." );
    }

    /**
     * Tests that iterator() works properly.
     */
    @Test
    public void testRectangleIterator () {
        // Create an Iterator.
        Iterator<Coordinate> iter = RECTANGLE.iterator();
        // Check that the Iterator produces the correct positions.
        for ( Coordinate position : VALID_POSITIONS ) {
            assertTrue( iter.hasNext(),
                    "iterator().hasNext() did not return True." );
            assertEquals( position, iter.next(),
                    "iterator().next() did not return the correct position" + position + "." );
        }
        // Check that the Iterator is out of positions.
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
        // Check the equality of the Rectangle against various objects.
        assertTrue( RECTANGLE.equals( RECTANGLE ),
                RECTANGLE + ".equals(" + RECTANGLE + ") did not return True." );
        assertFalse( RECTANGLE.equals( null ),
                RECTANGLE + ".equals(null) did not return False." );
        assertFalse( RECTANGLE.equals( new Object() ),
                RECTANGLE + ".equals(Object) did not return False." );
        // Create Rectangles with various positions.
        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                Coordinate minimum = new Coordinate( x, y );
                Coordinate maximum = new Coordinate( x + 5, y + 5 );
                Rectangle rect = new Rectangle( minimum, maximum );
                // Check the equality of the Rectangle against other Rectangles.
                Rectangle rectEq = new Rectangle( x, y, x + 5, y + 5 );
                assertTrue( rect.equals( rectEq ),
                        rect + ".equals(" + rectEq + ") did not return True." );
                Rectangle rectMin = new Rectangle( x + 1, y + 1, x + 5, y + 5 );
                assertFalse( rect.equals( rectMin ),
                        rect + ".equals(" + rectMin + ") did not return False." );
                Rectangle rectMax = new Rectangle( x, y, x + 6, y + 6 );
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
        // Check the hashCode() of the Rectangle against itself.
        assertEquals( RECTANGLE.hashCode(), RECTANGLE.hashCode(),
                RECTANGLE + ".hashCode() did not match itself." );
        // Check the hashCode() of Rectangles with various positions.
        for ( int x = -10; x <= 10; x++ ) {
            for ( int y = -10; y <= 10; y++ ) {
                Coordinate minimum = new Coordinate( x, y );
                Coordinate maximum = new Coordinate( x + 5, y + 5 );
                Rectangle rect = new Rectangle( minimum, maximum );
                Rectangle rectEq = new Rectangle( x, y, x + 5, y + 5 );
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
        // Check the toString() of various Rectangles.
        Rectangle rect1 = new Rectangle( 0, 0, 1, 1 );
        assertEquals( "Rect[(0, 0) - (1, 1)]", rect1.toString(),
                rect1 + ".toString() was wrong." );
        Rectangle rect2 = new Rectangle( -2, -3, 4, 5 );
        assertEquals( "Rect[(-2, -3) - (4, 5)]", rect2.toString(),
                rect2 + ".toString() was wrong." );
        Rectangle rect3 = new Rectangle( 5, -14, 7, 8 );
        assertEquals( "Rect[(5, -14) - (7, 8)]", rect3.toString(),
                rect3 + ".toString() was wrong." );
        Rectangle rect4 = new Rectangle( -69, -4, 20, -3 );
        assertEquals( "Rect[(-69, -4) - (20, -3)]", rect4.toString(),
                rect4 + ".toString() was wrong." );
    }

    /**
     * Tests that a square Rectangle is equivalent to a subclass of Rectangle.
     */
    @Test
    public void testRectangleCanBeSquare () {
        Rectangle rect = new Rectangle( 0, 0, 1, 1 );

        class Square extends Rectangle {
            @SuppressWarnings("SuspiciousNameCombination")
            public Square (Coordinate min, int width ) {
                super( min, min.plus( new Coordinate( width, width ) ) );
            }

            public Square (int x, int y, int width ) {
                this( new Coordinate( x, y ), width );
            }
        }

        Square square = new Square( 0, 0, 1 );

        assertTrue( rect.equals( square ),
                "Rectangle.equals(Square) did not return True." );

        assertTrue( square.equals( rect ),
                "Square.equals(Rectangle) did not return True." );

        assertEquals( rect.hashCode(), square.hashCode(),
                "Rectangle.hashCode() did not match Square.hashCode()." );
    }
}
