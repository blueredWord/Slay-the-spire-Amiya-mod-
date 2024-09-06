package Amiyamod.cards.Yzuzhou;


import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.FirstSayPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//亡陨
//不可被打出 回合结束时 燃己5。
public class Ydead extends CustomCard {
    private static final String NAME = "Ydead";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = -2;//卡片费用 -2为诅咒
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.CURSE;//卡片类型
    private static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.CURSE;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.CURSE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;//无法选择
    public Ydead() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(YCardTagClassEnum.YZuZhou);
    }

    public void triggerWhenDrawn() {
        //源石诅咒被抽到时共通效果
        Amiyamod.WhenYcardDrawn();
        //Amiyamod.BurnSelf(this.magicNumber);
    }

    public void triggerOnEndOfPlayerTurn() {
        //回合结束时失去生命。
        this.addToBot(new LoseHPAction(AbstractDungeon.player,AbstractDungeon.player,this.magicNumber));
        //Amiyamod.BurnSelf();
        super.triggerOnEndOfPlayerTurn();
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(FirstSayPower.POWER_ID)){
            return true;
        }
        return super.canUse(p,m);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {}
    public void upgrade() {}
    public AbstractCard makeCopy() {return new Ydead();}
}
