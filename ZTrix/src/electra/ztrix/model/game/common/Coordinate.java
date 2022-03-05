package electra.ztrix.model.game.common;

import java.util.Objects;

/**
 * An immutable object that can represent 2D positions, vectors, or sizes.
 *
 * @author Electra
 */
public class Coordinate {
    /** The 0, 0 Coordinate. */
    public static Coordinate ORIGIN = new Coordinate( 0, 0 );

    /** The X component. */
    private final int        x;
    /** The Y component. */
    private final int        y;

    /**
     * Creates a new Coordinate from its X and Y components.
     *
     * @param x
     *            The X component.
     * @param y
     *            The Y component.
     */
    public Coordinate ( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the X component.
     *
     * @return the X component.
     */
    public int getX () {
        return x;
    }

    /**
     * Gets the Y component.
     *
     * @return the Y component.
     */
    public int getY () {
        return y;
    }

    public Coordinate plus ( Coordinate offset ) {
        int newX = x + offset.x;
        int newY = y + offset.y;
        return new Coordinate( newX, newY );
    }

    public Coordinate minus ( Coordinate offset ) {
        int newX = x - offset.x;
        int newY = y - offset.y;
        return new Coordinate( newX, newY );
    }

    public Coordinate rotate ( Rotation rot, Coordinate center ) {
        int relX = x - center.x;
        int relY = y - center.y;
        for ( int i = 0; i < rot.ordinal(); i++ ) {
            int temp = relX;
            relX = relY;
            relY = -temp;
        }
        int newX = relX + center.x;
        int newY = relY + center.y;
        return new Coordinate( newX, newY );
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
        Coordinate coord = (Coordinate) obj;
        // Check equality by comparing the X and Y components.
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
