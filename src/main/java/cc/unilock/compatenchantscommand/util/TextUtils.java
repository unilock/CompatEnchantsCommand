package cc.unilock.compatenchantscommand.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;

public class TextUtils {
    public static Text getEnchantmentText(Enchantment enchantment) {
        // Get enchantment name (with max level appended, if present)
        MutableText result = enchantment.getName(enchantment.getMaxLevel()).copy();

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
            result = result.styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable(enchantment.getTranslationKey() + ".desc"))));
        }

        return result;
    }

    public static Text getHoverableText(ItemStack stack) {
        MutableText mutableText = Text.empty().append(stack.getName());

        return Texts.bracketed(mutableText)
                .formatted(Formatting.AQUA)
                .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(stack))));
    }
}
