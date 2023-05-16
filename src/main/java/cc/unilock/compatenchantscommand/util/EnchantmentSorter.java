package cc.unilock.compatenchantscommand.util;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

// TODO: Doesn't work (no change in output)

public class EnchantmentSorter {
    public static Stream<EnchantmentItem> sort(Set<Enchantment> set, boolean cursesBelow) {
        Comparator<EnchantmentItem> comparator;

        if (cursesBelow) {
            comparator = Comparator.comparing(EnchantmentItem::isCursed);
        } else {
            comparator = Comparator.comparing(e -> 0); // Preserve existing order
        }

        comparator = comparator.thenComparing(EnchantmentItem::getTranslatedName);

        return set.stream().map(EnchantmentItem::new).sorted(comparator);
    }

    public static Set<Enchantment> toSet(Stream<EnchantmentItem> stream) {
        Set<Enchantment> set = new HashSet<>();
        stream.forEachOrdered(item -> set.add(item.asEnchantment()));
        return set;
    }

    public static class EnchantmentItem {
        @NotNull private final Enchantment enchantment;
        private String translatedName;
        private final boolean isCursed;

        public EnchantmentItem(@NotNull Enchantment enchantment) {
            this.enchantment = enchantment;

            this.isCursed = enchantment.isCursed();
        }

        private void lazyInit() {
            this.translatedName = I18n.translate(this.enchantment.getTranslationKey());

        }

        @NotNull Enchantment asEnchantment() {
            return enchantment;
        }

        public boolean isCursed() {
            return isCursed;
        }

        public String getTranslatedName() {
            if (this.translatedName == null) {
                lazyInit();
            }

            return translatedName;
        }
    }
}
