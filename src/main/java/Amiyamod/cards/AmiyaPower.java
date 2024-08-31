package Amiyamod.cards;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class AmiyaPower extends CustomCard {
    private static final String NAME = "AmiyaPower";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 0;//卡片费用
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";//卡片描述
    private static final AbstractCard.CardType TYPE = CardType.SKILL;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public AmiyaPower() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //his.damage = this.baseDamage = 6;
        this.baseMagicNumber = this.magicNumber =2;
        this.timesUpgraded = 0;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        if (this.rarity == CardRarity.SPECIAL){
            this.rarity = CardRarity.COMMON;
        } else if (this.rarity == CardRarity.COMMON){
            this.rarity = CardRarity.UNCOMMON;
        } else if (this.rarity == CardRarity.UNCOMMON) {
            this.rarity = CardRarity.RARE;
            this.isEthereal = false;
            this.selfRetain = true;
            this.name = CARD_STRINGS.EXTENDED_DESCRIPTION[4];
        }
        if (this.timesUpgraded == 5){
            this.upgradeMagicNumber(1);
        }
        this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[Math.min(this.timesUpgraded-1, 3)];
        this.initializeDescription();

    }
    public boolean canUpgrade() {
        return (this.timesUpgraded < 5);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainEnergyAction(this.timesUpgraded > 3 ? 2 : 1));
        this.addToBot(new DrawCardAction(this.magicNumber));


        if (this.timesUpgraded > 0){
            ArrayList<AbstractGameAction> list =new ArrayList<>();
            list.add(new RemoveDebuffsAction(p));
            if (this.timesUpgraded > 1){
                list.add(new ApotheosisAction());
            }
            Amiyamod.Sword(true,list);
        }

        Amiyamod.HenJi(this.magicNumber,this,m);
    }
    public AbstractCard makeCopy() {return new AmiyaPower();}
}
