package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.character.Amiya;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.BigNotWorkPower;
import Amiyamod.power.FireAgainPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
//结晶化剑
//急性感染 1。 NL 源石技艺 。 燃己 1。 NL 获得一张 赤霄 ,将其升级 !M! 次。 NL 出鞘 :额外升级一次。
public class StoneSword extends CustomCard {
    private static final String NAME = "StoneSword";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 0;//卡片费用
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";//卡片描述
    private static final CardType TYPE = CardType.SKILL;//卡片类型
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.COMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public StoneSword() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 4;
        this.baseMagicNumber = this.magicNumber = 1;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        this.tags.add(YCardTagClassEnum.RedSky1);
        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.upgradeDamage(3); // 将该卡牌的伤害提高3点。
            this.upgradeMagicNumber(1);
            //this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //感染进度
        Amiyamod.addY(1);
        //出鞘
        int n = this.magicNumber;
        if(p.hasPower(BigNotWorkPower.POWER_ID)){
            if (p.getPower(BigNotWorkPower.POWER_ID).amount>BigNotWorkPower.cardsDoubledThisTurn){
                BigNotWorkPower.cardsDoubledThisTurn++;
                n++;
            }
        }
        ArrayList<AbstractGameAction> l1 = new ArrayList<AbstractGameAction>();
        l1.add(new MakeTempCardInHandAction(new RedSky(n+1)));
        ArrayList<AbstractGameAction> l2 = new ArrayList<AbstractGameAction>();
        l2.add(new MakeTempCardInHandAction(new RedSky(n)));
        Amiyamod.Sword(true,l1,l2);
        //燃己
        //Amiyamod.BurnSelf(2);
        //急性感染
        Amiyamod.HenJi(1,this,m);
    }

    public AbstractCard makeCopy() { return new StoneSword(); }
}
