package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.relics.Yill;
import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class BadZhufu extends CustomCard {
    private static final String NAME = "BadZhufu";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/" + NAME + ".png";//卡图

    private static final int COST = 1;//卡片费用

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人
    //=================================================================================================================
    //@ 【禁忌祝福】 燃己 1 。 NL 出鞘 : 升级 !M! 张 赤霄 ，失去等同于它 升级 次数的生命 。
    //=================================================================================================================
    public BadZhufu() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.isInnate = true; //固有
        this.misc = 4;
        this.baseMagicNumber = this.magicNumber = this.misc ;
        this.exhaust = true;

        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            //this.upgradeMagicNumber(2);
            this.upgradeBaseCost(0);
            //this.selfRetain = true;

            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //获得丝线
        int level = this.misc;
        for (AbstractCard c : p.masterDeck.group){
            if (c.hasTag(YCardTagClassEnum.YZuZhou)){
                level += this.magicNumber;
            }
        }

        Amiyamod.LinePower(level);

        Amiyamod.HenJi(1,this,m);

    }

    public AbstractCard makeCopy() {return new BadZhufu();}
}
