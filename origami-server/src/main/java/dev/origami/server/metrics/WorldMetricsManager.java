package dev.origami.server.metrics;

import org.bukkit.World;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Central registry of per-world tick metrics.
 * Singleton by design: exactly one tick loop feeds it, many consumers read it.
 */
public final class WorldMetricsManager {
    private static final WorldMetricsManager INSTANCE = new WorldMetricsManager();

    private final Map<java.util.UUID, WorldMetrics> metricsByWorld = new ConcurrentHashMap<>();

    private WorldMetricsManager() {
    }

    public static WorldMetricsManager getInstance() {
        return INSTANCE;
    }

    /**
     * Called once per world tick, from the server tick loop.
     */
    public void recordTick(World world, long durationNanos) {
        this.metricsByWorld
                .computeIfAbsent(world.getUID(), id -> new WorldMetrics(world))
                .recordTick(durationNanos);
    }

    public WorldMetrics getMetrics(World world) {
        return this.metricsByWorld.get(world.getUID());
    }

    public Collection<WorldMetrics> getAllMetrics() {
        return this.metricsByWorld.values();
    }
}