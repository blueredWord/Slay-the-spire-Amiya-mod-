package Amiyamod.relics;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;
import javassist.CtBehavior;
import org.apache.commons.lang3.NotImplementedException;

@SuppressWarnings({ "rawtypes", "unchecked" })
public interface BetterClickableRelic<T extends BetterClickableRelic<T>> {
	static HashMap<BetterClickableRelic, ClickManager> MAP = new HashMap<BetterClickableRelic, ClickManager>();
	
	/**
	 * @param duration 想要设置的时长
	 * @return 设置了时长后的同一遗物
	 */
	default T setDuration(int duration) {
		manager().DURATION = duration;
		return (T) this;
	}
	
	/**
	 * 在期间内右键遗物依次触发的效果，第一次触发第一个，第二次触发第二个
	 * @param actions
	 * @return 添加了右键效果后的同一遗物
	 */
	default T addRightClickActions(Runnable... actions) {
		if (manager().actions == null)
			manager().actions = new ArrayList<Runnable>();
		Stream.of(actions).forEachOrdered(manager().actions::add);
		return (T) this;
	}
	
	/**
	 * 在期间内右键遗物，期间结束后依据点击次数决定触发哪一个效果，一次触发第一个，二次触发第二个
	 * @param actions
	 * @return 添加了右键效果后的同一遗物
	 */
	default T addRightClickSelections(Runnable... actions) {
		if (manager().selections == null)
			manager().selections = new ArrayList<Runnable>();
		Stream.of(actions).forEachOrdered(manager().selections::add);
		return (T) this;
	}
	
	/**
	 * 基本的效果，每次右键遗物均会触发
	 */
	default void onEachRightClick() {
	}
	
	/**
	 * 在给定时间段内仅仅右键遗物了一次后触发
	 */
	default void onSingleRightClick() {
	}
	
	/**
	 * 在右键遗物后给定时间段结束后触发
	 */
	default void onDurationEnd() {
	}
	
	default void clickUpdate() {
		if (this instanceof AbstractRelic) {
			AbstractRelic r = (AbstractRelic) this;
			if (manager().RclickStart && InputHelper.justReleasedClickRight) {
				if (r.hb.hovered) {
					manager().Rclick = true;
					System.out.print("右键点击了" + r.name + ", 本次右击是初次右击");
					if (manager().clickTimes++ == 0) {
						manager().updateClickTime();
						System.out.println("，开始计时");
					} else {
						System.out.println("开始" + manager().DURATION + "毫秒内第" + manager().clickTimes + "次右击");
					}
					this.onEachRightClick();
				}
				manager().RclickStart = false;
			}
			if (r.hb != null && r.hb.hovered && InputHelper.justClickedRight) {
				manager().RclickStart = true;
			}
			if (this.checkOnlySingle()) {
				manager().dCheck = false;
				System.out.println(manager().DURATION + "毫秒内只点击了一次");
				this.onSingleRightClick();
			}
			if (manager().Rclick) {
				manager().Rclick = false;
				manager().dCheck = true;
				if (this.checkMulti()) {
					this.runCurrentAction();
				}
			}
			manager().checkReset();
		} else {
			throw new NotImplementedException("BetterClickableRelic interface implemented by non-relic class");
		}
	}
	
	class ClickManager<R extends BetterClickableRelic<R>> {
		boolean RclickStart, Rclick, dCheck, reseted;
		long lastClick;
		int clickTimes, DURATION = 300;
		ArrayList<Runnable> actions, selections;
		BetterClickableRelic<R> r;
		ClickManager(BetterClickableRelic<R> r) {
			this.r = r;
		}
		private boolean checkSingleClick() {
			return deltaTime() >= DURATION && dCheck && clickTimes == 1;
		}
		private long deltaTime() {
			return System.currentTimeMillis() - this.lastClick;
		}
		private boolean checkMultiClick() {
			return this.deltaTime() < DURATION;
		}
		private void updateClickTime() {
			this.reseted = false;
			this.lastClick = System.currentTimeMillis();
		}
		private void checkReset() {
			if (deltaTime() >= DURATION && !reseted) {
				reseted = true;
				System.out.print("右击选择触发，共" + clickTimes + "次，执行第" + clickTimes + "个");
				tryRun(selections);
				r.onDurationEnd();
				this.clickTimes = 0;
			}
		}
		private void runCurrentAction() {
			System.out.print("右击依次触发的第" + clickTimes + "次");
			tryRun(actions);
		}
		private void tryRun(ArrayList<Runnable> list) {
			if (list == null || this.clickTimes > list.size()) {
				System.out.println(", 但执行条件不满足，未执行");
				return;
			}
			Runnable tmp = list.get(this.clickTimes - 1);
			if (tmp != null) {
				tmp.run();
				System.out.println(", 执行成功");
			} else {
				System.out.println(", 但目标为null，无法执行");
			}
		}
	}
	
	static <R extends BetterClickableRelic<R>> ClickManager<R> manager(BetterClickableRelic<R> r) {
		if (!MAP.containsKey(r)) {
			MAP.put(r, new ClickManager<R>(r));
		}
		return MAP.get(r);
	}
	
	default ClickManager<T> manager() {
		return manager(this);
	}
	
	default long delta() {
		return manager().deltaTime();
	}
	
	default boolean checkMulti() {
		return manager().checkMultiClick();
	}
	
	default boolean checkOnlySingle() {
		return manager().checkSingleClick();
	}
	
	default void runCurrentAction() {
		manager().runCurrentAction();
	}

	default boolean hovered() {
		if (this instanceof AbstractRelic) {
			AbstractRelic relic = (AbstractRelic) this;
			return relic.hb.hovered;
		} else {
			throw new NotImplementedException("BetterClickableRelic interface implemented by non-relic class");
		}
	}

	@SpirePatch(clz = OverlayMenu.class, method = "update")
	public static class ClickableRelicUpdatePatch {
		@SpireInsertPatch(locator = Locator.class, localvars = { "r" })
		public static void Insert(OverlayMenu __instance, AbstractRelic r) {
			if (r instanceof BetterClickableRelic) {
				((BetterClickableRelic) r).clickUpdate();
			}
		}

		private static class Locator extends SpireInsertLocator {
			public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
				Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractRelic.class,
						"update");
				return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
			}
		}
	}
}

