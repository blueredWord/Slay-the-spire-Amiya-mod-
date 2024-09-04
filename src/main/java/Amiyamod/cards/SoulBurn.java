package Amiyamod.cards;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ChoseTemCardAction;
import Amiyamod.action.cards.SoulBurnAction;
import Amiyamod.cards.YCard.MagicBook;
import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class SoulBurn extends CustomCard {
    //=================================================================================================================
    //@ 【魂灵熔炉】 消耗至多 !M! 张弃牌堆中的卡牌。 NL 接下来的数个回合开始时获得 [B] ，每消耗1张卡牌便持续1回合。
    //=================================================================================================================
    private static final String NAME = "SoulBurn";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public SoulBurn() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 2;
        //this.heal = 15;
        //this.misc = 20;

        //this.exhaust = true;
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
            //this.upgradeMagicNumber(2);
            //this.selfRetain = true;
            //this.selfRetain = true;
            this.upgradeBaseCost(0);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new SoulBurnAction(this.magicNumber));
    }
    public AbstractCard makeCopy() {return new SoulBurn();}
}
