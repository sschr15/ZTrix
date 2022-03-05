package electra.ztrix.model.game.piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import electra.ztrix.model.game.board.Board;
import electra.ztrix.model.game.board.Mino;
import electra.ztrix.model.game.common.Coordinate;
import electra.ztrix.model.game.common.Region;
import electra.ztrix.model.game.common.Rotation;
import electra.ztrix.model.game.common.SetRegion;

public class PieceType {

    private static final Map<Rotation, Coordinate> BASE_OFFSET_TABLE_JLSTZ = Map.of(
            Rotation.R0, Coordinate.ORIGIN,
            Rotation.CW, Coordinate.ORIGIN,
            Rotation.R180, Coordinate.ORIGIN,
            Rotation.CCW, Coordinate.ORIGIN );

    private static final Map<Rotation, Coordinate> BASE_OFFSET_TABLE_IO = Map.of(
            Rotation.R0, new Coordinate( 0, 0 ),
            Rotation.CW, new Coordinate( 1, 0 ),
            Rotation.R180, new Coordinate( 1, -1 ),
            Rotation.CCW, new Coordinate( 0, -1 ) );

    private static final Iterable<Map<Rotation, Coordinate>> KICK_OFFSET_TABLE_JLSTZ = List.of(
            Map.of(
                    Rotation.R0, new Coordinate( 0, 0 ),
                    Rotation.CW, new Coordinate( 1, 0 ),
                    Rotation.R180, new Coordinate( 0, 0 ),
                    Rotation.CCW, new Coordinate( -1, 0 ) ),
            Map.of(
                    Rotation.R0, new Coordinate( 0, 0 ),
                    Rotation.CW, new Coordinate( 1, -1 ),
                    Rotation.R180, new Coordinate( 0, 0 ),
                    Rotation.CCW, new Coordinate( -1, -1 ) ),
            Map.of(
                    Rotation.R0, new Coordinate( 0, 0 ),
                    Rotation.CW, new Coordinate( 0, 2 ),
                    Rotation.R180, new Coordinate( 0, 0 ),
                    Rotation.CCW, new Coordinate( 0, 2 ) ),
            Map.of(
                    Rotation.R0, new Coordinate( 0, 0 ),
                    Rotation.CW, new Coordinate( 1, 2 ),
                    Rotation.R180, new Coordinate( 0, 0 ),
                    Rotation.CCW, new Coordinate( -1, 2 ) ) );

    private static final Iterable<Map<Rotation, Coordinate>> KICK_OFFSET_TABLE_IO = List.of(
            Map.of(
                    Rotation.R0, new Coordinate( -1, 0 ),
                    Rotation.CW, new Coordinate( 1, 0 ),
                    Rotation.R180, new Coordinate( 2, 0 ),
                    Rotation.CCW, new Coordinate( 0, 0 ) ),
            Map.of(
                    Rotation.R0, new Coordinate( 2, 0 ),
                    Rotation.CW, new Coordinate( 1, 0 ),
                    Rotation.R180, new Coordinate( -1, 0 ),
                    Rotation.CCW, new Coordinate( 0, 0 ) ),
            Map.of(
                    Rotation.R0, new Coordinate( -1, 0 ),
                    Rotation.CW, new Coordinate( 1, 1 ),
                    Rotation.R180, new Coordinate( 2, -1 ),
                    Rotation.CCW, new Coordinate( 0, -2 ) ),
            Map.of(
                    Rotation.R0, new Coordinate( 2, 0 ),
                    Rotation.CW, new Coordinate( 1, -2 ),
                    Rotation.R180, new Coordinate( -1, 1 ),
                    Rotation.CCW, new Coordinate( 0, 1 ) ) );

    public static final PieceType J_PIECE = new PieceType(
            new Mino(),
            new SetRegion( List.of(
                    new Coordinate( 0, -1 ),
                    new Coordinate( 0, 0 ),
                    new Coordinate( 0, 1 ),
                    new Coordinate( 1, -1 ) ) ),
            BASE_OFFSET_TABLE_JLSTZ,
            KICK_OFFSET_TABLE_JLSTZ );

    public static final PieceType L_PIECE = new PieceType(
            new Mino(),
            new SetRegion( List.of(
                    new Coordinate( 0, -1 ),
                    new Coordinate( 0, 0 ),
                    new Coordinate( 0, 1 ),
                    new Coordinate( 1, 1 ) ) ),
            BASE_OFFSET_TABLE_JLSTZ,
            KICK_OFFSET_TABLE_JLSTZ );

