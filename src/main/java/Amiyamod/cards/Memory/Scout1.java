package Amiyamod.cards.Memory;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.power.OutcastPower;
import Amiyamod.power.ScoutPower;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Scout1 extends CustomCard {
    //=================================================================================================================
    //@ 【】
    //=================================================================================================================
    private static final String NAME = "Scout1";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 0;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.SPECIAL;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//【是否指向敌人】

    public Scout1() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 0;
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        this.purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(ScoutPower.POWER_ID)){
            this.damage += this.magicNumber;
            this.calculateCardDamage(m);
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE, true));
            this.addToBot(new RemoveSpecificPowerAction(m,p,ScoutPower.POWER_ID));
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        if (mo.hasPower(ScoutPower.POWER_ID)){

            this.baseMagicNumber = ((TwoAmountPower)(mo.getPower(ScoutPower.POWER_ID))).amount2;
        } else {
            this.baseMagicNumber = 0;
        }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }


    public boolean canUpgrade() {return false;}
    @Override
    public void upgrade() {}
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (m!= null && m.hasPower(ScoutPower.POWER_ID)){
                return true;
            }
            this.cantUseMessage = CARD_STRINGS.UPGRADE_DESCRIPTION;
            return false;
        }
    }

    public AbstractCard makeCopy() {return new Scout1();}
}
