package cc.unilock.compatenchantscommand;

import cc.unilock.compatenchantscommand.util.EnchantmentUtils;
import cc.unilock.compatenchantscommand.util.TextUtils;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashSet;
import java.util.Set;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CompatEnchantsCommand implements ClientModInitializer {
	//public static final Logger LOGGER = LoggerFactory.getLogger("CompatEnchantsCommand");

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register(CompatEnchantsCommand::registerCommand);
	}

	public static void registerCommand(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess access) {
		dispatcher.register(
				literal("compatenchants")
						.executes(ctx -> compatEnchantsCommand(ctx.getSource(), ctx.getSource().getPlayer().getMainHandStack()))
						.then(argument("item", ItemStackArgumentType.itemStack(access))
								.executes(ctx -> compatEnchantsCommand(ctx.getSource(), ItemStackArgumentType.getItemStackArgument(ctx, "item").getItem().getDefaultStack()))
						)
		);
	}

	private static int compatEnchantsCommand(FabricClientCommandSource src, ItemStack stack) {
		Set<Enchantment> compatible = EnchantmentUtils.getCompatibleEnchantments(stack);
		if (compatible.isEmpty()) {
			src.sendFeedback(Text.translatable("compatenchantscommand.unenchantable", stack.getItem().getName()).formatted(Formatting.RED));
			return 1;
		}

		src.sendFeedback(Text.translatable("compatenchantscommand.item", TextUtils.getHoverableText(stack)));

		Set<Enchantment> current = EnchantmentHelper.get(stack).keySet();
		Set<Enchantment> incompatible = new HashSet<>();
		if (!current.isEmpty()) {
			src.sendFeedback(Text.translatable("compatenchantscommand.current"));
			for (Enchantment enchantment : current) {
				src.sendFeedback(Text.literal("- ").append(TextUtils.getEnchantmentText(enchantment)));
			}

			compatible.removeAll(current);
			incompatible = compatible;

			compatible = EnchantmentUtils.getApplicableEnchantments(compatible, current);

			incompatible.removeAll(compatible);
		}

		src.sendFeedback(Text.translatable("compatenchantscommand.compatible").formatted(Formatting.DARK_GREEN));
		for (Enchantment enchantment : compatible) {
			src.sendFeedback(Text.literal("- ").formatted(Formatting.DARK_GREEN).append(TextUtils.getEnchantmentText(enchantment)));
		}

		if (!incompatible.isEmpty()) {
			src.sendFeedback(Text.translatable("compatenchantscommand.incompatible").formatted(Formatting.DARK_RED));
			for (Enchantment enchantment : incompatible) {
				src.sendFeedback(Text.literal("- ").formatted(Formatting.DARK_RED).append(TextUtils.getEnchantmentText(enchantment)));
			}
		}

		return 0;
	}
}
