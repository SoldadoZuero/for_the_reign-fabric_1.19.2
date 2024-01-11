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

public class TpLobbyCommand {
    private static final long COOLDOWN_TIME = 10L;
    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment
    ) {
        dispatcher.register(CommandManager.literal("lobby")
                .executes(TpLobbyCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        double X = 193.522;
        double Y = 70.00000;
        double Z = -741.634;

        IEntityDataSaver playerNbt = (IEntityDataSaver) context.getSource().getPlayer();
        var player = context.getSource().getPlayer();
        assert player != null;
        assert playerNbt != null;
        long currentTime = System.currentTimeMillis() / 1000L;
        long lastUsedTime = playerNbt.getPersistentData().getLong("lobbyTpCooldown");

        if (currentTime - lastUsedTime < COOLDOWN_TIME) {
            long remainingTime = COOLDOWN_TIME - (currentTime - lastUsedTime);
            context.getSource().sendFeedback(Text.literal("Você precisa esperar mais " + formatTime(remainingTime) + " para usar este comando novamente."), false);
            return 0;
        }

        Objects.requireNonNull(player).requestTeleport(X, Y, Z);
        player.sendMessageToClient(Text.literal("Teleportado para o Lobby"), true);
        playerNbt.getPersistentData().putLong("lobbyTpCooldown", currentTime);

        return 1;
    }

    private static String formatTime(long seconds) {
        long secs = seconds % 60;
        return String.format("%02ds", secs);
    }
}
