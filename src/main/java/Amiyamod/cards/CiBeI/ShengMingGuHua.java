package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// 生命固化
// 获得12（16）点 丝线。燃己2
public class ShengMingGuHua extends CustomCard {
    private static final String NAME = "ShengMingGuHua";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/" + NAME + ".png";//卡图

    private static final int COST = 1;//卡片费用

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.Amiyathecolor;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public ShengMingGuHua() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 12;
        this.magicNumber = this.baseMagicNumber;
        //this.isInnate = true; //固有
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.upgradeMagicNumber(4);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 获得丝线
        this.addToBot( new AddTemporaryHPAction(
                p, //受益者是能力持有者
                p, //来源是能力持有者
                this.magicNumber //获得丝线
            )
        );

        // 燃己
        Amiyamod.BurnSelf(2);
    }

    public AbstractCard makeCopy() {
        return new ShengMingGuHua();
    }
}
