package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
    private static final CardColor COLOR = CardColorEnum.Amiya;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.SPECIAL;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//是否指向敌人

    public RedSky(int n) {
        super(ID+n, CARD_STRINGS.NAME, IMG_PATH,0 ,CARD_STRINGS.DESCRIPTION , TYPE, COLOR, RARITY, TARGET);
        //this.misc = n;
        this.timesUpgraded = 0;
        this.baseDamage = this.damage = (int)(4 * Math.pow(2,timesUpgraded));
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：尝试做成"+n+"级的赤霄。"
        );
        this.tags.add(YCardTagClassEnum.RedSky);

        this.isEthereal = true;
        this.exhaust = true;
        if(n>=0){
            for (int i = 0;i<n;i++){
                this.upgrade();
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：赤霄第"+i+"次升级。"
                );
            }
            if(n>0){
                this.rarity = CardRarity.COMMON;
                if(n>1){
                    this.rarity = CardRarity.UNCOMMON;
                }
                if(n>2){
                    this.rarity = CardRarity.RARE;
                }
            }
        }else {
            this.rawDescription =CARD_STRINGS.EXTENDED_DESCRIPTION[6];
        }

    }

    @Override
    public void upgrade() {
        this.timesUpgraded++;
        this.baseDamage = this.damage = (int)(4 * Math.pow(2,timesUpgraded));
        this.upgradedDamage = true;
        this.upgraded = true;
        this.name = CARD_STRINGS.NAME + "*" + CARD_STRINGS.EXTENDED_DESCRIPTION[Math.min(this.timesUpgraded, 5)];
        this.initializeTitle();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        m,
                        new DamageInfo(
                                p,
                                damage,
                                DamageInfo.DamageType.NORMAL
                        )
                )
        );
    }
    public AbstractCard makeCopy() { return new RedSky(this.timesUpgraded); }
}
