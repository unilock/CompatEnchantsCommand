package cc.unilock.compatenchants;

import cc.unilock.compatenchants.util.EnchantmentUtils;
import cc.unilock.compatenchants.util.TextUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod("compatenchants")
public class CompatEnchants {
    //public static final Logger LOGGER = LogManager.getLogger();

    public CompatEnchants() {
        MinecraftForge.EVENT_BUS.addListener(this::registerClientCommands);
    }

    private void registerClientCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(CommandManager.literal("compatenchants")
                .executes(ctx -> compatEnchantsCommand(ctx.getSource(), MinecraftClient.getInstance().player.getMainHandStack()))
                .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(event.getBuildContext()))
                        .executes(ctx -> compatEnchantsCommand(ctx.getSource(), ItemStackArgumentType.getItemStackArgument(ctx, "item").getItem().getDefaultStack()))
                ));
    }

    private static int compatEnchantsCommand(ServerCommandSource src, ItemStack stack) {
        Set<Enchantment> compatible = EnchantmentUtils.getCompatibleEnchantments(stack);
        if (compatible.isEmpty()) {
            src.sendFeedback(Text.translatable("compatenchants.unenchantable", stack.getItem().getName()).formatted(Formatting.RED), false);
            return 1;
        }

        src.sendFeedback(Text.translatable("compatenchants.item", TextUtils.getHoverableText(stack)), false);

        Set<Enchantment> current = EnchantmentHelper.get(stack).keySet();
        Set<Enchantment> incompatible = new HashSet<>();
        if (!current.isEmpty()) {
            src.sendFeedback(Text.translatable("compatenchants.current"), false);
            for (Enchantment enchantment : current) {
                src.sendFeedback(Text.literal("- ").append(TextUtils.getEnchantmentText(enchantment)), false);
            }

            compatible.removeAll(current);
            incompatible = compatible;

            compatible = EnchantmentUtils.getApplicableEnchantments(compatible, current);

            incompatible.removeAll(compatible);
        }

        src.sendFeedback(Text.translatable("compatenchants.compatible").formatted(Formatting.DARK_GREEN), false);
        for (Enchantment enchantment : compatible) {
            src.sendFeedback(Text.literal("- ").formatted(Formatting.DARK_GREEN).append(TextUtils.getEnchantmentText(enchantment)), false);
        }

        if (!incompatible.isEmpty()) {
            src.sendFeedback(Text.translatable("compatenchants.incompatible").formatted(Formatting.DARK_RED), false);
            for (Enchantment enchantment : incompatible) {
                src.sendFeedback(Text.literal("- ").formatted(Formatting.DARK_RED).append(TextUtils.getEnchantmentText(enchantment)), false);
            }
        }

        return 0;
    }
}
