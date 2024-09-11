package Amiyamod.patches;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class LittleLeOrb extends CustomOrb {
    public static final String NAME = "LittleLeOrb";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public LittleLeOrb() {
        super(POWER_ID,powerStrings.NAME,3, 6,powerStrings.DESCRIPTIONS[0], powerStrings.DESCRIPTIONS[1],
                "img/powers/Herpower.png");
        this.updateDescription();
    }

    public void updateDescription() {
        this.applyFocus();
        this.passiveDescription = powerStrings.DESCRIPTIONS[0] + this.passiveAmount + powerStrings.DESCRIPTIONS[1];
        this.evokeDescription = powerStrings.DESCRIPTIONS[2] + this.evokeAmount + powerStrings.DESCRIPTIONS[3];
        this.description = powerStrings.DESCRIPTIONS[4] + this.passiveDescription + powerStrings.DESCRIPTIONS[5] + this.evokeDescription;
    }

    @Override
    public void onEvoke() {
        Amiyamod.LinePower(this.evokeAmount);
    }
    public void onEndOfTurn() {
        Amiyamod.LinePower(this.passiveAmount);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new LittleLeOrb();
    }

    @Override
    public void playChannelSFX() {

    }
}
