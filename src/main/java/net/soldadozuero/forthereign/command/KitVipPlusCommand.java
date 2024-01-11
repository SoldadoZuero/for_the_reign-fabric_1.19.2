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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.soldadozuero.forthereign.util.IEntityDataSaver;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class KitVipPlusCommand {
    private static final long COOLDOWN_TIME = 30 * 24 * 60 * 60L;

    public static void register(
            CommandDispatcher<ServerCommandSource> dispatcher,
            CommandRegistryAccess commandRegistryAccess,
            CommandManager.RegistrationEnvironment registrationEnvironment
    ) {
        dispatcher.register(CommandManager.literal("kit")
                .then(CommandManager.literal("vip+").executes(KitVipPlusCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ItemStack douradinha = new ItemStack(Items.NETHERITE_SHOVEL);

        IEntityDataSaver playerNbt = (IEntityDataSaver) context.getSource().getPlayer();
        var player = context.getSource().getPlayer();
        assert player != null;
        assert playerNbt != null;
        long currentTime = System.currentTimeMillis() / 1000L;
        long lastUsedTime = playerNbt.getPersistentData().getLong("lastVipPlusKitTime");

        if (currentTime - lastUsedTime < COOLDOWN_TIME) {
            long remainingTime = COOLDOWN_TIME - (currentTime - lastUsedTime);
            context.getSource().sendFeedback(Text.literal("Você precisa esperar mais " + formatTime(remainingTime) + " para usar este comando novamente."), false);
            return 0;
        }

        player.world.playSound((player), player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((((
                                player.getRandom().nextFloat() - player.getRandom().nextFloat()
                        ) * 0.7F + 1.0F) * 2.0F)));

        player.getInventory().insertStack(new ItemStack(Registry.ITEM.get(new Identifier("simplehats:hatbag_epic")), 10));
        player.getInventory().insertStack(new ItemStack(Registry.ITEM.get(new Identifier("travelersbackpack:creeper"))));
        player.getInventory().insertStack(new ItemStack(Items.GOLDEN_APPLE, 32));
        player.getInventory().insertStack(new ItemStack(Items.ELYTRA));
        player.getInventory().insertStack(new ItemStack(Items.NETHERITE_PICKAXE));
        EnchantmentHelper.set(
                Map.of(
                        Enchantments.EFFICIENCY, 3, Enchantments.MENDING, 1
                ),douradinha);
        player.getInventory().insertStack(douradinha);
        player.sendMessageToClient(Text.literal("&n &a Kit resgatado"), true);
        playerNbt.getPersistentData().putLong("lastVipPlusKitTime", currentTime);

        return 1;
    }

    private static String formatTime(long seconds) {
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60;
        long secs = seconds % 60;
        return String.format("%02dd %02d:%02d:%02d", days, hours, minutes, secs);
    }
}
