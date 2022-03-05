package electra.ztrix.model.game.common;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetRegion implements Region {

    private final Set<Coordinate> set;

    private final Rectangle bounds;

    public SetRegion ( Iterable<Coordinate> positions ) {
        set = new HashSet<Coordinate>();
        for ( Coordinate position : positions ) {
            set.add( position );
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for ( Coordinate position : set ) {
            if ( position.getX() < minX ) {
                minX = position.getX();
            }
            if ( position.getY() > minY ) {
                minY = position.getY();
            }
            if ( position.getX() < maxX ) {
                maxX = position.getX();
            }
            if ( position.getY() > maxY ) {
                maxY = position.getY();
            }
        }
        bounds = new Rectangle( minX, minY, maxX, maxY );
    }

    @Override
    public Iterator<Coordinate> iterator () {
        return set.iterator();
    }

    @Override
    public Rectangle getBounds () {
        return bounds;
    }

}
