package net.soldadozuero.forthereign.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.Objects;

public class TpReignCommand {
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

        Objects.requireNonNull(context.getSource().getPlayer()).requestTeleport(X, Y, Z);
        context.getSource().getPlayer().sendMessageToClient(Text.literal("Teleportado para o Reinado"), true);

        return 1;
    }
}
