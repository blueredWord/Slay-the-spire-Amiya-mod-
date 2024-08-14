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
import java.util.UUID;
// 偏执症
// 不可被打出 抽到此牌时，选择一张手牌耗能+1，若你回合结束时没有将其打出，燃己4。
public class Ymust extends CustomCard {
    private static final String NAME = "Ymust";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = -2;//卡片费用 -2为诅咒
    private static final CardType TYPE = CardType.CURSE;//卡片类型
    private static final CardColor COLOR = CardColor.COLORLESS;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.CURSE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.NONE;//无法选择
    private UUID Carduuid = null;
    public Ymust() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 4;
    }

    public void triggerWhenDrawn() {
        //源石诅咒被抽到时共通效果
        Amiyamod.WhenYcardDrawn();
        //随机手牌费用加1
        CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);;
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if (card.type != CardType.CURSE){
                G.addToTop(card);
            }
        }
        if (G.isEmpty()){
            for(AbstractCard card : AbstractDungeon.player.hand.group){
                G.addToTop(card);
            }
        }
        if (!G.isEmpty()){
            AbstractCard card = G.getRandomCard(true);
            card.costForTurn += 1;
            card.isCostModifiedForTurn = true;
            card.isGlowing = true;
            this.Carduuid = card.uuid;
        }
    }

    public void triggerOnEndOfPlayerTurn() {
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if (card.uuid == this.Carduuid){
                Amiyamod.BurnSelf(this.magicNumber);
                card.isGlowing = false;
                break;
            }
        }
        super.triggerOnEndOfPlayerTurn();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}
    public void upgrade() {}
    public AbstractCard makeCopy() {return new Ymust();}

}
