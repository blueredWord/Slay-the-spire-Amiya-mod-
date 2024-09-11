package Amiyamod.power;

import Amiyamod.Amiyamod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

public class KingSayPower extends AbstractPower {
    public static final String NAME = "KingSayPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public boolean yes;
    private int min = 0;
    public KingSayPower(int n) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = n;
        this.min = 3;
        this.type = PowerType.BUFF;
        // 添加图标
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+this.min+DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2];
    }

    //回合结束时退出
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            AbstractPlayer p = AbstractDungeon.player;
            int i = p.hand.size();
            if (i>0){
                if (Settings.FAST_MODE) {
                    this.addToBot(new ExhaustAction(i, true, true, false, Settings.ACTION_DUR_XFAST));
                    //this.addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, i/2*this.amount)));
                    //this.addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, i/2*this.amount)));
                } else {
                    this.addToBot(new ExhaustAction(i, true, true));
                    //this.addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, i/2*this.amount)));
                    //this.addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, i/2*this.amount)));
                }
                this.yes = (i >= this.min);
                if (yes){
                    this.flash();
                    //this.addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p,this.amount)));
                }
            }
        }
    }
    public void atStartOfTurn() {
        this.flash();
        if (yes){
            for (int i = 0;i<this.amount;i++){
                CardGroup GG = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED) ;
                for (AbstractCard ca : Amiyamod.YZcard) {
                    if (!ca.hasTag(AbstractCard.CardTags.HEALING)) {
                        GG.addToBottom(ca);
                    }
                }
                AbstractCard c = GG.getRandomCard(true).makeCopy();
                if (c.canUpgrade()){
                    c.upgrade();
                }
                this.addToBot(new MakeTempCardInHandAction(c));
            }
        }
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }
}
