package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ShadowBackWindyAction;
import Amiyamod.action.cards.ShadowBloodAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.ShadowBackWindyPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Iterator;

public class ShadowBackWindy extends CustomCard {
    private static final String NAME = "ShadowBackWindy";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 1;//卡片费用
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";//卡片描述
    private static final CardType TYPE = CardType.SKILL;//卡片类型
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    //抽 !M! 张牌，本回合每当打出 赤霄 获得 !M! 点 格挡 。 NL 出鞘 : 给予所有敌人 !M! 层 虚弱 。
    public ShadowBackWindy() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 12;
        this.baseBlock = this.block = 5;

        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
        this.baseDraw = this.draw = this.magicNumber = this.baseMagicNumber = 1;
        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.RedSky1);
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.upgradeDamage(6);
            this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(this.magicNumber));
        this.addToBot(new ApplyPowerAction(p,p,new ShadowBackWindyPower(this.block)));
        Amiyamod.Sword(true,new ShadowBackWindyAction(this.magicNumber));
    }
    public AbstractCard makeCopy() {return new ShadowBackWindy();}
}
