package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.KingSeePower;
import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KingSee extends CustomCard {
    //=================================================================================================================
    //@ 【魔王幻景】 给予 8 点 丝线 。 抽 2 张牌 。 NL 目标的回合开始时，若 丝线 仍在， 给予 !M! 层 虚弱 。（ NL 虚无 。）
    //=================================================================================================================
    private static final String NAME = "KingSee";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 0;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//【是否指向敌人】

    public KingSee() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 8;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 8;
        this.baseDraw = this.draw = 2;
        this.misc = 2;
        //this.exhaust = true;
        //this.isEthereal = true;
        //this.selfRetain = true;
        this.tags.add(YCardTagClassEnum.LINE); //丝线卡牌tag
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
            this.upgradeMagicNumber(-4);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //m.takeTurn();
        //this.addToBot();
        this.addToBot(new HealAction(m,p,this.magicNumber));
        //Amiyamod.LinePower(this.misc,m);
        this.addToBot(new DrawCardAction(this.draw));
        this.addToBot(new ApplyPowerAction(m,p,new KingSeePower(this.misc,m)));
        //this.addToBot(new ApplyPowerAction(p,p,new KingSayPower(this.magicNumber)));
    }

    public AbstractCard makeCopy() {return new KingSee();}
}
