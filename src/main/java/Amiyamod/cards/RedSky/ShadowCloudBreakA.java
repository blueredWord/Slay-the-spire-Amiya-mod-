package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ShadowSkillAction;
import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShadowCloudBreakA extends CustomCard {
    private static final String NAME = "ShadowCloudBreakA";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = -2;//卡片费用
    private static final AbstractCard.CardType TYPE = CardType.POWER;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.NONE;//是否指向敌人

    public ShadowCloudBreakA() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void onChoseThisOption() {
        Amiyamod.getRedSky(1,false);
    }

    @Override
    public void upgrade() {}
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}
    public AbstractCard makeCopy() {return new ShadowCloudBreakA();}
}