package net.soldadozuero.forthereign.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.soldadozuero.forthereign.command.*;

public class ModRegistries {
    public static void registerModStuffs() {
        registerCommands();
    }
    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(KitStarterCommand::register);
        CommandRegistrationCallback.EVENT.register(KitVipCommand::register);
        CommandRegistrationCallback.EVENT.register(KitVipPlusCommand::register);
        CommandRegistrationCallback.EVENT.register(TpLobbyCommand::register);
        CommandRegistrationCallback.EVENT.register(TpReignCommand::register);
        CommandRegistrationCallback.EVENT.register(TpShogunCommand::register);
    }
}
