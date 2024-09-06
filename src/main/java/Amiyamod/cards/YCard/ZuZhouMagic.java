package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.ZuZhouMagicPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ZuZhouMagic extends CustomCard {
    //=================================================================================================================
    //@ 【诅咒巫咒】 源石技艺 。 造成 !D! 点伤害。 NL 当你失去生命时，目标敌人也将失去等量的生命。 NL 消耗 。
    //=================================================================================================================
    private static final String NAME = "ZuZhouMagic";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 0;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//【是否指向敌人】

    public ZuZhouMagic() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 4;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
        //this.selfRetain = true;
        //this.heal = 15;
        //this.magicNumber = this.baseMagicNumber = 2;
        //this.misc = 20;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(1)
            this.isInnate = true;
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        m,
                        new DamageInfo(p, damage,this.damageTypeForTurn)
                )
        );
        if (!p.hasPower(ZuZhouMagicPower.POWER_ID)){
            this.addToBot(new ApplyPowerAction(p,p,new ZuZhouMagicPower(p,m)));
        }
        //感染进度
        Amiyamod.addY(1);
    }
    public AbstractCard makeCopy() {return new ZuZhouMagic();}
}
