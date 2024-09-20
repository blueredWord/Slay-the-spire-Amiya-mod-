package Amiyamod.cards.Yzuzhou;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.patches.YZCardInterface;
import Amiyamod.power.FirstSayPower;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Iterator;
//认知障碍
//不可被打出 每当你 燃己 。获得 !M! 层临时混乱
public class Ysnake extends YCard implements YZCardInterface {
    private static final String NAME = "Ysnake";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = -2;//卡片费用 -2为诅咒
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.CURSE;//卡片类型
    private static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.CURSE;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.CURSE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;//无法选择
    public Ysnake() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(YCardTagClassEnum.YZuZhou);
    }

    private  void reset(){
        this.misc = 0;
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.initializeDescription();
    }
    public void triggerWhenDrawn() {
        this.reset();
        super.triggerWhenDrawn();
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        this.misc++;
        if (this.misc<this.magicNumber){
            this.rawDescription = CARD_STRINGS.DESCRIPTION+CARD_STRINGS.EXTENDED_DESCRIPTION[0]+this.misc+CARD_STRINGS.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }else{
            this.addToBot(new PressEndTurnButtonAction());
            this.reset();
        }
    }
    public void triggerOnEndOfPlayerTurn() {
        //回合结束时 随机手牌消耗
        //this.addToBot(new ExhaustAction(this.magicNumber, true, true, false, Settings.ACTION_DUR_XFAST));
        this.reset();
        super.triggerOnEndOfPlayerTurn();
    }
    public void triggerOnExhaust() {
        this.reset();
    }
    public void onMoveToDiscard() {
        this.reset();
    }

    @Override
    public void YZupgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.upgradeDamage(3); // 将该卡牌的伤害提高3点。
            this.upgradeMagicNumber(-1);
            //this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void upgrade() {}
    public AbstractCard makeCopy() {return new Ysnake();}

}
