package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.MagicYuJinAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.FirstSayPower;
import Amiyamod.power.KingSayPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

public class KingSay extends CustomCard {
    //=================================================================================================================
    //@ 【魔王律令】 抽到此卡时将2张随机 源石诅咒 拿入手中。 NL 回合结束时 消耗 所有手牌。 至少消耗3张时将 !M! 张 升级 过的 源石技艺 拿入手中。 NL 虚无 。
    //=================================================================================================================
    private static final String NAME = "KingSay";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public KingSay() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 8;
        //this.baseBlock = this.block = 12;
        this.baseDraw = this.draw =  this.magicNumber = this.baseMagicNumber = 1;
        //this.heal = 15;
        this.misc = 2;
        //this.exhaust = true;
        this.isEthereal = true;
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
            this.upgradeMagicNumber(1);
            //this.baseDraw += 1;
            //this.draw = this.baseDraw;
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            //this.target = CardTarget.ENEMY;
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    public void triggerWhenDrawn() {
        int i = AbstractDungeon.player.hand.size();
        i = 10 - AbstractDungeon.player.hand.size();
        i = Math.min(this.misc,i);
        for (int n =0 ;n<i;n++){
            this.addToBot(new MakeTempCardInHandAction(Amiyamod.GetNextYcard(true)));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p,p,new KingSayPower(this.magicNumber)));
    }

    public AbstractCard makeCopy() {return new KingSay();}
}
