package net.soldadozuero.forthereign.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.fabricmc.fabric.impl.item.FabricItemInternals;
import net.fabricmc.fabric.impl.registry.sync.FabricRegistry;
import net.fabricmc.fabric.impl.registry.sync.FabricRegistryInit;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

import java.util.Map;

public class KitVipCommand {
    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment
    ) {
        dispatcher.register(CommandManager.literal("kit")
                .then(CommandManager.literal("vip").executes(KitVipCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayer();
        ItemStack douradinha = new ItemStack(Items.DIAMOND_SHOVEL);

        assert player != null;
        player.world.playSound((player), player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((((
                                player.getRandom().nextFloat() - player.getRandom().nextFloat()
                        ) * 0.7F + 1.0F) * 2.0F)));

        player.getInventory().insertStack(new ItemStack(Registry.ITEM.get(new Identifier("simplehats:hatbag_epic")), 5));
        player.getInventory().insertStack(new ItemStack(Registry.ITEM.get(new Identifier("travelersbackpack:bat"))));
        player.getInventory().insertStack(new ItemStack(Items.GOLDEN_CARROT, 32));
        player.getInventory().insertStack(new ItemStack(Items.DIAMOND_SWORD));
        player.getInventory().insertStack(new ItemStack(Items.DIAMOND_PICKAXE));
        EnchantmentHelper.set(
                Map.of(
                        Enchantments.EFFICIENCY, 3, Enchantments.MENDING, 1
                ),douradinha);
        player.getInventory().insertStack(douradinha);

        return 1;
    }

}
