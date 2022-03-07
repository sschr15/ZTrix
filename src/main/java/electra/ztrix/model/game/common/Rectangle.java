package electra.ztrix.model.game.common;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A rectangular Region with efficient containment checks. Can be used as a
 * bounding box to see if two Regions are close to intersecting.
 *
 * @author Electra
 */
public class Rectangle implements Region {
    /** The contianed Coordinate with the minimum X and Y, inclusive. */
    private final Coordinate minimum;
    /** The contianed Coordinate with the maximum X and Y, exclusive. */
    private final Coordinate maximum;

    /**
     * Creates a Rectangle with given minimum and maximum Coordinates.
     *
     * @param minimum
     *            The minimum Coordinate, inclusive, non-null.
     * @param maximum
     *            The maximum Coordinate, exclusive, non-null and greater than
     *            the minimum in both X and Y.
     */
    public Rectangle ( Coordinate minimum, Coordinate maximum ) {
        if ( minimum == null ) {
            throw new NullPointerException( "Rectangle(minimum) must be non-null." );
        }
        if ( maximum == null ) {
            throw new NullPointerException( "Rectangle(maximum) must be non-null." );
        }
        if ( maximum.x() <= minimum.x() ) {
            throw new IllegalArgumentException( "Rectangle(maximum) must be greater than the minimum in X." );
        }
        if ( maximum.y() <= minimum.y() ) {
            throw new IllegalArgumentException( "Rectangle(maximum) must be greater than the minimum in Y." );
        }
        this.minimum = minimum;
        this.maximum = maximum;
    }

    /**
     * Creates a Rectangle with given minimum and maximum X and Y components.
     *
     * @param minX
     *            The minimum X value, inclusive.
     * @param minY
     *            The minimum Y value, inclusive.
     * @param maxX
     *            The maximum X value, exclusive and greater than the minimum.
     * @param maxY
     *            The maximum Y value, exclusive and greater than the minimum.
     */
    public Rectangle ( int minX, int minY, int maxX, int maxY ) {
        this( new Coordinate( minX, minY ), new Coordinate( maxX, maxY ) );
    }

    /**
     * Gets the contianed Coordinate with the minimum X and Y, inclusive.
     *
     * @return the minimum Coordinate.
     */
    public Coordinate getMinimum () {
        return minimum;
    }

    /**
     * Gets the contianed Coordinate with the maximum X and Y, exclusive.
     *
     * @return the maximum Coordinate.
     */
    public Coordinate getMaximum () {
        return maximum;
    }

    @Override
    @NotNull
    public Rectangle getBounds () {
        return this;
    }

    @Override
    public Rectangle translate ( Coordinate offset ) {
        if ( offset == null ) {
            throw new NullPointerException( "translate(offset) must be non-null." );
        }
        // Translate the corners of the Rectangle.
        Coordinate newMinimum = minimum.plus( offset );
        Coordinate newMaximum = maximum.plus( offset );
        return new Rectangle( newMinimum, newMaximum );
    }

    @Override
    public Rectangle rotate ( Rotation direction, Coordinate center ) {
        if ( direction == null ) {
            throw new NullPointerException( "rotate(direction) must be non-null." );
        }
        if ( center == null ) {
            throw new NullPointerException( "rotate(center) must be non-null." );
        }
        // Rotate the corners of the Rectangle.
        Coordinate rotatedMinimum = minimum.rotate( direction, center );
        Coordinate rotatedMaximum = maximum.rotate( direction, center );
        // Rotation does not preserve corners, so fix with min() and max().
        int minX = Math.min( rotatedMinimum.x(), rotatedMaximum.x() );
        int minY = Math.min( rotatedMinimum.y(), rotatedMaximum.y() );
        int maxX = Math.max( rotatedMinimum.x(), rotatedMaximum.x() );
        int maxY = Math.max( rotatedMinimum.y(), rotatedMaximum.y() );
        return new Rectangle( minX, minY, maxX, maxY );
    }

    /**
     * Checks whether the Rectangle contains a position.
     *
     * @param position
     *            The position to check.
     * @return True if the Rectangle contains the position.
     */
    @Contract("null -> false")
    public boolean contains ( Coordinate position ) {
        if ( position == null ) {
            return false;
        }

        return (
            position.x() >= minimum.x() &&
            position.y() >= minimum.y() &&
            position.x() < maximum.x() &&
            position.y() < maximum.y()
            );
    }

    /**
     * Checks whether the Rectangle contains a Region.
     *
     * @param region
     *            The Region to check.
     * @return True if the Rectangle contains the Region.
     */
    @Contract("null -> false")
    public boolean containsRegion ( Region region ) {
        if ( region == null ) {
            return false;
        }
        // Check the Region's bounds against each edge of the Rectangle.
        Rectangle bounds = region.getBounds();

        return (
            bounds.minimum.x() >= minimum.x() &&
            bounds.minimum.y() >= minimum.y() &&
            bounds.maximum.x() <= maximum.x() &&
            bounds.maximum.y() <= maximum.y()
            );
    }

    @Override
    public Iterator<Coordinate> iterator () {
        return new Iterator<>() {
            /** The current X coordinate of the Iterator. */
            private int x = minimum.x();
            /** The current Y coordinate of the Iterator. */
            private int y = minimum.y();

            @Override
            public boolean hasNext () {
                return y < maximum.y();
            }

            @Override
            public Coordinate next () {
                if ( y >= maximum.y() ) {
                    throw new NoSuchElementException();
                }
                // Create a Coordinate for the position.
                Coordinate pos = new Coordinate( x, y );
                // Update the X and Y values for the next position.
                x++;
                if ( x >= maximum.x() ) {
                    y++;
                    x = minimum.x();
                }
                // Return the position.
                return pos;
            }
        };
    }

    @Override
    public boolean equals ( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }

        return
                // Check if it's a rectangle, and if so...
                obj instanceof Rectangle rect &&
                // ...check if the minimum and maximum Coordinates are equal.
                maximum.equals( rect.maximum ) && minimum.equals( rect.minimum );
    }

    @Override
    public int hashCode () {
        return Objects.hash( maximum, minimum );
    }

    @Override
    public String toString () {
        return "Rect[" + minimum + " - " + maximum + "]";
    }
}
