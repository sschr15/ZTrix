package electra.ztrix.model.game.action;

/**
 * Holds a method used to revert some modification to an object.
 *
 * @author Electra
 */
public interface Revertable {
    /**
     * Reverts the effects of the method the Revertable is returned from.
     */
    public void revert ();
}
