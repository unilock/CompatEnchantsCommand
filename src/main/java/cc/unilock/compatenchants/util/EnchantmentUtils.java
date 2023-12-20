package cc.unilock.compatenchants.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EnchantmentUtils {
    public static Set<Enchantment> getCurrentEnchantments(ItemStack stack) {
        Set<Integer> enchantments = EnchantmentHelper.getEnchantments(stack).keySet();

        if (enchantments.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Enchantment> current = new HashSet<>();

        for (Integer id : enchantments) {
            current.add(Enchantment.enchantmentsList[id]);
        }

        return current;
    }

    public static Set<Enchantment> getCompatibleEnchantments(ItemStack stack) {
        Set<Enchantment> compatible = new HashSet<>();

        for (Enchantment candidate : Enchantment.enchantmentsList) {
            if (candidate != null && candidate.canApply(stack)) {
                compatible.add(candidate);
            }
        }

        return compatible;
    }

    public static Set<Enchantment> getApplicableEnchantments(Set<Enchantment> compatible, Set<Enchantment> current) {
        Set<Enchantment> applicable = new HashSet<>();

        for (Enchantment candidate : compatible) {
            if (isCompatible(current, candidate)) {
                applicable.add(candidate);
            }
        }

        return applicable;
    }

    private static boolean isCompatible(Set<Enchantment> target, Enchantment addition) {
        boolean compatible = true;

        for (Enchantment enchantment : target) {
            if (!enchantment.canApplyTogether(addition)) {
                compatible = false;
            }
        }

        return compatible;
    }
}
