package dev.origami.api;

import io.papermc.paper.ServerBuildInfo;

import java.util.logging.Logger;

/**
 * Marker/entry point for Origami-specific API additions.
 * Add your public API surface here; server-side implementations live in the
 * origami-server module.
 */
public final class Origami {

    public static final String NAME = "Origami";
    public static final Logger LOGGER = Logger.getLogger(NAME);

    private Origami() {
    }

    private static ServerBuildInfo buildInfo() {
        return ServerBuildInfo.buildInfo();
    }

    /**
     * Returns the Origami brand name.
     */
    public static String brand() {
        return NAME;
    }

    /**
     * Returns the full server version.
     */
    public static String version() {
        return buildInfo().asString(
                ServerBuildInfo.StringRepresentation.VERSION_SIMPLE
        );
    }

    /**
     * Returns the Minecraft version.
     */
    public static String minecraftVersion() {
        return buildInfo().minecraftVersionId();
    }

    public static String gitBranch() {
        return buildInfo().gitBranch().orElse("unknown");
    }

    public static String gitCommit() {
        return buildInfo().gitCommit().orElse("unknown");
    }

    public static String buildNumber() {
        var buildNumber = buildInfo().buildNumber();

        return buildNumber.isPresent()
                ? String.valueOf(buildNumber.getAsInt())
                : "unknown";
    }

    /**
     * Returns the complete Origami version.
     */
    public static String fullVersion() {
        return brand() + " " + version();
    }
}
