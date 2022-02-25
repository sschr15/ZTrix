package electra.ztrix.model.game.action;

/**
 * Holds a method used to revert some modification to an object.
 *
 * @author Electra
 */
public interface Revertible {
    /**
     * Reverts the effects of the method the Revertible is returned from.
     */
    public void revert ();
}
