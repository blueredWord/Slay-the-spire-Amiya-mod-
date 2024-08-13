package Amiyamod.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.UUID;

public abstract class ABeautifulLifePower extends AbstractPower {
    public static ArrayList<UUID> CardGroup= new ArrayList<UUID>();
    public ABeautifulLifePower(AbstractCreature owner, ArrayList<AbstractCard> G) {
        super();
    }
}
