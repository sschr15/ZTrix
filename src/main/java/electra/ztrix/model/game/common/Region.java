package electra.ztrix.model.game.common;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * An immutable interface containing a set of Coordinate positions.
 *
 * @author Electra
 */
public interface Region extends Iterable<Coordinate> {

    /**
     * Gets the Rectangle bounding box of the Region.
     *
     * @return the bounds.
     */
    @NotNull
    public Rectangle getBounds ();

    /**
     * Creates a new Region by translating this one.
     *
     * @param offset
     *            The offset to translate by, non-null.
     * @return The new, translated Region.
     */
    @Contract(value = "!null -> new", pure = true)
    public default Region translate ( Coordinate offset ) {
        if ( offset == null ) {
            throw new NullPointerException( "translate(offset) must be non-null." );
        }
        // Translate each position individually, adding to a SetRegion.
        List<Coordinate> positions = new ArrayList<>();
        for ( Coordinate position : this ) {
            Coordinate newPos = position.plus( offset );
            positions.add( newPos );
        }
        return new SetRegion( positions );
    }

    /**
     * Creates a new Region by rotating this one.
     *
     * @param direction
     *            The direction to rotate, non-null,
     * @param center
     *            The position to rotate around, non-null.
     *
     * @return The new, rotated Region.
     */
    @Contract(value = "!null, !null -> new", pure = true)
    public default Region rotate ( Rotation direction, Coordinate center ) {
        if ( direction == null ) {
            throw new NullPointerException( "rotate(direction) must be non-null." );
        }
        if ( center == null ) {
            throw new NullPointerException( "rotate(center) must be non-null." );
        }
        // Rotate each position individually, adding to a SetRegion.
        List<Coordinate> positions = new ArrayList<>();
        for ( Coordinate position : this ) {
            Coordinate newPos = position.rotate( direction, center );
            positions.add( newPos );
        }
        return new SetRegion( positions );
    }
}
