package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.Shadow15;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.LoseTempHpPower;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

public class PainMagic extends CustomCard implements OnLoseTempHpPower {
    //=================================================================================================================
    //@ 【苦难巫咒】 急性发作 源石技艺 。 NL 对所有敌人造成 !D! 点伤害。若你在本场战斗中失去过至少2次生命，此牌耗能减少1。
    //=================================================================================================================
    private static final String NAME = "PainMagic";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private boolean isdown = false;
    private static final int COST = 2;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;//【是否指向敌人】

    public PainMagic() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 14;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        //this.exhaust = true;
        //this.selfRetain = true;
        //this.heal = 15;
        this.magicNumber = this.baseMagicNumber = 3;
        this.misc = 1;
        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.upgradeMagicNumber(-1);
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }



    //失去生命、临时生命时-1费
    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int damageAmount) {
        int tem = (Integer) TempHPField.tempHp.get(AbstractDungeon.player);
        if (damageAmount > 0 && tem >= damageAmount) {
            this.tookDamage();
        }
        return damageAmount;
    }
    public void tookDamage() {
        if (!this.isdown){
            if (this.misc < this.magicNumber){
                this.misc++;
            } else {
                this.isdown = true;
                this.addToBot(new ReduceCostAction(this.uuid, 1));
                //this.upgradeBaseCost(this.cost-1);
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p,this.multiDamage,this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE)
        );
        //感染进度
        Amiyamod.addY(1);
        Amiyamod.HenJi(1,this,m);
    }
    public AbstractCard makeCopy() {return new PainMagic();}
}
