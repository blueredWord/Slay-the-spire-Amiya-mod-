package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ChoseTempToHandAction;
import Amiyamod.action.cards.KingSayAction;
import Amiyamod.action.cards.MagicYuJinAction;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.KingDamage;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.FirstSayPower;
import Amiyamod.power.KingSayPower;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;

import java.util.ArrayList;

public class KingSay extends CustomCard {
    //=================================================================================================================
    //@ 【魔王律令】 不能被打出 ，抽到此卡时将 !M! 张随机 源石技艺 拿到手中，回合结束时若它仍在手中则 消耗 所有手牌。
    //=================================================================================================================
    private static final String NAME = "KingSay";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = -2;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.RARE;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.NONE;//【是否指向敌人】
    public ArrayList<AbstractCard> list = new ArrayList<>();

    public KingSay() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 8;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 2;
        this.baseDraw = this.draw =1;
        //this.heal = 15;
        this.misc = 1;
        //DamageModifierManager.addModifier(this, new KingDamage());
        //this.exhaust = true;
        //this.isEthereal = true;
        //this.selfRetain = true;

        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(6);
            //this.exhaust = true;
            //this.isEthereal = false;
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(1);
            //this.baseDraw += 1;
            //this.draw = this.baseDraw;
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            //this.target = CardTarget.ENEMY;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    public void triggerWhenDrawn() {
        this.addToBot(new KingSayAction(this,true));
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (!this.list.isEmpty()){
            for (AbstractCard ca : this.list){
                if (ca.uuid == c.uuid){
                    this.list.remove(ca);
                    break;
                }
            }
            if (this.list.isEmpty()){
                if (AbstractDungeon.player.hand.size()<10){
                    this.addToBot(new KingSayAction(this,false));
                } else {
                    this.rawDescription = this.upgraded ? CARD_STRINGS.UPGRADE_DESCRIPTION : CARD_STRINGS.DESCRIPTION;
                    this.rawDescription += CARD_STRINGS.EXTENDED_DESCRIPTION[3];
                    this.initializeDescription();
                }
            } else {
                for (AbstractCard ca : this.list){
                    this.cardsToPreview = ca.makeSameInstanceOf();
                }
            }
        }
    }
/*
    public void triggerOnEndOfTurnForPlayingCard() {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "魔王律令：triggerOnEndOfTurnForPlayingCard"
        );
        int i = 0;
        if(!this.list.isEmpty()){
            for (AbstractCard c: this.list){
                for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisTurn){
                    if (c.uuid == card.uuid){
                        i++;
                        break;
                    }
                }
            }
        }
        if (i < this.list.size()){
            this.dontTriggerOnUseCard = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
        }
    }

 */
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return  false;
    }
    @Override
    public void triggerOnExhaust() {
        this.reset();
        super.triggerOnExhaust();
    }
    @Override
    public void onMoveToDiscard() {
        this.reset();
        super.onMoveToDiscard();
    }

    public void renderCardPreview(SpriteBatch sb) {
        if(!this.list.isEmpty()){
            for (AbstractCard c: this.list){
                c.flash(Color.BROWN);
            }
        }
        super.renderCardPreview(sb);
    }
    public void reset(){
        this.list.clear();
        this.cardsToPreview = null;
        this.rawDescription = this.upgraded ? CARD_STRINGS.UPGRADE_DESCRIPTION:CARD_STRINGS.DESCRIPTION;
        this.initializeDescription();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        /*
        if(this.dontTriggerOnUseCard){
            this.addToBot(new ExhaustAction(p.hand.size(),true,false,false));
            this.reset();
        }

         */
        //this.addToBot(new ApplyPowerAction(p,p,new KingSayPower(this.magicNumber)));
    }

    public AbstractCard makeCopy() {return new KingSay();}
}
