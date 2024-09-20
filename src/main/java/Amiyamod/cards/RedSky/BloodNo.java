package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.BloodSwordAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.BloodNoPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BloodNo extends CustomCard {
    //=================================================================================================================
    //@ 【青色怒火】 NL 造成 !D! 点伤害。 NL 入鞘 : 本回合打出的下一张 赤霄 无视 格挡 。
    //=================================================================================================================
    private static final String NAME = "BloodNo";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 3;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.RARE;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//【是否指向敌人】

    public BloodNo() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 0;
        this.damage = this.baseDamage = 27;
        this.misc = 1;
        //this.heal = 3;
        this.tags.add(YCardTagClassEnum.RedSky1);
        //this.exhaust = true;
        //this.isEthereal = true;
        //this.selfRetain = true;

        //源石卡牌tag
        //this.tags.add(CardTags.HEALING);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        //this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.misc += 1;
            //this.upgradeMagicNumber(1);
            //this.upgradeBlock(6);
            //this.exhaust = false;
            //this.upgradeDamage(10);
            //this.exhaust = false;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;

        this.baseMagicNumber = 0;
        for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
            if (c.isEthereal){
                this.baseMagicNumber += this.misc;
            }
        }

        this.baseDamage += this.baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.baseMagicNumber = 0;
        for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
            if (c.isEthereal){
                this.baseMagicNumber += this.misc;
            }
        }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Amiyamod.BurnSelf(1);
        this.damage += this.magicNumber;
        this.calculateCardDamage(m);
        this.addToBot(
                new DamageAction(m, new DamageInfo(p, damage, this.damageTypeForTurn))
        );
        //Amiyamod.BurnSelf(this.magicNumber);
        //Amiyamod.Sword(this.upgraded,new ApplyPowerAction(p,p,new BloodNoPower(this.magicNumber)));
    }
    public AbstractCard makeCopy() {return new BloodNo();}
}
