package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.action.UpRedAction;
import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShadowCloudBreakB extends CustomCard {
    private static final String NAME = "ShadowCloudBreakB";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = -2;//卡片费用
    private static final CardType TYPE = CardType.POWER;//卡片类型
    private static final CardColor COLOR = CardColor.COLORLESS   ;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.SPECIAL;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.NONE;//是否指向敌人

    public ShadowCloudBreakB() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void onChoseThisOption() {
        this.addToTop(new UpRedAction(true,false));
    }

    @Override
    public void upgrade() {}
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}
    public AbstractCard makeCopy() {return new ShadowCloudBreakB();}
}