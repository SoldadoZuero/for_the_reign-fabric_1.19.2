package net.soldadozuero.forthereign.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.soldadozuero.forthereign.command.*;
import net.soldadozuero.forthereign.event.ModPlayerEventCopyFrom;

public class ModRegistries {
    public static void registerModStuffs() {
        registerCommands();
        registerEvents();
    }
    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(KitStarterCommand::register);
        CommandRegistrationCallback.EVENT.register(KitVipCommand::register);
        CommandRegistrationCallback.EVENT.register(KitVipPlusCommand::register);
        CommandRegistrationCallback.EVENT.register(TpLobbyCommand::register);
        CommandRegistrationCallback.EVENT.register(TpReignCommand::register);
        CommandRegistrationCallback.EVENT.register(TpShogunCommand::register);
        CommandRegistrationCallback.EVENT.register(PlayerRegisterCommand::register);
        CommandRegistrationCallback.EVENT.register(PlayerLoginCommand::register);
    }

    private static void registerEvents() {
        ServerPlayerEvents.COPY_FROM.register(new ModPlayerEventCopyFrom());
    }
}
