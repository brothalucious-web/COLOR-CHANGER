package web.brothalucious.colorchanger;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import java.util.Random;

/** Deterministic challenge state; each hue family is decided once per cycle. */
public final class ColorProgression {
    public static final int GROUP_COUNT = 8;
    public static final double NORMAL_COLOR_CHANCE = 0.45;
    public static final double RANDOM_COLOR_CHANCE = 0.55;
    public static final double DEATH_THRESHOLD = 0.85;
    public static final int RESET_TICKS = 20 * 30;
    private final Random random = new Random();
    private final boolean[] restored = new boolean[GROUP_COUNT];
    private final boolean[] original = new boolean[GROUP_COUNT];
    private final int[] replacementHue = new int[GROUP_COUNT];
    private int cycleTicks;
    private int nextGroupTicks = RESET_TICKS / GROUP_COUNT;
    private int restoredCount;
    private boolean deathTriggered;

    public void tick(MinecraftClient client) {
        if (client.world == null || client.player == null) {
            resetCycle();
            return;
        }
        cycleTicks++;
        if (cycleTicks >= RESET_TICKS) {
            resetCycle();
            return;
        }
        if (restoredCount < GROUP_COUNT && cycleTicks >= nextGroupTicks) {
            restoreNextGroup();
            nextGroupTicks += RESET_TICKS / GROUP_COUNT;
        }
        // Seven of eight equal-weight hue families exceed the 85% threshold.
        if (!deathTriggered && restoredCount / (double) GROUP_COUNT >= DEATH_THRESHOLD) {
            deathTriggered = true;
            PlayerEntity player = client.player;
            if (player != null && !player.isCreative() && !player.isSpectator()) {
                player.kill();
            }
        }
    }

    private void restoreNextGroup() {
        for (int i = 0; i < GROUP_COUNT; i++) {
            if (!restored[i]) {
                restored[i] = true;
                original[i] = random.nextDouble() < NORMAL_COLOR_CHANCE;
                replacementHue[i] = random.nextInt(360);
                restoredCount++;
                return;
            }
        }
    }

    public boolean isRestored(int group) { return restored[Math.floorMod(group, GROUP_COUNT)]; }
    public boolean usesOriginalColor(int group) { return original[Math.floorMod(group, GROUP_COUNT)]; }
    public int replacementHue(int group) { return replacementHue[Math.floorMod(group, GROUP_COUNT)]; }
    public double restorationProgress() { return restoredCount / (double) GROUP_COUNT; }
    public int cycleTicks() { return cycleTicks; }

    public void resetCycle() {
        for (int i = 0; i < GROUP_COUNT; i++) {
            restored[i] = false;
            original[i] = false;
            replacementHue[i] = 0;
        }
        cycleTicks = 0;
        restoredCount = 0;
        deathTriggered = false;
        nextGroupTicks = RESET_TICKS / GROUP_COUNT;
    }
}
