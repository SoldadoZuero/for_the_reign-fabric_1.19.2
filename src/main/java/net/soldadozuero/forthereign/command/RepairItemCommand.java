package net.soldadozuero.forthereign.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;


public class RepairItemCommand {
    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment
    ) {
        dispatcher.register(CommandManager.literal("repair").executes(RepairItemCommand::run));
    }

    private static int run (CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        assert player != null;
        ItemStack handItem = player.getMainHandStack();

        if (!handItem.isDamaged()) {
            player.sendMessageToClient(Text.literal("O item não possui durabilidade ou já foi reparado"), true);
            return 0;
        }
        handItem.setDamage(0);
        player.world.playSound((player), player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS, 0.2F, ((((
                        player.getRandom().nextFloat() - player.getRandom().nextFloat()
                ) * 0.7F + 1.0F) * 2.0F)));
        player.sendMessage(Text.literal( "Seu item foi reparado"), true);

        return 1;
    }
}
