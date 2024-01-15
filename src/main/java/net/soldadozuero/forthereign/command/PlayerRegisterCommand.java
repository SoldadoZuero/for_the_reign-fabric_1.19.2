package net.soldadozuero.forthereign.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.soldadozuero.forthereign.util.IEntityDataSaver;


public class PlayerRegisterCommand {
    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment
    ) {
        dispatcher.register(CommandManager.literal("register")
                .then(CommandManager.argument("password", StringArgumentType.string())
                        .then(CommandManager.argument("password2", StringArgumentType.string())).executes(PlayerRegisterCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        IEntityDataSaver playerNbt = (IEntityDataSaver) context.getSource().getPlayer();
        var arg1 = StringArgumentType.getString(context, "password");
        var arg2 = StringArgumentType.getString(context, "password2");
        assert playerNbt != null;

        if (playerNbt.getPersistentData().getBoolean("playerRegistered")) {
            context.getSource().sendFeedback(Text.literal("&e Você já está registrado, use /login"), true);
            return 0;
        }
        if (!arg1.equals(arg2)) {
            context.getSource().sendFeedback(Text.literal("&4 As senhas não são iguais"), true);
            return 0;
        }

        playerNbt.getPersistentData().putBoolean("playerRegistered", true);
        playerNbt.getPersistentData().putString("playerPassword", arg1);
        context.getSource().sendFeedback(Text.literal("&a Sua senha foi registrada, use /login para logar"), true);

        return 1;
    }
}
