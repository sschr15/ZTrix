package electra.ztrix.model.game.common;

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
    public final Coordinate minimum;
    /** The contianed Coordinate with the maximum X and Y, exclusive. */
    public final Coordinate maximum;

    /**
     * Creates a Rectangle with given minimum and maximum Coordinates.
     *
     * @param minimum
     *            The minimum Coordinate, inclusive, non-null.
     * @param maximum
     *            The maximum Coordinate, exclusive, non-null and greater than
     *            the minimum in both X and Y.
     */
    public Rectangle ( final Coordinate minimum, final Coordinate maximum ) {
        if ( minimum == null ) {
            throw new NullPointerException( "Rectangle(minimum) must be non-null." );
        }
        if ( maximum == null ) {
            throw new NullPointerException( "Rectangle(maximum) must be non-null." );
        }
        if ( maximum.x <= minimum.x ) {
            throw new IllegalArgumentException( "Rectangle(maximum) must be greater than the minimum in X." );
        }
        if ( maximum.y <= minimum.y ) {
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
    public Rectangle ( final int minX, final int minY, final int maxX, final int maxY ) {
        this( new Coordinate( minX, minY ), new Coordinate( maxX, maxY ) );
    }

    @Override
    public Rectangle getBounds () {
        return this;
    }

    /**
     * Checks whether the Rectangle contains a position.
     *
     * @param pos
     *            The position to check, non-null.
     * @return True if the Rectangle contains the position.
     */
    public boolean contains ( final Coordinate pos ) {
        if ( pos == null ) {
            throw new NullPointerException( "contains(pos) must be non-null." );
        }
        // Check against each edge of the Rectangle.
        if ( pos.x < minimum.x ) {
            return false;
        }
        if ( pos.y < minimum.y ) {
            return false;
        }
        if ( pos.x >= maximum.x ) {
            return false;
        }
        if ( pos.y >= maximum.y ) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether the Rectangle contains a Region.
     *
     * @param region
     *            The Region to check, non-null.
     * @return True if the Rectangle contains the Region.
     */
    public boolean containsRegion ( final Region region ) {
        if ( region == null ) {
            throw new NullPointerException( "containsRegion(region) must be non-null." );
        }
        // Check the Region's bounds against each edge of the Rectangle.
        final Rectangle bounds = region.getBounds();
        if ( bounds.minimum.x < minimum.x ) {
            return false;
        }
        if ( bounds.minimum.y < minimum.y ) {
            return false;
        }
        if ( bounds.maximum.x > maximum.x ) {
            return false;
        }
        if ( bounds.maximum.y > maximum.y ) {
            return false;
        }
        return true;
    }

    @Override
    public Iterator<Coordinate> iterator () {
        return new Iterator<Coordinate>() {
            /** The current X coordinate of the Iterator. */
            private int x = minimum.x;
            /** The current Y coordinate of the Iterator. */
            private int y = minimum.y;

            @Override
            public final boolean hasNext () {
                return y < maximum.y;
            }

            @Override
            public final Coordinate next () {
                if ( y >= maximum.y ) {
                    throw new NoSuchElementException();
                }
                // Create a Coordinate for the position.
                final Coordinate pos = new Coordinate( x, y );
                // Update the X and Y values for the next position.
                x++;
                if ( x >= maximum.x ) {
                    y++;
                    x = minimum.x;
                }
                // Return the position.
                return pos;
            }
        };
    }

    @Override
    public boolean equals ( final Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Rectangle rect = (Rectangle) obj;
        // Check equality by comparing the minimum and maximum Coordinates.
        return maximum.equals( rect.maximum ) && minimum.equals( rect.minimum );
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
