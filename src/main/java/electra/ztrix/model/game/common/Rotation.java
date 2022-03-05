package electra.ztrix.model.game.common;

/**
 * An enumeration representing absolute or relative 90-degree Rotations.
 *
 * @author Electra
 */
public enum Rotation {
    /** The 0 degree rotation. */
    R0,
    /** The 90 degree clockwise rotation. */
    CW,
    /** The 180 degree rotation. */
    R180,
    /** The 90 degree counterclockwise rotation. */
    CCW;

    /**
     * Sums another Rotation with this one.
     *
     * @param rotation
     *            The Rotation to add.
     * @return The sum of the Rotations.
     */
    public Rotation plus ( Rotation rotation ) {
        int ord = ordinal() + rotation.ordinal();
        int mod = ord % values().length;
        return values()[mod];
    }
}
