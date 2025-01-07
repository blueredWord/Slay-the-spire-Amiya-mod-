package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.action.UpRedAction;
import Amiyamod.action.cards.StoneSwordAction;
import Amiyamod.character.Amiya;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.BigNotWorkPower;
import Amiyamod.power.FireAgainPower;
import Amiyamod.power.ShadowSkyOpenPower;
import Amiyamod.power.StoneSwordPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class StoneSword extends CustomCard {
    private static final String NAME = "StoneSword";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 0;//卡片费用
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";//卡片描述
    private static final CardType TYPE = CardType.SKILL;//卡片类型
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.COMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public StoneSword() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 4;
        this.baseMagicNumber = this.magicNumber = 3;
        this.exhaust = true;
        this.misc = 1;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        this.tags.add(YCardTagClassEnum.RedSky1);
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    //+===============================================================================================================
    // [结晶化剑]  #@ 源石技艺 。 NL 获得1张 影霄 ，按手中 源石诅咒 的数量 升级 它。 NL 出鞘 : 打出的下1张 影霄 视为 源石技艺 。  NL 消耗 。
    //+===============================================================================================================

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.upgradeDamage(3); // 将该卡牌的伤害提高3点。
            this.upgradeMagicNumber(1);
            this.exhaust = false;
            //this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Amiyamod.getRedSky(this.magicNumber);
        this.addToBot(new ApplyPowerAction(p,p,new StoneSwordPower(this.misc)));
        this.addToBot(new StoneSwordAction(Math.max(0,this.magicNumber-3)));
        //Amiyamod.Sword(true,new UpRedAction(false));
        //Amiyamod.Sword(true,new ApplyPowerAction(p,p,new StoneSwordPower(this.magicNumber)));
    }

    public AbstractCard makeCopy() { return new StoneSword(); }
}
