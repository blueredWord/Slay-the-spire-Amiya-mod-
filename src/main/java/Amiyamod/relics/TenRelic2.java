package Amiyamod.relics;

import Amiyamod.Amiyamod;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;

public class TenRelic2 extends TenRelic {
	public static final String NAME = "TenRelic2";
	public static final String ID = Amiyamod.makeID(NAME);

	public TenRelic2() {
		super(
				ID,
				ImageMaster.loadImage("img/relics/"+NAME+".png"),
				ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
				RelicTier.RARE,
				LandingSound.SOLID
		);
		this.counter = 10;
		this.cost = 4;
	}
	public void obtain() {
		if (AbstractDungeon.player.hasRelic(TenRelic.ID)) {
			int i = 0;
			for (AbstractRelic r : AbstractDungeon.player.relics){
				if (r instanceof TenRelic){
					instantObtain(AbstractDungeon.player, i, false);
					break;
				}
				i++;
			}
		} else {
			super.obtain();
		}
	}
	public void onTrigger() {
		super.onTrigger();
		AbstractPlayer p = AbstractDungeon.player;
		this.addToBot(new HealAction(p,p,this.cost));
	}
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0]+4+DESCRIPTIONS[1];
	}
	public boolean canSpawn() {
		return AbstractDungeon.player.hasRelic(TenRelic.ID) && AbstractDungeon.player.getRelic(TenRelic.ID).counter < 10 ;
	}
	// 返回当前遗物的副本
	public AbstractRelic makeCopy() {
		return new TenRelic2();
	}
}