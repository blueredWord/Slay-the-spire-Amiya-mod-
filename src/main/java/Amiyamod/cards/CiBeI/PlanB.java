package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.action.KingSeeAction;
import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlanB extends CustomCard {
    private static final String NAME = "PlanB";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/" + NAME + ".png";//卡图

    private static final int COST = 0;//卡片费用
    private static final AbstractCard.CardType TYPE = CardType.SKILL;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public PlanB() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //源石卡牌tag
        this.selfRetain = true;
        this.exhaust = true;
        //this.isEthereal = true;
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.isEthereal = false;
            //this.upgradeBaseCost(1);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = 11 - p.hand.size();
        for (int n = 0; n < i; n++) {
            this.addToBot(new KingSeeAction(this.cardID, 1, this.upgraded));
        }
    }

    public AbstractCard makeCopy() {
        return new PlanB();
    }
}