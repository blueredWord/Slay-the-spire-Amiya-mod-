package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.cards.YCard.LoseWish;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.HopePower;
import Amiyamod.power.LoseWishPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Hope extends CustomCard {
    //=================================================================================================================
    //@ 【期冀之汇】 只要你拥有 丝线 ，NL 打出 源石技艺 时不会增长 感染进度 改为失去1点生命。
    //=================================================================================================================
    private static final String NAME = "Hope";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.POWER;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public Hope() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 1;
        //this.heal = 15;
        //this.misc = 20;
        //this.isEthereal = true;
        //this.selfRetain = true;

        //源石卡牌tag
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(6);
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            //this.selfRetain = true;
            this.upgradeBaseCost(0);
            //this.isInnate = true ;
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p,p,new HopePower(this.magicNumber)));
    }

    public AbstractCard makeCopy() {return new Hope();}
}
