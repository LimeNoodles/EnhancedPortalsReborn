package alz.mods.enhancedportals.server;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class CommandEPClient extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "epc";
	}

	// TODO
	// commands for the client
	// change sound frequency
	// change portal frequency
	// see version number

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender)
	{
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender commandSender, String[] args)
	{
		return super.addTabCompletionOptions(commandSender, args);
	}

	@Override
	public void processCommand(ICommandSender commandSender, String[] args)
	{

	}
}