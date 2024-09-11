package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.power.BreakRingPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BreakRing extends CustomCard {
    //=================================================================================================================
    //@ 【极限施法】 增加 !M! 点 感染进度 。 NL 你本回合打出的下1张 急性发作 卡将额外触发一次效果。 NL 燃己 !M! 。 消耗 。
    //=================================================================================================================
    private static final String NAME = "BreakRing";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 0;//卡片费用
    private static final AbstractCard.CardType TYPE = CardType.SKILL;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public BreakRing() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        //this.baseDraw = this.draw = 1;
        this.exhaust = true;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.selfRetain = true;
            //this.exhaust = false;
            this.upgradeMagicNumber(-2);
            //this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Amiyamod.addY(this.magicNumber);
        this.addToBot(new ApplyPowerAction(p,p,new BreakRingPower(1)));
        Amiyamod.BurnSelf(this.magicNumber);
    }
    public AbstractCard makeCopy() {return new BreakRing();}
}
