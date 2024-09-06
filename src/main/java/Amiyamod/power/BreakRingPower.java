package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BreakRingPower extends AbstractPower {
    public static final String NAME = "BreakRingPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public BreakRingPower(int am) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = am;
        this.type = PowerType.BUFF;
        // 添加图标
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    //回合结束时退出
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(YCardTagClassEnum.YCard)) {
            this.flash();
            if (this.amount < 1 ) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner,this.ID, 1));
            }
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            AbstractMonster m = null;
            if (action.target != null && !action.target.isPlayer) {
                tmp.calculateCardDamage((AbstractMonster)action.target);
                m = (AbstractMonster) action.target;
            }
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, tmp.energyOnUse, true, true), true);
        }
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+DESCRIPTIONS[1];
    }
}
