package electra.ztrix.model.game.piece;

import electra.ztrix.model.game.action.Revertable;
import electra.ztrix.model.game.board.Board;
import electra.ztrix.model.game.common.Coordinate;
import electra.ztrix.model.game.common.Region;
import electra.ztrix.model.game.common.Rotation;

public class ActivePiece {

    private final PieceType type;
    private final Board board;
    private Coordinate position;
    private Rotation rotation;

    public ActivePiece ( PieceType type, Board board,
            Coordinate position, Rotation rotation ) {
        this.type = type;
        this.board = board;
        this.position = position;
        this.rotation = rotation;
    }

    public PieceType getPieceType () {
        return type;
    }

    public Board getBoard () {
        return board;
    }

    public Coordinate getPosition () {
        return position;
    }

    public Rotation getRotation () {
        return rotation;
    }

    public Region getRegion () {
        Region shape = type.getShape( rotation );
        return shape.translate( position );
    }

    public Revertable setPosition ( Coordinate pos ) {
        if ( pos == null ) {
            throw new NullPointerException( "setPosition(pos) must not be null." );
        }

        Coordinate prevPos = position;
        position = pos;

        return new Revertable() {

            @Override
            public void revert () {
                position = prevPos;
            }
        };
    }

    public Revertable setRotation ( Rotation rot ) {
        if ( rot == null ) {
            throw new NullPointerException( "setRotation(rot) must not be null." );
        }

        Rotation prevRot = rotation;
        rotation = rot;

        return new Revertable() {

            @Override
            public void revert () {
                rotation = prevRot;
            }
        };
    }

    public boolean isNotColliding () {
        Region region = getRegion();
        return board.isRegionEmpty( region );
    }

    public Revertable move ( Coordinate offset ) {
        Coordinate newPos = position.plus( offset );
        Revertable revertable = setPosition( newPos );

        if ( isNotColliding() ) {
            return revertable;
        }

        revertable.revert();
        return null;
    }

    public Revertable rotate ( Rotation direction ) {
        Coordinate prevPos = position;
        Rotation prevRot = rotation;

        Revertable revertable = new Revertable() {

            @Override
            public void revert () {
                position = prevPos;
                rotation = prevRot;
            }
        };

        rotation = prevRot.plus( direction );
        if ( isNotColliding() ) {
            return revertable;
        }

        Iterable<Coordinate> kicks = type.getKicks( prevRot, rotation );
        for ( Coordinate kick : kicks ) {
            position = prevPos.plus( kick );
            if ( isNotColliding() ) {
                return revertable;
            }
        }

        revertable.revert();
        return null;
    }

    public Revertable place () {
        Coordinate prevPos = position;
        Coordinate down = new Coordinate( 0, -1 );
        while ( move( down ) != null ) {
            // Keep moving down til you hit the floor.
        }

        Region region = getRegion();
        Revertable revertable = board.setRegion( region, type.getMino() );

        return new Revertable() {

            @Override
            public void revert () {
                revertable.revert();
                position = prevPos;
            }
        };
    }
}
