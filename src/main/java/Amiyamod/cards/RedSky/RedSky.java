package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.ShadowWaterMusicPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;

public class RedSky extends CustomCard {
    private static final String NAME = "RedSky";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    //private static final String DESCRIPTION = "造成 !D! 点伤害。";//卡片描述
    private static final CardType TYPE = CardType.ATTACK;//卡片类型
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.SPECIAL;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//是否指向敌人

    public RedSky(boolean n) {
        super(ID, CARD_STRINGS.NAME, IMG_PATH,0 ,CARD_STRINGS.EXTENDED_DESCRIPTION[6], TYPE, COLOR, RARITY, TARGET);
        //this.misc = n;
        this.timesUpgraded = 0;
        //this.tags.add(YCardTagClassEnum.RedSky);
        this.isEthereal = true;
        this.exhaust = true;

        this.initializeTitle();
    }
    public RedSky(int n) {
        super(ID+n, CARD_STRINGS.NAME, IMG_PATH,0 ,CARD_STRINGS.DESCRIPTION , TYPE, COLOR, RARITY, TARGET);
        //this.misc = n;
        this.timesUpgraded = 0;
        this.baseDamage = this.damage = (int)(4 * Math.pow(2,timesUpgraded));
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：尝试做成"+n+"级的赤霄。"
        );
        this.tags.add(YCardTagClassEnum.RedSky1);

        this.isEthereal = true;
        this.exhaust = true;
        if(n>=0){
            this.name = CARD_STRINGS.NAME + "*" + CARD_STRINGS.EXTENDED_DESCRIPTION[Math.min(this.timesUpgraded, 5)];
            for (int i = 0;i<n;i++){
                this.upgrade();
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：赤霄第"+i+"次升级。"
                );
            }
        }else {
            this.rawDescription =CARD_STRINGS.EXTENDED_DESCRIPTION[6];
        }
        this.initializeTitle();
    }

    @Override
    public void upgrade() {
        this.timesUpgraded++;
        CardRarity ry = CardRarity.SPECIAL;
        if (this.timesUpgraded>3){
            ry = CardRarity.RARE;
        } else if (this.timesUpgraded>1) {
            ry = CardRarity.UNCOMMON;
        } else if (this.timesUpgraded>0) {
            ry = CardRarity.COMMON;
        }

        this.rarity = ry;

        this.baseDamage = this.damage = (int)(4 * Math.pow(2,timesUpgraded));
        this.upgradedDamage = true;
        this.upgraded = true;
        this.name = CARD_STRINGS.NAME + "*" + CARD_STRINGS.EXTENDED_DESCRIPTION[Math.min(this.timesUpgraded, 5)];
        this.initializeTitle();
    }
    public void applyPowers() {
        AbstractPlayer p =AbstractDungeon.player;
        if(p.hasPower(ShadowWaterMusicPower.POWER_ID)){
            this.isMultiDamage = true;
            this.target = CardTarget.ALL_ENEMY;
        } else {
            this.isMultiDamage = false;
            this.target = CardTarget.ENEMY;
        }
        super.applyPowers();
    }
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(ShadowWaterMusicPower.POWER_ID)){
            //applyPowers();
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(p,this.multiDamage,this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY)
            );
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(
                            m,
                            new DamageInfo(
                                    p,
                                    damage,
                                    this.damageTypeForTurn
                            )
                    )
            );
        }
        for (AbstractCard c : p.drawPile.group){
            if (c instanceof CloudBreakIn){
                c.triggerOnOtherCardPlayed(this);
            }
        }
        for (AbstractCard c : p.discardPile.group){
            if (c instanceof CloudBreakIn){
                c.triggerOnOtherCardPlayed(this);
            }
        }
    }
    public AbstractCard makeCopy() { return new RedSky(this.timesUpgraded); }
}
