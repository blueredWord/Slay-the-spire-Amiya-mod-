package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.action.KingSeeAction;
import Amiyamod.action.SEAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.power.LittleTePower;
import Amiyamod.power.LittleTePower2;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class LittleTe2 extends CustomCard {
    private static final String NAME = "LittleTe2";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//卡片费用
    private static final CardType TYPE = CardType.SKILL;//卡片类型
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public LittleTe2() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //源石卡牌tag
        this.draw = this.baseDraw = this.baseMagicNumber = this.magicNumber = 2;
        this.misc = 1;
        //this.isEthereal = true;
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.isEthereal = false;
            //this.upgradeBaseCost(1);
            this.upgradeMagicNumber(1);
            this.draw = this.baseDraw = this.baseMagicNumber;
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SEAction(NAME,true));
        //获得状态:获得格挡时改为获得等量的丝线
        this.addToBot(new DrawCardAction(this.magicNumber));
        this.addToTop(new ApplyPowerAction(p, p, new LittleTePower2(this.misc)));
    }
    public AbstractCard makeCopy() {return new LittleTe2();}
}
