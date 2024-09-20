package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.action.KingSeeAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.relics.Yill;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MindRoll extends CustomCard {
    //=================================================================================================================
    //@ 【意志震荡】 急性发作 1。 NL 对所有敌人造成 !D! 点伤害，将一张 随机 追忆 拿入手中。 NL 此卡每次打出伤害提高 !M! 。
    //=================================================================================================================
    private static final String NAME = "MindRoll";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;//【是否指向敌人】

    public MindRoll() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 8;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 3;
        this.isMultiDamage = true;
        //this.baseDraw = this.draw = 1;
        //this.heal = 15;
        this.misc = 1;
        //this.exhaust = true;
        this.isEthereal = true;
        //this.selfRetain = true;

        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-1);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    public void applyPowers() {
        this.misc = AbstractDungeon.player.exhaustPile.size()/this.magicNumber;
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.misc;

        super.applyPowers();

        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.misc = AbstractDungeon.player.exhaustPile.size()/this.magicNumber;
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.misc;

        super.calculateCardDamage(mo);

        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.damage += this.misc;
        this.calculateCardDamage(m);

        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p,this.multiDamage,this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY)
        );
        this.addToBot(new KingSeeAction(this.cardID,1,false));
        //this.upgradeDamage(this.magicNumber);
        //Amiyamod.HenJi(this.misc,this,m);
    }
    public AbstractCard makeCopy() {return new MindRoll();}
}
