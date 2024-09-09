package Amiyamod.action.cards;

import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class MagicYuJinAction extends AbstractGameAction {
    private AbstractCard c;
    public static final String[] TEXT;
    private int damage;
    private AbstractCard CtoEx = null ;
    public MagicYuJinAction(AbstractCard card, AbstractMonster m) {
        this.c = card;
        this.target = m;
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DAMAGE;
        if (Settings.FAST_MODE) {
            this.startDuration = this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = this.duration = Settings.ACTION_DUR_FASTER;
        }
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.discardPile.isEmpty()) {
            if (this.duration == this.startDuration) {
                if (this.c.upgraded) {
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    AbstractDungeon.gridSelectScreen.open(p.discardPile, 1, true, TEXT[2]);
                } else {
                    CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    for (AbstractCard card:p.discardPile.group){
                        if (card.hasTag(YCardTagClassEnum.YZuZhou)){
                            G.addToTop(card);
                        }
                    }
                    if (G.isEmpty()){
                        this.CtoEx = p.discardPile.getRandomCard(true);
                    } else {
                        this.CtoEx = G.getRandomCard(true);
                    }
                }
            } else {
                if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 1){
                    this.CtoEx = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                } else if (this.CtoEx == null) {
                    CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    for (AbstractCard card:p.discardPile.group){
                        if (card.hasTag(YCardTagClassEnum.YZuZhou)){
                            G.addToTop(card);
                        }
                    }
                    if (G.isEmpty()){
                        this.CtoEx = p.discardPile.getRandomCard(true);
                    } else {
                        this.CtoEx = G.getRandomCard(true);
                    }
                }

                if (this.CtoEx.rarity == AbstractCard.CardRarity.CURSE || this.CtoEx.rarity == AbstractCard.CardRarity.RARE){
                    this.damage = 2* this.c.damage;
                } else if (this.CtoEx.rarity == AbstractCard.CardRarity.UNCOMMON) {
                    this.damage = (int)(1.66* this.c.damage);
                }   else if (this.CtoEx.rarity == AbstractCard.CardRarity.COMMON) {
                    this.damage = (int)(1.33 * this.c.damage);
                } else {
                    this.damage = this.c.damage;
                }

                this.addToTop(
                        new DamageAction(
                                this.target,
                                new DamageInfo(this.target,this.damage,this.c.damageTypeForTurn)
                        )
                );
                p.discardPile.moveToExhaustPile(this.CtoEx);
                this.isDone = true;
            }
            this.tickDuration();
        }else {
            this.isDone = true;
        }

    }

    static {
        TEXT = CardCrawlGame.languagePack.getPowerStrings("AmiyaMod:UI").DESCRIPTIONS;
    }

}
