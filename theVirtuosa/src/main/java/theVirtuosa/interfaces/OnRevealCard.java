package theVirtuosa.interfaces;

public interface OnRevealCard {
    // actions added in onRevealed should be added to the top of the action queue as default
    void onRevealed();

    default boolean isMovedOnReveal(){ return false; }
}
