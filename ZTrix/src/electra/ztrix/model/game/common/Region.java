package electra.ztrix.model.game.common;

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
}