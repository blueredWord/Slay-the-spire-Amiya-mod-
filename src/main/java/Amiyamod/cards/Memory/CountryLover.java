package Amiyamod.cards.Memory;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.power.YBBSPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CountryLover extends CustomCard {
    //=================================================================================================================
    //@ 【爱国者】 造成 !D! 点伤害 !M! 次。获得 !B! 点 格挡 。 NL 将1张 *生命献祭 拿到手中。 NL 消耗 。
    //=================================================================================================================
    private static final String NAME = "CountryLover";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 4;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.SPECIAL;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//【是否指向敌人】

    public CountryLover() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 7;
        this.baseBlock = this.block = 24;
        this.magicNumber = this.baseMagicNumber = 4;
        this.misc = 1;
        this.cardsToPreview = new CountryLover1();
        //this.heal = 15;
        //this.misc = 198;
        this.exhaust = true;
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
            this.upgradeBaseCost(3);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0;i<this.magicNumber;i++){
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }
        this.addToBot(new GainBlockAction(p,p,this.block));
        this.addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeCopy()));
    }

    public AbstractCard makeCopy() {return new CountryLover();}
}
