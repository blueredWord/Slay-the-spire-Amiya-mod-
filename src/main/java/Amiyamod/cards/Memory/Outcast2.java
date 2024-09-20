package Amiyamod.cards.Memory;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.OutcastPower;
import Amiyamod.power.ScoutPower;
import Amiyamod.power.ShadowWaterMusicPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Outcast2 extends CustomCard {
    //=================================================================================================================
    //@ 【】
    //=================================================================================================================
    private static final String NAME = "Outcast2";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColor.COLORLESS;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 0;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.SPECIAL;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//【是否指向敌人】

    public Outcast2() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 13;
        this.magicNumber = this.baseMagicNumber = 2 ;
        this.selfRetain = true;
        this.purgeOnUse = true;
    }

    public void applyPowers() {
        AbstractPlayer p = AbstractDungeon.player;
        if(p.hasPower(OutcastPower.POWER_ID) && p.getPower(OutcastPower.POWER_ID).amount>=6){
            this.isMultiDamage = true;
            this.target = CardTarget.ALL;
        } else {
            this.isMultiDamage = false;
            this.target = CardTarget.ENEMY;
        }
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(OutcastPower.POWER_ID)){
            int n = p.getPower(OutcastPower.POWER_ID).amount;
            if (n<6){
                for (int i = 0;i<n;i++){
                    this.addToBot(
                            new DamageAction(m, new DamageInfo(p,damage, this.damageTypeForTurn))
                    );
                }
            } else {
                for(int i = 0;i<n;i++){
                    AbstractDungeon.actionManager.addToBottom(
                            new DamageAllEnemiesAction(p,this.multiDamage,this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE)
                    );
                }
                this.addToBot(
                        new DamageAction(p, new DamageInfo(p,13, DamageInfo.DamageType.THORNS))
                );
            }
            this.addToBot(new RemoveSpecificPowerAction(p,p,OutcastPower.POWER_ID));
        }
    }

    public boolean canUpgrade() {return false;}
    @Override
    public void upgrade() {}
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            if (p.hasPower(OutcastPower.POWER_ID)){
                return true;
            }
            this.cantUseMessage = CARD_STRINGS.UPGRADE_DESCRIPTION;
            return false;
        }
    }

    public AbstractCard makeCopy() {return new Outcast2();}
}
