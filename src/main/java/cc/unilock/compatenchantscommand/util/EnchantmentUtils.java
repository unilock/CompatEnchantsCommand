package cc.unilock.compatenchantscommand.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

public class EnchantmentUtils {
    public static Set<Enchantment> getCompatibleEnchantments(ItemStack stack) {
        Set<Enchantment> compatible = new HashSet<>();

        for (Enchantment enchantment : Registry.ENCHANTMENT) {
            if (enchantment.isAcceptableItem(stack)) {
                compatible.add(enchantment);
            }
        }

        return compatible;
    }

    public static Set<Enchantment> getApplicableEnchantments(Set<Enchantment> compatible, Set<Enchantment> current) {
        Set<Enchantment> applicable = new HashSet<>();

        for (Enchantment candidate : compatible) {
            if (EnchantmentHelper.isCompatible(current, candidate)) {
                applicable.add(candidate);
            }
        }

        return applicable;
    }
}
