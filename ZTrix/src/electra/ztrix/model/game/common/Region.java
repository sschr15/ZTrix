package electra.ztrix.model.game.common;

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
     * @return the smallest Rectangle fully containing the Region.
     */
    public Rectangle getBounds ();

    public default Region translate ( Coordinate offset ) {
        List<Coordinate> positions = new ArrayList<Coordinate>();
        for ( Coordinate pos : this ) {
            Coordinate newPos = pos.plus( offset );
            positions.add( newPos );
        }
        return new SetRegion( positions );
    }

    public default Region rotate ( Rotation dir, Coordinate center ) {
        List<Coordinate> positions = new ArrayList<Coordinate>();
        for ( Coordinate pos : this ) {
            Coordinate newPos = pos.rotate( dir, center );
            positions.add( newPos );
        }
        return new SetRegion( positions );
    }
}
