package Amiyamod.cards;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.power.FindingPower;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import org.apache.logging.log4j.LogManager;

public class Before extends CustomCard  {
    private static final String NAME = "Before";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/" + NAME + ".png";//卡图

    private static final int COST = -2;//卡片费用
    private static final AbstractCard.CardType TYPE = CardType.POWER;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人
    //=================================================================================================================
    //@ 【昔日道标】
    //=================================================================================================================
    public Before() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //源石卡牌tag
        this.isInnate = true;
        this.selfRetain = true;
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    public Before(AbstractCard card) {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.EXTENDED_DESCRIPTION[0] + card.name + CARD_STRINGS.EXTENDED_DESCRIPTION[1], TYPE, COLOR, RARITY, TARGET);
        //源石卡牌tag
        this.isInnate = true;
        this.selfRetain = true;
        this.cardsToPreview =card;
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerOnExhaust() {
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this.cardsToPreview.makeSameInstanceOf(), false));
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        return false;
    }
    public AbstractCard makeCopy() {
        AbstractCard A = new Before();
        if (this.cardsToPreview != null){
            A.cardsToPreview = this.cardsToPreview.makeStatEquivalentCopy();
        }

        return A;
    }

}
