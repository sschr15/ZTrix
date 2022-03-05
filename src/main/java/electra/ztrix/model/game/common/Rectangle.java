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
        if ( maximum.getX() <= minimum.getX() ) {
            throw new IllegalArgumentException( "Rectangle(maximum) must be greater than the minimum in X." );
        }
        if ( maximum.getY() <= minimum.getY() ) {
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
     * @return the minimum Coordinate, inclusive.
     */
    public Coordinate getMinimum () {
        return minimum;
    }

    /**
     * Gets the contianed Coordinate with the maximum X and Y, exclusive.
     *
     * @return the maximum Coordinate, exclusive.
     */
    public Coordinate getMaximum () {
        return maximum;
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
    public boolean contains ( Coordinate pos ) {
        if ( pos == null ) {
            throw new NullPointerException( "contains(pos) must be non-null." );
        }
        // Check against each edge of the Rectangle.
        if ( pos.getX() < minimum.getX() ) {
            return false;
        }
        if ( pos.getY() < minimum.getY() ) {
            return false;
        }
        if ( pos.getX() >= maximum.getX() ) {
            return false;
        }
        if ( pos.getY() >= maximum.getY() ) {
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
    public boolean containsRegion ( Region region ) {
        if ( region == null ) {
            throw new NullPointerException( "containsRegion(region) must be non-null." );
        }
        // Check the Region's bounds against each edge of the Rectangle.
        Rectangle bounds = region.getBounds();
        if ( bounds.minimum.getX() < minimum.getX() ) {
            return false;
        }
        if ( bounds.minimum.getY() < minimum.getY() ) {
            return false;
        }
        if ( bounds.maximum.getX() > maximum.getX() ) {
            return false;
        }
        if ( bounds.maximum.getY() > maximum.getY() ) {
            return false;
        }
        return true;
    }

    @Override
    public Iterator<Coordinate> iterator () {
        return new Iterator<Coordinate>() {
            /** The current X coordinate of the Iterator. */
            private int x = minimum.getX();
            /** The current Y coordinate of the Iterator. */
            private int y = minimum.getY();

            @Override
            public boolean hasNext () {
                return y < maximum.getY();
            }

            @Override
            public Coordinate next () {
                if ( y >= maximum.getY() ) {
                    throw new NoSuchElementException();
                }
                // Create a Coordinate for the position.
                Coordinate pos = new Coordinate( x, y );
                // Update the X and Y values for the next position.
                x++;
                if ( x >= maximum.getX() ) {
                    y++;
                    x = minimum.getX();
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
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        Rectangle rect = (Rectangle) obj;
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