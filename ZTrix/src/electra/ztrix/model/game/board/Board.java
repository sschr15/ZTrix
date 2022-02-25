package electra.ztrix.model.game.board;

import java.util.LinkedList;
import java.util.Queue;

import electra.ztrix.model.game.action.Revertable;
import electra.ztrix.model.game.common.Coordinate;
import electra.ztrix.model.game.common.Rectangle;
import electra.ztrix.model.game.common.Region;

/**
 * A grid of Minos that can be checked and modified.
 *
 * @author Electra
 */
public class Board {
    /** The Rectangle bounding box of the Board. */
    public final Rectangle bounds;
    /** The matrix storing the grid of Minos. */
    private final Mino[][] matrix;

    /**
     * Creates a new Board with a given size.
     *
     * @param size
     *            The size as a Coordinate, non-null with X and Y positive.
     */
    public Board ( final Coordinate size ) {
        this.bounds = new Rectangle( Coordinate.ORIGIN, size );
        this.matrix = new Mino[size.y][size.x];
    }

    /**
     * Gets the Mino at a particular position.
     *
     * @param pos
     *            The position, non-null and within the Board's bounds.
     * @return The Mino at the position.
     */
    public Mino getMinoAt ( final Coordinate pos ) {
        if ( pos == null ) {
            throw new NullPointerException( "getMinoAt(pos) must be non-null" );
        }
        if ( !bounds.contains( pos ) ) {
            throw new IndexOutOfBoundsException( "getMinoAt(pos) must be within the Board's bounds" );
        }

        return matrix[pos.y][pos.x];
    }

    /**
     * Gets whether a region is entirely empty and contained within the Board's
     * bounds. This can be used to check collision with the Board.
     *
     * @param region
     *            The Region to check, non-null.
     * @return True if the region is empty and within the Board's bounds
     */
    public boolean isRegionEmpty ( final Region region ) {
        if ( region == null ) {
            throw new NullPointerException( "isRegionEmpty(region) must be non-null" );
        }
        if ( !bounds.containsRegion( region ) ) {
            return false;
        }

        for ( final Coordinate pos : region ) {
            final Mino mino = matrix[pos.y][pos.x];
            if ( mino != null ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Sets the Mino at a particular position, revertably.
     *
     * @param pos
     *            The position, non-null and within the Board's bounds.
     * @param mino
     *            The Mino to set, non-null.
     * @return The Revertable used to revert this method.
     */
    public Revertable setMinoAt ( final Coordinate pos, final Mino mino ) {
        if ( mino == null ) {
            throw new NullPointerException( "setMinoAt(mino) must be non-null" );
        }

        final Mino prev = getMinoAt( pos );
        matrix[pos.y][pos.x] = mino;

        return new Revertable() {
            @Override
            public void revert () {
                matrix[pos.y][pos.x] = prev;
            }
        };
    }

    /**
     * Sets an entire Region of the Board to a particular Mino, revertably.
     *
     * @param region
     *            The Region to check, non-null and within the Board's bounds.
     * @param mino
     *            The Mino to set, non-null.
     * @return The Revertable used to revert this method.
     */
    public Revertable setRegion ( final Region region, final Mino mino ) {
        if ( region == null ) {
            throw new NullPointerException( "setRegion(region) must be non-null" );
        }
        if ( !bounds.containsRegion( region ) ) {
            throw new IndexOutOfBoundsException( "setRegion(region) must be within the Board's bounds." );
        }
        if ( mino == null ) {
            throw new NullPointerException( "setRegion(mino) must be non-null" );
        }

        final Queue<Mino> prevs = new LinkedList<Mino>();

        for ( final Coordinate pos : region ) {
            final Mino prev = matrix[pos.y][pos.x];
            prevs.add( prev );
            matrix[pos.y][pos.x] = mino;
        }

        return new Revertable() {
            @Override
            public void revert () {
                for ( final Coordinate pos : region ) {
                    final Mino prev = prevs.remove();
                    matrix[pos.y][pos.x] = prev;
                }
            }
        };
    }
}
