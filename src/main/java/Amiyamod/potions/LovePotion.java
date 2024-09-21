package Amiyamod.potions;

import Amiyamod.Amiyamod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class LovePotion extends AbstractPotion {
    public static final String ID = Amiyamod.makeID("LovePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public LovePotion() {
        super(
                potionStrings.NAME,
                ID,
                PotionRarity.UNCOMMON,
                //PotionSize.EYE,
                PotionSize.HEART,
                PotionEffect.NONE,
                Color.PINK.cpy(),Color.PURPLE.cpy(),null

        );
        this.potency = this.getPotency();
        this.description = potionStrings.DESCRIPTIONS[0]+this.potency+potionStrings.DESCRIPTIONS[1];
        this.isThrown = false;
        this.labOutlineColor =  Amiyamod.ColorOut.cpy();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2],potionStrings.DESCRIPTIONS[3]));
    }

    public void use(AbstractCreature target) {
        Amiyamod.LinePower(this.potency);
    }

    public int getPotency(int ascensionLevel) {
        return 17;
    }

    public AbstractPotion makeCopy() {
        return new LovePotion();
    }
}
