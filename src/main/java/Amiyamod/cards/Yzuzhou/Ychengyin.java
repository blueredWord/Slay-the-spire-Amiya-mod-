package Amiyamod.cards.Yzuzhou;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Iterator;

public class Ychengyin extends CustomCard {
    private static final String NAME = "Strike";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = -2;//卡片费用 -2为诅咒
    private static final CardType TYPE = CardType.CURSE;//卡片类型
    private static final CardColor COLOR = CardColor.COLORLESS;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.CURSE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.NONE;//无法选择
    public Ychengyin() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void triggerWhenDrawn() {
        //源石诅咒被抽到时共通效果
        Amiyamod.WhenYcardDrawn();
    }

    public void triggerOnEndOfPlayerTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        boolean var1 = false;
        Iterator var2 = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        //遍历本回合打出过的卡
        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            //如果打出过源石卡
            if (c.hasTag(YCardTagClassEnum.YCard)) {
                //向玩家施加虚弱
                var1 = true;
                /* 暂时封印深度感染的效果
                if(满足 && a){
                    Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                    while(var3.hasNext()) {
                        AbstractMonster mo = (AbstractMonster)var3.next();
                        this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                        this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    }
                }
                */
                break;
            }
        }
        if (var1){
            this.addToTop(new ApplyPowerAction(p, p, new WeakPower(p, 1, false), 1));
        }
    }



    public void use(AbstractPlayer p, AbstractMonster m) {}
    public void upgrade() {}
    public AbstractCard makeCopy() {return new Ychengyin();}
}