    public static final PieceType S_PIECE = new PieceType(
            new Mino(),
            new SetRegion( List.of(
                    new Coordinate( -1, 0 ),
                    new Coordinate( 0, 0 ),
                    new Coordinate( 1, 0 ),
                    new Coordinate( 1, 1 ) ) ),
            BASE_OFFSET_TABLE_JLSTZ,
            KICK_OFFSET_TABLE_JLSTZ );

    public static final PieceType T_PIECE = new PieceType(
            new Mino(),
            new SetRegion( List.of(
                    new Coordinate( 0, -1 ),
                    new Coordinate( 0, 0 ),
                    new Coordinate( 0, 1 ),
                    new Coordinate( 1, 0 ) ) ),
            BASE_OFFSET_TABLE_JLSTZ,
            KICK_OFFSET_TABLE_JLSTZ );

    public static final PieceType Z_PIECE = new PieceType(
            new Mino(),
            new SetRegion( List.of(
                    new Coordinate( 0, 0 ),
                    new Coordinate( 0, 1 ),
                    new Coordinate( 1, -1 ),
                    new Coordinate( 1, 0 ) ) ),
            BASE_OFFSET_TABLE_JLSTZ,
            KICK_OFFSET_TABLE_JLSTZ );

    public static final PieceType I_PIECE = new PieceType(
            new Mino(),
            new SetRegion( List.of(
                    new Coordinate( 0, -1 ),
                    new Coordinate( 0, 0 ),
                    new Coordinate( 0, 1 ),
                    new Coordinate( 0, 2 ) ) ),
            BASE_OFFSET_TABLE_IO,
            KICK_OFFSET_TABLE_IO );

    public static final PieceType O_PIECE = new PieceType(
            new Mino(),
            new SetRegion( List.of(
                    new Coordinate( -1, 0 ),
                    new Coordinate( -1, 1 ),
                    new Coordinate( 0, 0 ),
                    new Coordinate( 0, 1 ) ) ),
            BASE_OFFSET_TABLE_IO,
            KICK_OFFSET_TABLE_IO );

    private final Mino mino;
    private final Map<Rotation, Region> shapes;
    private final Map<Rotation, Map<Rotation, Iterable<Coordinate>>> kickTable;

    private Map<Rotation, Region> generateShapes ( Region shape,
            Map<Rotation, Coordinate> baseOffsetTable ) {
        Map<Rotation, Region> shapes = new HashMap<>();
        for ( Rotation rotation : Rotation.values() ) {
            Region rotatedShape = shape.rotate( rotation, Coordinate.ORIGIN );
            Coordinate baseOffset = baseOffsetTable.get( rotation );
            Region offsetShape = rotatedShape.translate( baseOffset );
            shapes.put( rotation, offsetShape );
        }
        return shapes;
    }

    private Map<Rotation, Map<Rotation, Iterable<Coordinate>>> generateKickTable (
            Iterable<Map<Rotation, Coordinate>> kickOffsetTable ) {
        Map<Rotation, Map<Rotation, Iterable<Coordinate>>> kickTable = new HashMap<>();
        for ( Rotation oldRotation : Rotation.values() ) {
            Map<Rotation, Iterable<Coordinate>> rotationKicks = new HashMap<>();
            for ( Rotation newRotation : Rotation.values() ) {
                List<Coordinate> kicks = new ArrayList<>();
                for ( Map<Rotation, Coordinate> offsetTable : kickOffsetTable ) {
                    Coordinate startOffset = offsetTable.get( oldRotation );
                    Coordinate endOffset = offsetTable.get( newRotation );
                    Coordinate kick = startOffset.minus( endOffset );
                    kicks.add( kick );
                }
                rotationKicks.put( newRotation, kicks );
            }
            kickTable.put( oldRotation, rotationKicks );
        }
        return kickTable;
    }

    public PieceType ( Mino mino, Region shape,
            Map<Rotation, Coordinate> baseOffsetTable,
            Iterable<Map<Rotation, Coordinate>> kickOffsetTable ) {
        this.mino = mino;
        shapes = generateShapes( shape, baseOffsetTable );
        kickTable = generateKickTable( kickOffsetTable );
    }

    public Mino getMino () {
        return mino;
    }

    public Region getShape ( Rotation rotation ) {
        return shapes.get( rotation );
    }

    public Iterable<Coordinate> getKicks ( Rotation oldRotation, Rotation newRotation ) {
        Map<Rotation, Iterable<Coordinate>> rotationKicks = kickTable.get( oldRotation );
        return rotationKicks.get( newRotation );
    }

    public ActivePiece createActivePiece ( Board board, Coordinate position, Rotation rotation ) {
        return new ActivePiece( this, board, position, rotation );
    }
}
