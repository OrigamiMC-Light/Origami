package dev.origami.server.tools;

import java.util.logging.Logger;

public final class OrigamiBanner {
    private static final Logger LOGGER = Logger.getLogger("Origami");

    private OrigamiBanner() {
    }

    public static void print() {
        LOGGER.info("");
        LOGGER.info("§6  #######  §e########  ####  ######    ###    ##     ## ####");
        LOGGER.info("§6 ##     ## §e##     ##  ##  ##    ##  ## ##   ###   ###  ##");
        LOGGER.info("§6 ##     ## §e##     ##  ##  ##       ##   ##  #### ####  ##");
        LOGGER.info("§6 ##     ## §e########   ##  ##   #### #### ## ## ### ##  ##");
        LOGGER.info("§6 ##     ## §e##   ##    ##  ##    ##  ######### ##     ##  ##");
        LOGGER.info("§6 ##     ## §e##    ##   ##  ##    ##  ##     ## ##     ##  ##");
        LOGGER.info("§6  #######  §e##     ## ####  ######   ##     ## ##     ## ####");
        LOGGER.info("");
        LOGGER.info("§8  » §7A folded, faster fork of §6Paper§7 — §fdev.origami");
        LOGGER.info("");
    }
}