package electra.ztrix.model.game.common;

import java.util.Objects;

/**
 * A 2D Coordinate to represent positions, vectors, or sizes.
 *
 * @author Electra
 */
public class Coordinate {
    public static final Coordinate ORIGIN = new Coordinate( 0, 0 );

    /** The X component. */
    public final int               x;
    /** The Y component. */
    public final int               y;

    /**
     * Creates a new Coordinate from its X and Y components.
     *
     * @param x
     *            The X component.
     * @param y
     *            The Y component.
     */
    public Coordinate ( final int x, final int y ) {
        this.x = x;
        this.y = y;
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
        final Coordinate coord = (Coordinate) obj;

        return ( x == coord.x ) && ( y == coord.y );
    }

    @Override
    public int hashCode () {
        return Objects.hash( x, y );
    }

    @Override
    public String toString () {
        return "(" + x + ", " + y + ")";
    }
}
