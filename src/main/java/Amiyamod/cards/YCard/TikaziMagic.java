package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.TikaziMagicAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class TikaziMagic extends CustomCard {
    //=================================================================================================================
    //@ 【提卡兹巫咒】  急性发作 。 源石技艺 。 NL 造成 !D! 点伤害2次，抽2张牌。 NL 抽到 源石诅咒 或 源石技艺 时，获得 [E] 。
    //=================================================================================================================
    private static final String NAME = "TikaziMagic";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public TikaziMagic() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.UPGRADE_DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        //this.exhaust = true;
        //this.selfRetain = true;
        //this.heal = 15;
        this.draw = this.baseDraw =  this.magicNumber = this.baseMagicNumber = 2;
        this.misc = 2;
        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.draw = this.baseDraw =  this.baseMagicNumber;
            //this.upgradeDamage(4);
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //抽卡

        this.addToBot(new DrawCardAction(this.magicNumber,new TikaziMagicAction()));

        //}else {
        // this.addToBot(new DrawCardAction(this.magicNumber));}
        //,new TikaziMagicAction()
    }
    public AbstractCard makeCopy() {return new TikaziMagic();}
}
