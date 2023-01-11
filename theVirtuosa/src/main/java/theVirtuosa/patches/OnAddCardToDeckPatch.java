package theVirtuosa.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.interfaces.OnAddToDeckCard;

/*
@SpirePatch2(
        clz = Soul.class,
        method = "obtain"
)

 */
@Deprecated
public class OnAddCardToDeckPatch {
    /*
    private static final Logger logger = LogManager.getLogger(OnAddCardToDeckPatch.class.getName());

    @SpirePrefixPatch
    public static void patch(AbstractCard card) {
        logger.info("OnAddCardToDeckPatch triggered");
        if (card instanceof OnAddToDeckCard)
        {
            ((OnAddToDeckCard)card).onAddToMasterDeck();
        }
    }

     */
}
