package cc.unilock.compatenchants;

import cc.unilock.compatenchants.util.EnchantmentUtils;
import cc.unilock.compatenchants.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompatEnchantsCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "compatenchants";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/compatenchants <item>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length > 1) {
            throw new WrongUsageException(getCommandUsage(sender));
        }

        ItemStack stack = args.length == 1 ? new ItemStack(getItemByText(sender, args[0])) : Minecraft.getMinecraft().thePlayer.getHeldItem();

        if (stack == null) {
            sender.addChatMessage(new ChatComponentTranslation("compatenchants.unenchantable", "N/A").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }

        Set<Enchantment> compatible = EnchantmentUtils.getCompatibleEnchantments(stack);
        if (compatible.isEmpty()) {
            sender.addChatMessage(new ChatComponentTranslation("compatenchants.unenchantable", StatCollector.translateToLocal(stack.getItem().getUnlocalizedName() + ".name")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return;
        }

        sender.addChatMessage(new ChatComponentTranslation("compatenchants.item", TextUtils.getHoverableText(stack)));

        Set<Enchantment> current = EnchantmentUtils.getCurrentEnchantments(stack);
        Set<Enchantment> incompatible = new HashSet<>();
        if (!current.isEmpty()) {
            sender.addChatMessage(new ChatComponentTranslation("compatenchants.current"));
            for (Enchantment enchantment : current) {
                sender.addChatMessage(TextUtils.getEnchantmentText(enchantment, null));
            }

            compatible.removeAll(current);
            incompatible = compatible;

            compatible = EnchantmentUtils.getApplicableEnchantments(compatible, current);

            incompatible.removeAll(compatible);
        }

        sender.addChatMessage(new ChatComponentTranslation("compatenchants.compatible").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GREEN)));
        for (Enchantment enchantment : compatible) {
            sender.addChatMessage(TextUtils.getEnchantmentText(enchantment, EnumChatFormatting.DARK_GREEN));
        }

        if (!incompatible.isEmpty()) {
            sender.addChatMessage(new ChatComponentTranslation("compatenchants.incompatible").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_RED)));
            for (Enchantment enchantment : incompatible) {
                sender.addChatMessage(TextUtils.getEnchantmentText(enchantment, EnumChatFormatting.DARK_RED));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return getListOfStringsFromIterableMatchingLastWord(args, Item.itemRegistry.getKeys());
        }
        return super.addTabCompletionOptions(sender, args);
    }
}
