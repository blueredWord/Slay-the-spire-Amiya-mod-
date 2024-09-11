package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;

import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Ghost extends CustomCard {
    //=================================================================================================================
    //@ 【幽影重重】 将你当前的 丝线 翻倍。 NL 消耗 。
    //=================================================================================================================
    private static final String NAME = "Ghost";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 2;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public Ghost() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.baseBlock = this.block = 12;
        //this.magicNumber = this.baseMagicNumber = 4;
        //this.heal = 15;
        //this.misc = 20;

        this.exhaust = true;
        //this.isEthereal = true;
        this.selfRetain = true;

        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.exhaust = false;
            this.upgradeBaseCost(1);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int var = TempHPField.tempHp.get(p);
        if (var > 0){
            AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(p,p,var));
            //TempHPField.tempHp.set(p,var * 2);
        }
    }

    public AbstractCard makeCopy() {return new Ghost();}
}
