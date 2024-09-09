package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.BreakRingPower;
import Amiyamod.power.ChiMeRaPower;
import Amiyamod.power.YSayPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class SuperYPotion extends CustomCard {
    //=================================================================================================================
    //@ 【强效源石爆发剂】 消耗  将抽牌堆中所有的 源石诅咒拿到手中 ，获得等量能量。 本回合不会增加感染进度。
    //=================================================================================================================
    private static final String NAME = "SuperYPotion";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public SuperYPotion() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
        //this.selfRetain = true;
        //this.heal = 15;
        this.magicNumber = this.baseMagicNumber = 1;
        //this.misc = 20;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            //this.selfRetain = true;
            this.upgradeBaseCost(0);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : p.drawPile.group){
            if (c.hasTag(YCardTagClassEnum.YZuZhou)){
                list.add(c);
            }
        }
        int n = Math.min(10-p.hand.size(),list.size());
        int i=0;
        for(;i<n;i++){
            p.drawPile.moveToHand(list.get(i));
        }
        this.addToBot(new ApplyPowerAction(p,p,new BreakRingPower(this.magicNumber)));
        //this.addToBot(new ApplyPowerAction(p,p,new YSayPower()));
        //感染进度
        //Amiyamod.addY(i);
    }
    public AbstractCard makeCopy() {return new SuperYPotion();}
}
