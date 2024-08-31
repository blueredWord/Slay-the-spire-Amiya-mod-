package Amiyamod.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.CiBeI.LineBody;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.LineBow;
import Amiyamod.power.MemoryPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Memory extends CustomCard {
    private static final String NAME = "Memory";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/" + NAME + ".png";//卡图

    private static final int COST = 2;//卡片费用

    private static final AbstractCard.CardType TYPE = CardType.POWER;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public Memory() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        //this.exhaust = true ;
        //this.isEthereal = true;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            //this.upgradeMagicNumber(1);
            //this.upgradeBaseCost(0);
            //this.isEthereal = false;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer pl, AbstractMonster m) {
        CardGroup G = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for (AbstractPlayer p  : CardCrawlGame.characterManager.getAllCharacters()){
            p.getTitle(p.chosenClass);
            p.getCardColor();
            String ID = this.cardID+p.getCardColor().name();
            String Des =CARD_STRINGS.EXTENDED_DESCRIPTION[1]+this.magicNumber+CARD_STRINGS.EXTENDED_DESCRIPTION[2]+p.getTitle(p.chosenClass);
            if (this.upgraded){
                Des+= CARD_STRINGS.EXTENDED_DESCRIPTION[4];
            }else {
                Des+= CARD_STRINGS.EXTENDED_DESCRIPTION[3];
            }
            int COST = -2;
            AbstractPower pow = new MemoryPower(p.getCardColor(),this.magicNumber,this.upgraded,p.getTitle(p.chosenClass));
            CustomCard card = new CustomCard(ID,CARD_STRINGS.EXTENDED_DESCRIPTION[0]+p.getTitle(p.chosenClass),IMG_PATH, COST,Des, CardType.POWER, p.getCardColor(), AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.NONE) {
                @Override
                public void upgrade() {}
                @Override
                public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}
                @Override
                public void onChoseThisOption() {
                    this.addToBot(new ApplyPowerAction(pl,pl,pow));
                }
                @Override
                public boolean canUse(AbstractPlayer p, AbstractMonster m) {
                    return  false;
                }
            };

            if (this.upgraded) {card.upgrade();}
            G.addToBottom(card);
        }
        this.addToBot(new ChooseOneAction(G.group));
    }

    public AbstractCard makeCopy() {
        return new Memory();
    }
}
