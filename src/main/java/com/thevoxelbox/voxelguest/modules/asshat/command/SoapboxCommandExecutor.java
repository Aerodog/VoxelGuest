package com.thevoxelbox.voxelguest.modules.asshat.command;

import com.thevoxelbox.voxelguest.modules.asshat.AsshatModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

/**
 * Executes /soapbox commands.
 *
 * @author Monofraps
 */
public class SoapboxCommandExecutor implements TabExecutor
{
    private final AsshatModule module;

    /**
     * Creates a new soapbox command executor.
     *
     * @param module The owning module.
     */
    public SoapboxCommandExecutor(final AsshatModule module)
    {
        this.module = module;
    }

    @Override
    public final boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings)
    {
        if (!commandSender.hasPermission("voxelguest.asshat.soapbox"))
        {
            commandSender.sendMessage("You don't have permissions.");
            return true;
        }

        module.setSilenceEnabled(!module.isSilenceEnabled());
        commandSender.sendMessage(String.format("Soapbox is %s", module.isSilenceEnabled() ? "enabled" : "disabled"));

        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender commandSender, final Command command, final String s, final String[] strings)
    {
        return Collections.EMPTY_LIST;
    }
}
