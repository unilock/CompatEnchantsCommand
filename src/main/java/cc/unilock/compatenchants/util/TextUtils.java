package cc.unilock.compatenchants.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;

public class TextUtils {
    public static Text getEnchantmentText(Enchantment enchantment) {
        // Get enchantment name (with max level appended, if present)
		// (idk why 1.18.2 is so obtuse)
        MutableText result = enchantment.getMaxLevel() == 1
			? new TranslatableText(enchantment.getTranslationKey())
			: new TranslatableText(enchantment.getTranslationKey()).append(" ").append(new TranslatableText("enchantment.level." + enchantment.getMaxLevel()));

        // Color the name depending on the enchantment type
        if (enchantment.isCursed()) {
            result.formatted(Formatting.RED);
        } else if (enchantment.isTreasure()) {
            result.formatted(Formatting.LIGHT_PURPLE);
        } else {
            result.formatted(Formatting.GREEN);
        }

        // Show the enchantment description, if any, when hovering over the name
        // (same format as Enchantment Descriptions or idwtialsimmoedm)
        if (Language.getInstance().hasTranslation(enchantment.getTranslationKey() + ".desc")) {
            result = result.styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText(enchantment.getTranslationKey() + ".desc"))));
        }

        return result;
    }

    public static Text getHoverableText(ItemStack stack) {
        MutableText mutableText = new LiteralText("").append(stack.getName());

        return Texts.bracketed(mutableText)
                .formatted(Formatting.AQUA)
                .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(stack))));
    }
}
