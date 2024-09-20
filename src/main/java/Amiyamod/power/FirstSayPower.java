package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.ShadowCry;
import Amiyamod.cards.YCard.FirstSayA;
import Amiyamod.cards.Yzuzhou.ASay;
import Amiyamod.patches.LibraryTypeEnum;
import Amiyamod.patches.YCardTagClassEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

public class FirstSayPower extends AbstractPower {
    public static final String NAME = "FirstSayPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public FirstSayPower(int am) {
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
/*
    public void atStartOfTurn() {
        this.flash();
        for (int i = 0;i<this.amount;i++){
            this.addToBot(new MakeTempCardInHandAction(new ASay().makeCopy()));
        }
    }

 */
    /*
//打出攻击牌时给赤霄
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(YCardTagClassEnum.YZuZhou)) {
            this.flash();
            this.us(card);
        }
    }

     */

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            AbstractPlayer p = AbstractDungeon.player;
            ArrayList<AbstractCard> list = new ArrayList<>();
            for (AbstractCard C : p.hand.group){
                if (!C.hasTag(YCardTagClassEnum.YZuZhou)){
                    list.add(C);
                }
            }
            int cost = 0;
            int damage = 0;
            int B = 0;
            int M = 0;
            int H = 0;
            int D = 0;
            int mis = 0;
            int Y = 0;
            if (!list.isEmpty()){
                this.flash();
                for (AbstractCard c : list){
                    if (c.hasTag(YCardTagClassEnum.YCard)){
                        Y++;
                        cost += 3;
                    }
                    if (4 > c.cost && c.cost > 0){
                        cost += c.cost;
                    } else if (c.cost > 4) {
                        int x = Math.max((c.cost-c.costForTurn)/2,c.cost/3);
                        cost += x;
                    }
                    if (c.rarity == AbstractCard.CardRarity.UNCOMMON){
                        cost += 2;
                    } else if (c.rarity == AbstractCard.CardRarity.RARE) {
                        cost += 10;
                    }
                    if (c.damage>0){damage += c.damage;}
                    if (c.block>0){B += c.block;}
                    if (c.magicNumber>0){M += c.magicNumber;}
                    if (c.heal>0){H += c.heal;}
                    if (c.draw>0){D += c.draw;}
                    if (c.misc>0){mis += c.misc;}

                    p.hand.moveToExhaustPile(c);
                }
                AbstractCard c = new FirstSayA(damage,B,M,H,D,mis,cost,Y);
                this.addToBot(new MakeTempCardInHandAction(c));
            }

        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}
