package cc.unilock.compatenchantscommand.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;

import java.util.Comparator;
import java.util.List;

// https://github.com/Wolfsurge/EnchantmentPreview/blob/a0e83d433191ddfcee4afa03b4c0e23533de51c6/src/client/kotlin/me/surge/EnchantmentPreviewsClient.kt#L16

public class EnchantmentUtilsNG {
    public static List<Enchantment> getCompatibleEnchantments(ItemStack stack) {
        return Registry.ENCHANTMENT.stream()
                .filter(ench -> ench.isAcceptableItem(stack) && EnchantmentHelper.isCompatible(EnchantmentHelper.get(stack).keySet(), ench))
                .sorted(Comparator.comparing(o -> Text.translatable(o.getTranslationKey()).getString()))
                .toList();
    }

    public static List<Enchantment> getApplicableEnchantments(ItemStack stack) {
        return Registry.ENCHANTMENT.stream()
                .filter(ench -> EnchantmentHelper.isCompatible(EnchantmentHelper.get(stack).keySet(), ench))
                .sorted(Comparator.comparing(o -> Text.translatable(o.getTranslationKey()).getString()))
                .toList();
    }
}
