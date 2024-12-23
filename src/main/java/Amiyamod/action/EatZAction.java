package Amiyamod.action;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ExAnyWhereCardAction;
import Amiyamod.cards.YCard.EatZuzhou;
import Amiyamod.cards.YCard.FirstSayA;
import Amiyamod.cards.Yzuzhou.*;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.EatZuzhouPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;

import java.util.Objects;

public class EatZAction extends AbstractGameAction {
    private static final CardStrings CARD_STRINGS = EatZuzhou.CARD_STRINGS;;
    private static final String IMG_PATH = EatZuzhou.IMG_PATH;
    private final String cardID = EatZuzhou.ID;
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public EatZAction(AbstractCreature m) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.target = m;
    }

    public void update() {
        CardGroup G = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for (AbstractCard card : p.masterDeck.group){
            if (card.hasTag(YCardTagClassEnum.YZuZhou) && !(card instanceof FirstSayA) ){
                boolean add = true;
                if (!G.isEmpty()){
                    for (AbstractCard c : G.group){
                        if (Objects.equals(c.cardID, this.cardID + card.cardID)){
                            add = false;
                            break;
                        }
                    }
                }
                if (add){
                    LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                            "螯合诅咒：添加 {} 。",card
                    );
                    String ID = this.cardID+card.cardID;
                    String Des;
                    int mag ;
                    if (card instanceof Yjianwang){
                        // 健忘 每回合有 M次 50%几率给予你 [E] 。
                        mag = 1;
                        Des = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
                    } else if (card instanceof Ydead) {
                        // 回合结束时,失去M点生命
                        mag = 9;
                        Des = CARD_STRINGS.EXTENDED_DESCRIPTION[1];
                    } else if (card instanceof Ytiruo) {
                        //  造成的伤害减少M点。
                        mag = 3;
                        Des = CARD_STRINGS.EXTENDED_DESCRIPTION[2];
                    } else if (card instanceof Yjiejin) {
                        // 受到的伤害增加3点。
                        mag = 3;
                        Des = CARD_STRINGS.EXTENDED_DESCRIPTION[3];
                    } else if (card instanceof Yangry) {
                        // 若一次失去至少8点生命，目标获得 M 层 易伤 。
                        mag = 1;
                        Des = CARD_STRINGS.EXTENDED_DESCRIPTION[4];
                    }  else if (card instanceof Ysex) {
                        // 每当你打出 源石技艺 ，失去所有 格挡 。
                        mag = -1;
                        Des = CARD_STRINGS.EXTENDED_DESCRIPTION[5];
                    }  else if (card instanceof Ysnake) {
                        // 回合开始时，使你额外抽取 !M! 张牌。
                        mag = 1;
                        Des = CARD_STRINGS.EXTENDED_DESCRIPTION[6];
                    }  else if (card instanceof Ychengyin) {
                        // 每当你打出 源石技艺 ，获得 M 层 虚弱 。
                        mag = 1;
                        Des = CARD_STRINGS.EXTENDED_DESCRIPTION[7];
                    }  else {
                        // 回合开始时，使你额外抽取 !M! 张牌。
                        mag = 1;
                        Des = CARD_STRINGS.EXTENDED_DESCRIPTION[8];
                    }

                    String Na = CARD_STRINGS.NAME + ":"+ card.name;
                    int COST = -2;
                    AbstractCreature m = this.target;
                    AbstractPower pow = new EatZuzhouPower(card,m,mag);
                    CustomCard CC = new CustomCard(ID,Na,IMG_PATH, COST,Des, AbstractCard.CardType.SKILL, p.getCardColor(), AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.NONE) {
                        @Override
                        public void upgrade() {}
                        @Override
                        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}
                        @Override
                        public void onChoseThisOption() {
                            this.addToBot(new ExAnyWhereCardAction(card.uuid));
                            this.addToBot(new ApplyPowerAction(m,AbstractDungeon.player,pow));
                        }
                        @Override
                        public boolean canUse(AbstractPlayer p, AbstractMonster m) {
                            return false;
                        }
                    };
                    CC.cardsToPreview = card.makeStatEquivalentCopy();
                    CC.purgeOnUse = true;
                    CC.baseMagicNumber = CC.magicNumber = mag ;
                    G.addToBottom(CC);
                }
            }
        }
        if (!G.isEmpty()){
            this.addToTop(new ChooseOneAction(G.group));
        }
        this.isDone = true;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = uiStrings.TEXT;
    }
}
