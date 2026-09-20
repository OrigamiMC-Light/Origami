package dev.origami.api;

/**
 * Marker/entry point for Origami-specific API additions.
 * Add your public API surface here; server-side implementations live in the
 * origami-server module.
 */
public final class Origami {
    private Origami() {
    }

    public static String brand() {
        return "Origami";
    }
}
