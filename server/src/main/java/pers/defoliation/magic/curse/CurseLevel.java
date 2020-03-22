package pers.defoliation.magic.curse;

public class CurseLevel<T extends Curse> {

    public final T curse;
    public final int level;

    public CurseLevel(T curse, int level) {
        this.curse = curse;
        this.level = level;
    }

}
