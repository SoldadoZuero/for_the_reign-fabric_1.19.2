package net.soldadozuero.forthereign.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Objects;

public class TpShogunCommand {
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

        Objects.requireNonNull(context.getSource().getPlayer()).requestTeleport(X, Y, Z);
        context.getSource().getPlayer().sendMessageToClient(Text.literal("Teleportado para o Shogunato"), true);

        return 1;
    }
}
