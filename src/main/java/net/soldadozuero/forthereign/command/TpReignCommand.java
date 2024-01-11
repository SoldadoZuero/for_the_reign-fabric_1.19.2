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

public class TpReignCommand {
    private static final long COOLDOWN_TIME = 10L;
    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment
    ) {
        dispatcher.register(CommandManager.literal("medieval")
                .executes(TpReignCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        double X = -29125.500;
        double Y = 96.00000;
        double Z = -28240.500;

        IEntityDataSaver playerNbt = (IEntityDataSaver) context.getSource().getPlayer();
        var player = context.getSource().getPlayer();
        assert player != null;
        assert playerNbt != null;
        long currentTime = System.currentTimeMillis() / 1000L;
        long lastUsedTime = playerNbt.getPersistentData().getLong("reignTpCooldown");

        if (currentTime - lastUsedTime < COOLDOWN_TIME) {
            long remainingTime = COOLDOWN_TIME - (currentTime - lastUsedTime);
            context.getSource().sendFeedback(Text.literal("Você precisa esperar mais " + formatTime(remainingTime) + " para usar este comando novamente."), false);
            return 0;
        }

        Objects.requireNonNull(player).requestTeleport(X, Y, Z);
        player.sendMessageToClient(Text.literal("Teleportado para o Reinado"), true);
        playerNbt.getPersistentData().putLong("reignTpCooldown", currentTime);


        return 1;
    }

    private static String formatTime(long seconds) {
        long secs = seconds % 60;
        return String.format("%02ds", secs);
    }
}
