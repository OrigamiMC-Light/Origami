package dev.origami.server.config;

import dev.origami.api.Origami;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public final class OrigamiConfig {

    private static final Logger LOGGER = Logger.getLogger(Origami.brand());
    private static boolean mergeItemsAggressively = true;

    private OrigamiConfig () { }

    public static void load(Path serverRoot) {
        Path configFile = serverRoot.resolve("origami.yml");
        try {
            if (!Files.exists(configFile)) {
                Files.writeString(configFile, """
                    # Origami configuration
                    merge-items-aggressively: true
                    """);
                LOGGER.info("[Origami] Created default origami.yml");
            }

            YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                    .path(configFile)
                    .build();
            ConfigurationNode root = loader.load();

            mergeItemsAggressively = root.node("merge-items-aggressively").getBoolean(true);

            LOGGER.info("[Origami] Loaded config: merge-items-aggressively=" + mergeItemsAggressively);
        } catch (IOException e) {
            LOGGER.warning("[Origami] Failed to load origami.yml: " + e.getMessage());
        }
    }

    public static boolean isMergeItemsAggressively() {
        return mergeItemsAggressively;
    }
}