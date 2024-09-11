package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.action.relic.TheTenAction;
import Amiyamod.power.YSayPower;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import basemod.abstracts.CustomRelic;
import org.apache.logging.log4j.LogManager;

public class TenRelic extends CustomRelic implements BetterClickableRelic<TenRelic> , OnApplyPowerRelic {
	public static final String NAME = "TenRelic";
	public static final String ID = Amiyamod.makeID(NAME);
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

	public TenRelic(String id, Texture img, Texture imgout, RelicTier relicTier, LandingSound landingSound) {
		super(id,img,imgout,relicTier,landingSound);
		this.setDuration(800).addRightClickActions(null, this::onClick);
	}

	public void atPreBattle() {
		usedThisCombat = false;
		if (this.counter>0){
			this.work(true);
		}
	}
	public void atTurnStartPostDraw() {
		this.canU = true;
		AbstractPlayer p =AbstractDungeon.player;
		this.work(!p.hasPower(YSayPower.POWER_ID));
	}

	public void onPlayerEndTurn() {
		this.canU = false;
		this.work(false);
	}

	public void work(boolean i){
		if (this.counter>0){
			this.grayscale = !i;
			this.pulse = i;
			if (i){
				this.beginPulse();
			}else {
				this.stopPulse();
			}
		} else {
			this.grayscale = true;
			this.pulse = false;
			this.stopPulse();
		}
	}

	public void onClick(){
		if(AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && !usedThisCombat && this.canU ){
			//this.grayscale = true;
			//usedThisCombat = true;
			//this.pulse = false;
			//this.stopPulse();
			LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
					"检测： {}",!AbstractDungeon.player.hasPower(YSayPower.POWER_ID)
			);
			if (this.counter>0 && !AbstractDungeon.player.hasPower(YSayPower.POWER_ID)){
				canU = false;
				this.counter -=1;
				this.flash();
				this.addToBot(new TheTenAction());
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


	@Override
	public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
		if (abstractPower instanceof YSayPower){
			this.work(false);
		}
		return true;
	}
}