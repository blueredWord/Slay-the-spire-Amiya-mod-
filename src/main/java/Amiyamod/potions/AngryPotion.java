package Amiyamod.potions;

import Amiyamod.Amiyamod;
import Amiyamod.power.YSayPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class AngryPotion extends AbstractPotion {
    public static final String ID = Amiyamod.makeID("AngryPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public AngryPotion() {
        super(
                potionStrings.NAME,
                ID,
                PotionRarity.COMMON,
                //PotionSize.EYE,
                PotionSize.BOLT,
                PotionEffect.NONE,
                Color.RED, Color.BLACK,null

        );
        this.potency = this.getPotency();
        this.description = potionStrings.DESCRIPTIONS[0];
        this.isThrown = false;
        this.labOutlineColor = Amiyamod.ColorOut.cpy();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[1],potionStrings.DESCRIPTIONS[2]));
    }

    public void use(AbstractCreature target) {
        Amiyamod.Sword(true);
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new AngryPotion();
    }
}
