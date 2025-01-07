package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.OnCombatStartInterface;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.HornPower;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;

public class Horn extends CustomCard {
    //=================================================================================================================
    //@ 【温迪戈之角】 每当你失去生命，获得 !M! 点临时 力量 。
    //=================================================================================================================
    private static final String NAME = "Horn";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//【是否指向敌人】

    public Horn() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.magicNumber = this.baseMagicNumber = 3;
        this.damage = this.baseDamage = 9;
        this.misc = 0;
        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YCard);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(6);
            //this.exhaust = true;
            //this.isEthereal = false;
            //this.upgradeMagicNumber(-1);
            this.upgradeDamage(4);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = this.isdown() ?  AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy():AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }
/*
    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int damageAmount) {
        int tem=(Integer) TempHPField.tempHp.get(AbstractDungeon.player);
        if (!this.isdown && damageAmount > 0){
            this.tookDamage();
        }
        return damageAmount;
    }
    public void tookDamage() {
        this.misc++;
        if (this.misc >= this.magicNumber){
            this.isdown = true;
        }
    }

 */
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage,this.damageTypeForTurn)));
        if (this.isdown()){
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage,this.damageTypeForTurn)));
        }
        //this.addToBot(new ApplyPowerAction(p,p,new HornPower(p,this.magicNumber)));
    }

    public AbstractCard makeCopy() {return new Horn();}

    public boolean isdown(){
        return Amiyamod.Shp > AbstractDungeon.player.currentHealth;
    }
}
