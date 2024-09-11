package Amiyamod.cards.Memory;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.LittleLeOrb;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.ACEPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;

public class Her extends CustomCard {
    //=================================================================================================================
    //@ 【特雷西亚】 将充能球栏位提升至 !M! 。 NL 生成 !M! 粒 微尘 。 NL 消耗 。
    //=================================================================================================================
    private static final String NAME = "Her";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 3;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.SPECIAL;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public Her() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.misc = 1;
        this.tags.add(YCardTagClassEnum.YCard);
        //this.selfRetain = true;
        //this.purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Amiyamod.HenJi(this.misc,this,m);
        Amiyamod.addY(this.misc);
        if (AbstractDungeon.player.maxOrbs < this.magicNumber){
            this.addToBot(new IncreaseMaxOrbAction(3-AbstractDungeon.player.maxOrbs));
        }
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new ChannelAction(new LittleLeOrb()));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {return new Her();}
}
