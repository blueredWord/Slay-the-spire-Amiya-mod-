package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.patches.LibraryTypeEnum;
import Amiyamod.patches.YCardTagClassEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;

public class YBBSPower extends AbstractPower {
    public static final String NAME = "YBBSPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public YBBSPower(int n) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = n;
        this.type = PowerType.BUFF;
        // 添加图标
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        //this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
        this.description = DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[3]+this.amount*6+DESCRIPTIONS[4] ;
        //this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1]+(this.amount*6)+DESCRIPTIONS[2];
    }
/*
    public void onCardDraw(AbstractCard card) {
        if (card.hasTag(YCardTagClassEnum.YZuZhou)){
            CardGroup GG = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED) ;
            for (AbstractCard ca : Amiyamod.YZcard) {
                if (!ca.hasTag(AbstractCard.CardTags.HEALING)) {
                    GG.addToBottom(ca);
                }
            }
            AbstractCard c = GG.getRandomCard(true).makeCopy();
            this.addToBot(new MakeTempCardInHandAction(c));
        }
    }
 */
    //回合结束时退出

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            Amiyamod.addY(this.amount);
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction((AbstractPlayer)this.owner,this.amount*6, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE)
            );
        }
    }

}
