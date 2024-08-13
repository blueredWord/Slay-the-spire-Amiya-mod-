package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.power.LineDefenderPower;
import Amiyamod.power.NoFengPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//护身丝线
//直到下个回合开始前，每受到1次伤害获得3（5）点丝线
public class LineDefender extends CustomCard {
    private static final String NAME = "LineDefender";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//卡片费用
    private static final AbstractCard.CardType TYPE = CardType.SKILL;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.Amiyathecolor;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public LineDefender() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        //this.exhaust = true; //消耗
        //this.tags.add(YCardTagClassEnum.YCard); //源石卡牌tag
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.upgradeBaseCost(1);
            this.upgradeMagicNumber(2);
            //this.exhaust = false;       //消耗
            //this.selfRetain = true;     //保留
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //获得状态：本回合造成伤害时获得等同于伤害点丝线。
        this.addToTop(new ApplyPowerAction(p, p, new LineDefenderPower(p, this.magicNumber), this.magicNumber));
    }
    public AbstractCard makeCopy() {return new LineDefender();}
}
