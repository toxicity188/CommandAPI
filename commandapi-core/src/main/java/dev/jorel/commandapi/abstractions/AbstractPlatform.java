package dev.jorel.commandapi.abstractions;

import java.io.IOException;
import java.util.List;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.LiteralCommandNode;

import dev.jorel.commandapi.arguments.SuggestionProviders;

public abstract class AbstractPlatform<Source> {
	// TODO: Add methods that need platform-specific implementations
	// All methods in bukkit NMS will probably also need to be here

	// ^ I don't think all bukkit NMS methods will have to be in here.
	// Almost all Bukkit NMS methods should be implemented in Bukkit's
	// AbstractPlatform implementation. The only things in here are going
	// to be supppppppppper low-level stuff


	// Platform-specific loading, enabling, and disabling tasks
	public abstract void onLoad();

	public abstract void onEnable(Object plugin);

	public abstract void onDisable();


	// "Source" in this case (for CommandContext<Source>) is something like a
	// CommandListenerWrapper (Spigot mappings) or CommandSourceStack (Mojang mappings).
	// over
	public abstract AbstractCommandSender<?> getSenderForCommand(CommandContext<Source> cmdCtx, boolean forceNative);

	// Converts a command source into its source.
	public abstract AbstractCommandSender<?> getCommandSenderFromCommandSource(Source cs);

	// Converts a CommandSender to a Brigadier Source
	public abstract Source getBrigadierSourceFromCommandSender(AbstractCommandSender<?> sender);

	// Registers a permission. Bukkit's permission system requires permissions to be "registered"
	// before they can be used.
	public abstract void registerPermission(String string);

	// Some commands have existing suggestion providers.
	// TODO: We can PROBABLY avoid this by
	//  implementing the relevant NMS SuggestionProviders implementation on the platform-specific
	//  argument, but I CBA to think about that now so I'll dump it here
	public abstract SuggestionProvider<Source> getSuggestionProvider(SuggestionProviders suggestionProvider);


	/**
	 * Stuff to run after a command has been generated. For Bukkit, this involves
	 * finding command ambiguities for logging and generating the command JSON
	 * dispatcher file. If we're being fancy, we could also create a "registered
	 * a command" event (which can't be cancelled)
	 *
	 * @param aliasNodes    any alias nodes that were also registered as a part of this registration process
	 * @param resultantNode the node that was registered
	 */
	public abstract void postCommandRegistration(LiteralCommandNode<Source> resultantNode, List<LiteralCommandNode<Source>> aliasNodes) throws IOException;

	/**
	 * Registers a Brigadier command node and returns the built node.
	 */
	public abstract LiteralCommandNode<Source> registerCommandNode(LiteralArgumentBuilder<Source> node);

	// TODO: I'm not sure what this is supposed to do. Currently, Bukkit help is handled in onEnable
	//  and private methods, so is this method obsolete now? Other platforms could conceivably do the same,
	//  or do nothing if they don't have help (like Velocity might?). Maybe there's something that can be done
	//  with this registerHelp method that makes more sense?
	// We probabbbbbbbbly need to register some form of help for commands? I'm not
	// sure if 
	public abstract void registerHelp();


	/**
	 * Unregisters a command from the CommandGraph so it can't be run anymore.
	 *
	 * @param commandName the name of the command to unregister
	 * @param force       whether the unregistration system should attempt to remove
	 *                    all instances of the command, regardless of whether they
	 *                    have been registered by Minecraft, Bukkit or Spigot etc.
	 */
	public abstract void unregister(String commandName, boolean force);

	public abstract CommandDispatcher<Source> getBrigadierDispatcher();
}
