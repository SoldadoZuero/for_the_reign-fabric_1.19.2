package net.soldadozuero.forthereign.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.soldadozuero.forthereign.util.IEntityDataSaver;

import java.util.Objects;

public class TpShogunCommand {
    private static final long COOLDOWN_TIME = 10L;

    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment
    ) {
        dispatcher.register(CommandManager.literal("shogunato")
                .executes(TpShogunCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        double X = -3185.500;
        double Y = 128.00000;
        double Z = -1154.500;

        IEntityDataSaver playerNbt = (IEntityDataSaver) context.getSource().getPlayer();
        var player = context.getSource().getPlayer();
        assert player != null;
        assert playerNbt != null;
        long currentTime = System.currentTimeMillis() / 1000L;
        long lastUsedTime = playerNbt.getPersistentData().getLong("shogunTpCooldown");

        if (currentTime - lastUsedTime < COOLDOWN_TIME) {
            long remainingTime = COOLDOWN_TIME - (currentTime - lastUsedTime);
            context.getSource().sendFeedback(Text.literal("Você precisa esperar mais " + formatTime(remainingTime) + " para usar este comando novamente."), false);
            return 0;
        }

        Objects.requireNonNull(context.getSource().getPlayer()).requestTeleport(X, Y, Z);
        context.getSource().getPlayer().sendMessageToClient(Text.literal("Teleportado para o Shogunato"), true);
        playerNbt.getPersistentData().putLong("shogunTpCooldown", currentTime);


        return 1;
    }

    private static String formatTime(long seconds) {
        long secs = seconds % 60;
        return String.format("%02ds", secs);
    }
}