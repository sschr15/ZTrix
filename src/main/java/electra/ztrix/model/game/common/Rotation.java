package electra.ztrix.model.game.common;

public enum Rotation {
    R0, CW, R180, CCW;

    public Rotation plus ( Rotation rot ) {
        int ord = ordinal() + rot.ordinal();
        int mod = ord % values().length;
        return values()[mod];
    }
}
