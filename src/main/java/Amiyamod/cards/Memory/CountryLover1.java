package Amiyamod.cards.Memory;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.power.OutcastPower;
import Amiyamod.power.YBBSPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CountryLover1 extends CustomCard {
    //=================================================================================================================
    //@ 【生命献祭】 将 *投矛 拿到手中 。 NL 每回合结束时增加 !M! 感染进度 ，对所有敌人造成7点伤害。 NL 虚无 。
    //=================================================================================================================
    private static final String NAME = "CountryLover1";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 0;//【卡片费用】
    private static final CardType TYPE = CardType.POWER;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.SPECIAL;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public CountryLover1() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 1;
        this.cardsToPreview = new CountryLover2();
        //this.heal = 15;
        //this.misc = 198;
        this.isEthereal = true;
        //this.selfRetain = true;
        //this.selfRetain = true;

        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YZuZhou);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeBaseCost(0);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot( new MakeTempCardInHandAction(this.cardsToPreview.makeCopy()));
        this.addToBot(new ApplyPowerAction(p,p,new YBBSPower(this.magicNumber)));
    }

    public AbstractCard makeCopy() {return new CountryLover1();}
}
