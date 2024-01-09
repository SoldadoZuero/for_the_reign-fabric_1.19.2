package net.soldadozuero.forthereign.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.soldadozuero.forthereign.command.KitStarterCommand;
import net.soldadozuero.forthereign.command.KitVipCommand;

public class ModRegistries {
    public static void registerModStuffs() {
        registerCommands();
    }
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(KitStarterCommand::register);
        CommandRegistrationCallback.EVENT.register(KitVipCommand::register);
    }
}
