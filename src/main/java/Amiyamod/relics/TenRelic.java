package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.action.relic.TheTenAction;
import Amiyamod.cards.AmiyaPower;
import Amiyamod.power.YSayPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import basemod.abstracts.CustomRelic;

public class TenRelic extends CustomRelic implements BetterClickableRelic<TenRelic> {
	public static final String NAME = "TenRelic";
	public static final String ID = Amiyamod.makeID(NAME);
	private static final String IMG_PATH = "img/relics/"+NAME+".png";
	private boolean cardSelected = true;
	public static RoomPhase phase;
	private static boolean usedThisCombat = false;
	private static CurrentScreen pre;
	private boolean canU = false;

	public TenRelic() {
		super(
				ID,
				ImageMaster.loadImage("img/relics/"+NAME+".png"),
				ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
				RelicTier.STARTER,
				LandingSound.SOLID
		);
		this.counter = 10;
		this.setDuration(800).addRightClickActions(null, this::onClick);
	}

	public void atPreBattle() {
		usedThisCombat = false;
		if (this.counter>0){
			this.pulse = true;
			this.beginPulse();
		}
	}
	public void atTurnStartPostDraw() {
		this.canU = true;
	}

	public void onPlayerEndTurn() {
		this.canU = false;
	}

	public  void onClick(){
		if(AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && !usedThisCombat && this.canU){
			this.grayscale = true;
			usedThisCombat = true;
			this.pulse = false;
			this.stopPulse();
			if (this.counter>0){
				this.counter -=1;
				this.flash();
				this.addToBot(new TheTenAction());
				this.addToBot(new MakeTempCardInHandAction(new AmiyaPower().makeCopy()));
				if (this.counter == 0){
					this.isDone = true;
					this.counter = -1;
				}
			}
		}
	}
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	public void onVictory() {
		canU = false;
		if (this.counter>0){
			this.grayscale = false;
		}
		this.pulse = false;
	}

	public void update() {
		super.update();
		if (!cardSelected) {
			if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
				AbstractDungeon.effectList
						.add(new ShowCardAndObtainEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0),
								Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
			} else if (AbstractDungeon.screen != pre) {
				return;
			}
			cardSelected = true;
			AbstractDungeon.getCurrRoom().phase = phase;
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
	}


}