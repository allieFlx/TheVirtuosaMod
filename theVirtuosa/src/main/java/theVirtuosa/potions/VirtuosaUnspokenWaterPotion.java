package theVirtuosa.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVirtuosa.TheVirtuosa;

public class VirtuosaUnspokenWaterPotion extends CustomPotion {

    public static final String POTION_ID = TheVirtuosa.makeID("VirtuosaUnspokenWaterPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public VirtuosaUnspokenWaterPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.EYE, PotionColor.NONE);

        potency = getPotency();
        description = DESCRIPTIONS[0];
        isThrown = false;
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(target));
        }
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new VirtuosaUnspokenWaterPotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 3;
    }

    public void upgradePotion()
    {
      potency += 1;
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
}
