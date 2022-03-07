package electra.ztrix.model.game.common;

import org.jetbrains.annotations.Contract;

/**
 * An immutable object that can represent 2D positions, vectors, or sizes.
 *
 * @param x The X component.
 * @param y The Y component.
 * @author Electra
 */
public record Coordinate(int x, int y) {
    /**
     * The 0, 0 Coordinate.
     */
    public static final Coordinate ORIGIN = new Coordinate(0, 0);

    /**
     * Creates a new Coordinate by adding an offset to this one.
     *
     * @param offset The Coordinate to add, non-null.
     * @return The new Coordinate.
     */
    @Contract(value = "!null -> new", pure = true)
    public Coordinate plus(Coordinate offset) {
        if (offset == null) {
            throw new NullPointerException("plus(offset) must be non-null.");
        }
        // Calculate the new Coordinate.
        int newX = x + offset.x;
        int newY = y + offset.y;
        return new Coordinate(newX, newY);
    }

    /**
     * Creates a new Coordinate by subtracting an offset from this one.
     *
     * @param offset The Coordinate to subtract, non-null.
     * @return The new Coordinate.
     */
    @Contract(value = "!null -> new", pure = true)
    public Coordinate minus(Coordinate offset) {
        if (offset == null) {
            throw new NullPointerException("minus(offset) must be non-null.");
        }
        // Calculate the new Coordinate.
        int newX = x - offset.x;
        int newY = y - offset.y;
        return new Coordinate(newX, newY);
    }

    /**
     * Creates a new Coordinate by negating this one.
     *
     * @return The new, negated Coordinate.
     */
    public Coordinate negate () {
        return ORIGIN.minus( this );
    }

    /**
     * Creates a new Coordinate by rotating this one.
     *
     * @param direction The direction to rotate, non-null.
     * @param center    The position to rotate around, non-null.
     * @return The new, rotated Coordinate.
     */
    @Contract(value = "!null, !null -> new", pure = true)
    public Coordinate rotate(Rotation direction, Coordinate center) {
        if (direction == null) {
            throw new NullPointerException("rotate(direction) must be non-null.");
        }
        if (center == null) {
            throw new NullPointerException("rotate(center) must be non-null.");
        }
        // Calculate the relative offset from the center.
        int relX = x - center.x;
        int relY = y - center.y;
        // Rotate the relative offset for each 90 degree rotation.
        for (int i = 0; i < direction.ordinal(); i++) {
            int temp = relX;
            //noinspection SuspiciousNameCombination
            relX = relY;
            relY = -temp;
        }
        // Calculate the new absolute Coordinate from the relative offset.
        int newX = relX + center.x;
        int newY = relY + center.y;
        return new Coordinate(newX, newY);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // equals() and hashCode() are provided automatically because of the record syntax.
}
