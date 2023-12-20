package cc.unilock.compatenchants.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

import javax.annotation.Nullable;

public class TextUtils {
    public static IChatComponent getEnchantmentText(Enchantment enchantment, @Nullable EnumChatFormatting color) {
        // Get enchantment name (with max level appended, if present)
        IChatComponent component = new ChatComponentText("- ").appendText(enchantment.getTranslatedName(enchantment.getMaxLevel()));

        if (color != null) {
            component.getChatStyle().setColor(color);
        }

        // Show the enchantment description, if any, when hovering over the name
        // (same format as Enchantment Descriptions or idwtialsimmoedm)
        if (StatCollector.canTranslate(enchantment.getName() + ".desc")) {
            component.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(StatCollector.translateToLocal(enchantment.getName() + ".desc"))));
        }

        return component;
    }

    public static IChatComponent getHoverableText(ItemStack stack) {
        IChatComponent component = new ChatComponentText("[").appendText(stack.getDisplayName()).appendText("]");

        if (stack.getItem() != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            stack.writeToNBT(nbt);
            component.getChatStyle().setColor(EnumChatFormatting.AQUA);
            component.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(nbt.toString())));
        }

        return component;
    }
}
