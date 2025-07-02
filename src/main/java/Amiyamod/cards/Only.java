package Amiyamod.cards;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.OnlyAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Only extends CustomCard {
    private static final String NAME = "Before";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/Before.png";//卡图

    private static final int COST = 0;//卡片费用
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;//是否指向敌人
    //=================================================================================================================
    //@ 【仅剩的创意】
    //=================================================================================================================
    public Only() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //源石卡牌tag
        this.damage = this.baseDamage = 7;
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(3);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p,this.multiDamage,this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE)
        );
        if ( !p.hand.isEmpty() ){
            this.addToBot(new OnlyAction(this));
        }
    }

    public AbstractCard makeCopy() {
        return new Only();
    }
}
