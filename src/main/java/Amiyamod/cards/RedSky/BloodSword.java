package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.BloodSwordAction;
import Amiyamod.cards.YCard.YOpen;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.relics.Yill;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BloodSword extends CustomCard {
    //=================================================================================================================
    //@ 【血影】 燃己 1 。 NL 出鞘 : 升级 !M! 张 赤霄 ，失去等同于它 升级 次数的生命 。
    //=================================================================================================================
    private static final String NAME = "BloodSword";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 0;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public BloodSword() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.misc = 1;
        this.tags.add(YCardTagClassEnum.RedSky1);
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
            //this.upgradeMagicNumber(1);
            //this.upgradeBlock(6);
            //this.exhaust = false;
            //this.upgradeDamage(6);
            //this.exhaust = false;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded){
            Amiyamod.BurnSelf(this.magicNumber);
        }

        Amiyamod.Sword(true,new BloodSwordAction());
    }
    public AbstractCard makeCopy() {return new BloodSword();}
}
