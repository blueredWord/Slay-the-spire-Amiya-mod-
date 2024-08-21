package Amiyamod.cards.Yzuzhou;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Iterator;
//健忘症
//不可被打出 抽到此牌时令一张随机手牌获得虚无。
public class Yjianwang extends CustomCard {
    private static final String NAME = "Yjianwang";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = -2;//卡片费用 -2为诅咒
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.CURSE;//卡片类型
    private static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.COLORLESS;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.CURSE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;//无法选择
    public Yjianwang() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(YCardTagClassEnum.YZuZhou);
    }

    public void triggerWhenDrawn() {
        //被抽到时 随机一张手牌变为虚无
        /*
        AbstractCard c =AbstractDungeon.player.hand.getRandomCard(true);
        if(c!=null){
            c.isEthereal = true;
            c.rawDescription += "  NL 虚无 。";
        }
         */
        //源石诅咒被抽到时共通效果
        Amiyamod.WhenYcardDrawn();
    }

    public void triggerOnEndOfPlayerTurn() {
        //回合结束时 随机一张手牌变为虚无
        AbstractCard c =AbstractDungeon.player.hand.getRandomCard(true);
        if(c!=null){
            c.isEthereal = true;
            c.rawDescription += "  NL 虚无 。";
        }
        super.triggerOnEndOfPlayerTurn();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}
    public void upgrade() {}
    public AbstractCard makeCopy() {return new Yjianwang();}
}
