package Amiyamod.cards.Yzuzhou;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.patches.YZCardInterface;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

//浸泪
//不可被打出 不因此诅咒的效果获得丝线时改为获得等量的格挡（本身无效果，由获得丝线接口检测）
public class FullCry extends CustomCard {
    private static final String NAME = "FullCry";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 1;//卡片费用 -2为诅咒
    private static final CardType TYPE = CardType.CURSE;//卡片类型
    private static final CardColor COLOR = CardColor.CURSE;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.SPECIAL;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.NONE;//无法选择
    public FullCry() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(YCardTagClassEnum.YZuZhou);
        this.isEthereal = true;
        this.baseMagicNumber = this.magicNumber = 2;
    }


    //public void use(AbstractPlayer p, AbstractMonster m) {}
    public void upgrade() {}

    public void triggerOnExhaust() {
        AbstractDungeon.player.increaseMaxHp(this.magicNumber, true);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    public AbstractCard makeCopy() {return new FullCry();}

}
