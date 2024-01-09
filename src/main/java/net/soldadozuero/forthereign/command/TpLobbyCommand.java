package net.soldadozuero.forthereign.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Objects;

public class TpLobbyCommand {
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

        Objects.requireNonNull(context.getSource().getPlayer()).requestTeleport(X, Y, Z);
        context.getSource().getPlayer().sendMessageToClient(Text.literal("Teleportado para o Lobby"), true);

        return 1;
    }
}
