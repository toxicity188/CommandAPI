/*******************************************************************************
 * Copyright 2018, 2020 Jorel Ali (Skepter) - MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package dev.jorel.commandapi.arguments;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.jorel.commandapi.AbstractPlatform;
import dev.jorel.commandapi.BukkitPlatform;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

/**
 * An argument that represents the Bukkit ItemStack object
 */
public class ItemStackArgument extends SafeOverrideableArgument<ItemStack, ItemStack> {

	/**
	 * An ItemStack argument. Always returns an itemstack of size 1
	 * @param nodeName the name of the node for this argument
	 */
	public ItemStackArgument(String nodeName) {
		super(nodeName, BukkitPlatform.get()._ArgumentItemStack(), BukkitPlatform.get()::convert);
	}

	@Override
	public Class<ItemStack> getPrimitiveType() {
		return ItemStack.class;
	}

	@Override
	public CommandAPIArgumentType getArgumentType() {
		return CommandAPIArgumentType.ITEMSTACK;
	}
	
	@Override
	public <CommandSourceStack> ItemStack parseArgument(AbstractPlatform<Argument<?>, CommandSender, CommandSourceStack> platform,
														CommandContext<CommandSourceStack> cmdCtx, String key, Object[] previousArgs) throws CommandSyntaxException {
		return ((BukkitPlatform<CommandSourceStack>) platform).getItemStack(cmdCtx, key);
	}
}
