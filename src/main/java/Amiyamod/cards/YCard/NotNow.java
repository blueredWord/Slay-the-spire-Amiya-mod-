package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NotNow extends CustomCard {
    //=================================================================================================================
    //@ 【终局未至】 源石技艺 。 NL 急性发作 !M! 。 NL 对所有敌人造成 !D! 点伤害。 NL 急性发作 触发时，抽 !M! 张牌。
    //=================================================================================================================
    private static final String NAME = "NotNow";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;//【是否指向敌人】

    public NotNow() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 9;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 3;
        this.isMultiDamage = true;
        //this.baseDraw = this.draw = 1;
        //this.heal = 15;
        //this.misc = 20;
        //this.exhaust = true;
        this.isEthereal = true;
        //this.selfRetain = true;

        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YCard);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            //this.exhaust = false;
            //this.upgradeDamage(4);
            this.upgradeMagicNumber(3);
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = this.damage;
        this.damage = this.damage + this.baseMagicNumber;
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p,this.multiDamage,this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY)
        );
        for (AbstractCard c : p.hand.group){
            if (c.hasTag(YCardTagClassEnum.YZuZhou)){
                this.addToBot(new DrawCardAction(this.draw));
                break;
            }
        }
        this.damage = i ;
        Amiyamod.addY(1);
        Amiyamod.HenJi(this.magicNumber,this,m);
    }
    public AbstractCard makeCopy() {return new NotNow();}
}
