package Amiyamod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MyAutoPlayAction extends AbstractGameAction {
    private AbstractCard card;
    private CardGroup group;

    public MyAutoPlayAction(AbstractCard card, CardGroup group) {
        this.card = card;
        this.group = group;
    }

    public void update() {

        if (this.group.contains(this.card)) {

            this.card.targetAngle = 0.0F;
            if (this.card.target == AbstractCard.CardTarget.ENEMY){
                this.target =  AbstractDungeon.getRandomMonster();
            }
            if (this.target!= null ) {
                this.card.calculateCardDamage((AbstractMonster)this.target);
            }
            AbstractDungeon.actionManager.addToTop(new QueueCardAction(this.card,this.target));
        }
        this.isDone = true;
    }

}
