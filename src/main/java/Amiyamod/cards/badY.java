package Amiyamod.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.Yzuzhou.ASay;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.LibraryTypeEnum;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import org.apache.logging.log4j.LogManager;

public class badY extends CustomCard {
    //=================================================================================================================
    //@ 【污染碎片】 保留 。 NL 获得 !M! 金币 。 NL 每当这张牌被 保留 时，增加 !M! 感染进度 。 NL 源石诅咒 。
    //=================================================================================================================
    private static final String NAME = "badY";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = AbstractCard.CardColor.CURSE;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 2;//【卡片费用】
    private static final CardType TYPE = CardType.POWER;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.CURSE;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.NONE;//【是否指向敌人】

    public badY() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 2;
        //this.heal = 15;
        this.misc = 60;
        this.selfRetain = true;
        //this.selfRetain = true;

        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YZuZhou);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {}
    public boolean canUpgrade() {
        return false;
    }

    public void onRetained() {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "onRetained触发"
        );
        Amiyamod.addY(this.magicNumber);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
            if (c instanceof badY && c.uuid != this.uuid){

                CardCrawlGame.sound.play("GOLD_GAIN");
                for(int i = 0; i < this.misc; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(p, (float) Settings.WIDTH / 2.0F,(float) Settings.HEIGHT / 2.0F,p.hb.cX, p.hb.cY, true));
                }
                AbstractDungeon.player.gainGold(this.misc);

                break;
            }
        }
    }

    public AbstractCard makeCopy() {return new badY();}
}
