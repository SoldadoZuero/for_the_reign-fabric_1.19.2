package net.soldadozuero.forthereign.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.Map;

public class KitStarterCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("kit")
                .then(CommandManager.literal("starter").executes(KitStarterCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayer();
        ItemStack douradinha = new ItemStack(Items.GOLDEN_SHOVEL);

        player.world.playSound((player), player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((((
                                player.getRandom().nextFloat() - player.getRandom().nextFloat()
                        ) * 0.7F + 1.0F) * 2.0F)));

        player.getInventory().insertStack(new ItemStack(Items.COBBLESTONE, 192));
        player.getInventory().insertStack(new ItemStack(Items.DARK_OAK_LOG, 64));
        player.getInventory().insertStack(new ItemStack(Items.COOKED_PORKCHOP, 32));
        player.getInventory().insertStack(new ItemStack(Items.IRON_SWORD));
        player.getInventory().insertStack(new ItemStack(Items.IRON_PICKAXE));
        EnchantmentHelper.set(
                Map.of(
                        Enchantments.EFFICIENCY, 3, Enchantments.UNBREAKING, 5, Enchantments.MENDING, 1
                ),douradinha);
        player.getInventory().insertStack(douradinha);

        return 1;
    }

}
