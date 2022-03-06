package electra.ztrix.model.game.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the Rotation class.
 *
 * @author Electra
 */
public class TestRotation {

    /**
     * Tests that plus() works properly via properties.
     */
    @Test
    public void testRotationPlusProperties () {
        // Test the identity property.
        for ( Rotation rotation : Rotation.values() ) {
            Rotation sumIdentity = rotation.plus( Rotation.R0 );
            assertEquals( rotation, sumIdentity,
                    rotation + ".plus(R0) did not equal itself." );
        }
        // Test the commutative property.
        for ( Rotation rotation1 : Rotation.values() ) {
            for ( Rotation rotation2 : Rotation.values() ) {
                Rotation sum = rotation1.plus( rotation2 );
                Rotation sumCommutative = rotation2.plus( rotation1 );
                assertEquals( sum, sumCommutative,
                        rotation2 + ".plus(" + rotation1 + ") was not commutative." );
            }
        }
    }

    /**
     * Tests that plus() works properly via examples.
     */
    @Test
    public void testRotationPlusExamples () {
        // Test plus() with various Rotations.
        Rotation sumRR = Rotation.CW.plus( Rotation.CW );
        assertEquals( Rotation.R180, sumRR,
                "CW.plus(CW) was wrong." );
        Rotation sumLL = Rotation.CCW.plus( Rotation.CCW );
        assertEquals( Rotation.R180, sumLL,
                "CCW.plus(CCW) was wrong." );
        Rotation sum22 = Rotation.R180.plus( Rotation.R180 );
        assertEquals( Rotation.R0, sum22,
                "R180.plus(R180) was wrong." );
        Rotation sum2R = Rotation.R180.plus( Rotation.CW );
        assertEquals( Rotation.CCW, sum2R,
                "R180.plus(CW) was wrong." );
        Rotation sum2L = Rotation.R180.plus( Rotation.CCW );
        assertEquals( Rotation.CW, sum2L,
                "R180.plus(CCW) was wrong." );
    }

    /**
     * Tests that plus() cannot be called with a null Rotation.
     */
    @Test
    public void testRotationPlusNull () {
        // Check the NullPointerException.
        assertThrows( NullPointerException.class,
                () -> Rotation.R0.plus( null ),
                "plus(null) did not throw an Exception." );
    }
}
