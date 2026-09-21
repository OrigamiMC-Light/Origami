package dev.origami.server.metrics;

import org.bukkit.World;

import java.util.UUID;

/**
 * Per-world tick performance, tracked over three rolling windows.
 * Window sizes assume ~20 ticks/second: 100 = 5s, 1200 = 1m, 6000 = 5m.
 */
public final class WorldMetrics {
    private static final int WINDOW_5S_TICKS = 100;
    private static final int WINDOW_1M_TICKS = 1_200;
    private static final int WINDOW_5M_TICKS = 6_000;

    private final UUID worldId;
    private final String worldName;
    private final TickWindow window5s = new TickWindow(WINDOW_5S_TICKS);
    private final TickWindow window1m = new TickWindow(WINDOW_1M_TICKS);
    private final TickWindow window5m = new TickWindow(WINDOW_5M_TICKS);

    public WorldMetrics(World world) {
        this.worldId = world.getUID();
        this.worldName = world.getName();
    }

    public void recordTick(long durationNanos) {
        this.window5s.record(durationNanos);
        this.window1m.record(durationNanos);
        this.window5m.record(durationNanos);
    }

    public UUID worldId() {
        return this.worldId;
    }

    public String worldName() {
        return this.worldName;
    }

    public double mspt5s() {
        return this.window5s.averageMillis();
    }

    public double mspt1m() {
        return this.window1m.averageMillis();
    }

    public double mspt5m() {
        return this.window5m.averageMillis();
    }

    public double tps5s() {
        return this.window5s.tps();
    }

    public double tps1m() {
        return this.window1m.tps();
    }

    public double tps5m() {
        return this.window5m.tps();
    }

    public boolean hasData() {
        return this.window5s.sampleCount() > 0;
    }
}