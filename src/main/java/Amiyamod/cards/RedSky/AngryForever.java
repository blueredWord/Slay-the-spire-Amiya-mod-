package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.AngryForeverPower;
import Amiyamod.power.RedSkyPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//永燃怒火
//（固有）进入愠怒，每回合开始时进入 愠怒。
public class AngryForever extends CustomCard {
    private static final String NAME = "AngryForever";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 2;//卡片费用

    private static final AbstractCard.CardType TYPE = CardType.POWER;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public AngryForever() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.baseMagicNumber = 2;
        //this.magicNumber = this.baseMagicNumber;
        //this.baseDraw = this.draw = 1;
        //this.exhaust = true;
        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.RedSky1);
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.selfRetain = true;
            //this.exhaust = false;
            //this.isInnate = true;
            //this.upgradeBaseCost(1);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //进入愠怒
        if (this.upgraded){
            Amiyamod.Sword(true);
        }
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new RedSkyPower()));
        //每回合开始时进入愠怒的能力
        this.addToBot(new ApplyPowerAction(p,p,new AngryForeverPower()));
    }
    public AbstractCard makeCopy() {return new AngryForever();}
}
