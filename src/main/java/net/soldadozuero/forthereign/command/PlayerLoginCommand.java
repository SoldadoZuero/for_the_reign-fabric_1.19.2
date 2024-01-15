package net.soldadozuero.forthereign.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.soldadozuero.forthereign.util.IEntityDataSaver;

public class PlayerLoginCommand {
    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment
    ) {
        dispatcher.register(CommandManager.literal("login")
                .then(CommandManager.argument("password", StringArgumentType.string())).executes(PlayerLoginCommand::run));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        IEntityDataSaver playerNbt = (IEntityDataSaver) context.getSource().getPlayer();
        assert player != null;
        assert playerNbt != null;
        var arg1 = StringArgumentType.getString(context, "password");
        var arg2 = playerNbt.getPersistentData().getString("playerPassword");

        if (playerNbt.getPersistentData().getBoolean("playerLoggedIn")) {
            context.getSource().sendFeedback(Text.literal("&e Você já está logado"), true);
            return 0;
        }
        if (!arg1.equals(arg2)) {
            context.getSource().sendFeedback(Text.literal("&c Senha incorreta, tente novamente"), true);
            return 0;
        }

        playerNbt.getPersistentData().putBoolean("playerLoggedIn", true);
        player.clearStatusEffects();
        context.getSource().sendFeedback(Text.literal("&a Logado com sucesso. Bem-Vindo"), true);

        return 1;
    }
}
