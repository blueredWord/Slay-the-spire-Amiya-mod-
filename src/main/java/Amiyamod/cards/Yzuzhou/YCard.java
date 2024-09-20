package Amiyamod.cards.Yzuzhou;

import Amiyamod.Amiyamod;
import Amiyamod.power.FirstSayPower;
import Amiyamod.power.LittleTePower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class YCard extends CustomCard {
    public YCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void upgrade() {}

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(LittleTePower.ID1)){
            return true;
        }
        return super.canUse(p,m);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        if (p.hasPower(LittleTePower.ID1)){
            this.exhaust = true ;
            Amiyamod.BurnSelf(1);
        }
    }

    public void triggerWhenDrawn() {
        Amiyamod.WhenYcardDrawn();
    }
}
