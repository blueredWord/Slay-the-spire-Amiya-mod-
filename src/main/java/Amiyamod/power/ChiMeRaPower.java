package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.relics.CYrelic;
import Amiyamod.relics.Yill;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ChiMeRaPower extends AbstractPower {
    public static final String NAME = "ChiMeRaPower";
    public static final String POWERID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWERID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public ChiMeRaPower(int n) {
        this.name = powerStrings.NAME;
        this.ID = POWERID;
        this.owner = AbstractDungeon.player;
        this.amount = n;
        this.type = PowerType.BUFF;

        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);

        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = this.amount + DESCRIPTIONS[0];
    }
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return (float) (damage*1.5);
    }
    @Override
    public void onRemove() {
        this.addToBot(new DiscardAction(this.owner, this.owner, AbstractDungeon.player.hand.size(), true));
        this.addToBot(new PressEndTurnButtonAction());
    }
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        card.damageTypeForTurn = DamageInfo.DamageType.HP_LOSS;
    }
    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }
}
