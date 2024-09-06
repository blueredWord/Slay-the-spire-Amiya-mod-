package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.cards.CiBeI.SoulDefend;
import Amiyamod.cards.Yzuzhou.ASay;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.power.FirstSayPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import java.util.Iterator;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER;
import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL;

public class FirstSay extends CustomCard {
    //=================================================================================================================
    //@ 【本源呢喃】 （固有）可以打出原本 不能被打出 的 源石诅咒 。 NL 回合开始时将一张随机 源石诅咒 加入手牌。
    //=================================================================================================================
    private static final String NAME = "FirstSay";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 2;//【卡片费用】
    private static final CardType TYPE = POWER;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.RARE;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public FirstSay() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.baseBlock = this.block = 12;
        this.cardsToPreview = new ASay();
        this.magicNumber = this.baseMagicNumber = 1;
        //this.heal = 15;
        //this.misc = 20;
        //this.isEthereal = true;
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
            //this.upgradeBlock(6);
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            //this.selfRetain = true;
            this.isInnate = true;
            //this.upgradeBaseCost(2);
            //this.target = CardTarget.ALL_ENEMY;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p,p,new FirstSayPower(this.magicNumber)));
    }
    public AbstractCard makeCopy() {return new FirstSay();}
}
