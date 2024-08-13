package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.power.HerSeePower;
import Amiyamod.power.LineBow;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
//碎戒
//将1张“源石诅咒”加入手牌，获得2点能量，选择1张消耗牌堆的牌加入手牌。燃己2
public class BreakRing extends CustomCard {
    private static final String NAME = "BreakRing";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//卡片费用

    private static final AbstractCard.CardType TYPE = CardType.SKILL;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.Amiyathecolor;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public BreakRing() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        //this.baseDraw = this.draw = 1;
        //this.exhaust = true;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.selfRetain = true;
            //this.exhaust = false;
            this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //一张随机诅咒
        this.addToBot(new MakeTempCardInHandAction(Amiyamod.GetNextYcard(true)));
        //获得能量
        this.addToBot(new GainEnergyAction(this.magicNumber));
        //获得一张消耗区的卡
        this.addToBot(new ExhumeAction(false));
        //燃己2
       Amiyamod.BurnSelf(this.magicNumber);
    }
    public AbstractCard makeCopy() {return new BreakRing();}
}
