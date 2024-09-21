package Amiyamod.potions;

import Amiyamod.Amiyamod;
import Amiyamod.power.YBBSPower;
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

import java.util.ArrayList;

public class YPotion extends AbstractPotion {
    public static final String ID = Amiyamod.makeID("YPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public YPotion() {
        super(
                potionStrings.NAME,
                ID,
                PotionRarity.RARE,
                //PotionSize.EYE,
                PotionSize.T, PotionEffect.NONE,
                Color.ORANGE.cpy(),
                Color.RED.cpy(),
                null

        );
        this.potency = this.getPotency();
        this.description = potionStrings.DESCRIPTIONS[0];
        this.isThrown = false;
        this.labOutlineColor = Amiyamod.ColorOut.cpy();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[1],potionStrings.DESCRIPTIONS[2]));
    }

    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p,p,new YSayPower()));
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new YPotion();
    }
}
