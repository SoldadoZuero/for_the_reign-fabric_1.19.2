package net.soldadozuero.forthereign.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.soldadozuero.forthereign.command.KitStarterCommand;

public class ModRegistries {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(KitStarterCommand::register);
    }
}
