package com.thevoxelbox.voxelguest.modules.asshat;

import com.thevoxelbox.voxelguest.modules.GuestModule;
import com.thevoxelbox.voxelguest.modules.asshat.ban.Banlist;
import com.thevoxelbox.voxelguest.modules.asshat.ban.BannedPlayer;
import com.thevoxelbox.voxelguest.modules.asshat.command.BanCommandExecutor;
import com.thevoxelbox.voxelguest.modules.asshat.command.BanreasonCommandExecutor;
import com.thevoxelbox.voxelguest.modules.asshat.command.FreezeCommandExecutor;
import com.thevoxelbox.voxelguest.modules.asshat.command.KickCommandExecutor;
import com.thevoxelbox.voxelguest.modules.asshat.command.MuteCommandExecutor;
import com.thevoxelbox.voxelguest.modules.asshat.command.SoapboxCommandExecutor;
import com.thevoxelbox.voxelguest.modules.asshat.command.UnbanCommandExecutor;
import com.thevoxelbox.voxelguest.modules.asshat.command.UnmuteCommandExecutor;
import com.thevoxelbox.voxelguest.modules.asshat.mute.MutedPlayer;
import com.thevoxelbox.voxelguest.modules.asshat.mute.Mutelist;
import com.thevoxelbox.voxelguest.persistence.Persistence;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Monofraps
 */
public class AsshatModule extends GuestModule
{
	public static final String SILENCE_BYPASS_PERM = "voxelguest.asshat.bypass.silence";
	public static final String FREEZE_BYPASS_PERM = "voxelguest.asshat.bypass.freeze";
	private final AsshatModuleConfiguration config = new AsshatModuleConfiguration();
	private PlayerListener playerListener;
	private BanCommandExecutor banCommandExecutor;
	private UnbanCommandExecutor unbanCommandExecutor;
	private BanreasonCommandExecutor banreasonCommandExecutor;
	private MuteCommandExecutor muteCommandExecutor;
	private UnmuteCommandExecutor unmuteCommandExecutor;
	private KickCommandExecutor kickCommandExecutor;
	private SoapboxCommandExecutor soapboxCommandExecutor;
	private FreezeCommandExecutor freezeCommandExecutor;
	private Mutelist mutelist = new Mutelist();
	private Banlist banlist = new Banlist();
	private boolean silenceEnabled = false;
	private boolean freezeEnabled = false;

	public AsshatModule()
	{
		setName("Asshat Module");

		playerListener = new PlayerListener(this);

		Persistence.getInstance().registerPersistentClass(BannedPlayer.class);
		Persistence.getInstance().registerPersistentClass(MutedPlayer.class);

		banCommandExecutor = new BanCommandExecutor(this);
		unbanCommandExecutor = new UnbanCommandExecutor(this);
		banreasonCommandExecutor = new BanreasonCommandExecutor(this);
		muteCommandExecutor = new MuteCommandExecutor(this);
		unmuteCommandExecutor = new UnmuteCommandExecutor(this);
		kickCommandExecutor = new KickCommandExecutor(this);
		soapboxCommandExecutor = new SoapboxCommandExecutor(this);
		freezeCommandExecutor = new FreezeCommandExecutor(this);


	}

	@Override
	public final void onEnable()
	{
		super.onEnable();
	}

	@Override
	public final void onDisable()
	{
		super.onDisable();
	}

	@Override
	public final String getConfigFileName()
	{
		return "asshat";
	}

	@Override
	public final Object getConfiguration()
	{
		return config;
	}

	@Override
	public final Set<Listener> getListeners()
	{
		final HashSet<Listener> listeners = new HashSet<>();
		listeners.add(playerListener);
		return listeners;
	}

	@Override
	public Map<String, CommandExecutor> getCommandMappings()
	{
		HashMap<String, CommandExecutor> commandMappings = new HashMap<>();
		commandMappings.put("ban", banCommandExecutor);
		commandMappings.put("unban", unbanCommandExecutor);
		commandMappings.put("banreason", banreasonCommandExecutor);
		commandMappings.put("mute", muteCommandExecutor);
		commandMappings.put("unmute", unmuteCommandExecutor);
		commandMappings.put("kick", kickCommandExecutor);
		commandMappings.put("soapbox", soapboxCommandExecutor);
		commandMappings.put("freeze", freezeCommandExecutor);

		return commandMappings;
	}

	public final Banlist getBanlist()
	{
		return banlist;
	}

	public final Mutelist getMutelist()
	{
		return mutelist;
	}

	public final boolean isSilenceEnabled()
	{
		return silenceEnabled;
	}

	public final void setSilenceEnabled(final boolean silenceEnabled)
	{
		this.silenceEnabled = silenceEnabled;
	}

	public final boolean isFreezeEnabled()
	{
		return freezeEnabled;
	}

	public final void setFreezeEnabled(final boolean freezeEnabled)
	{
		this.freezeEnabled = freezeEnabled;
	}

	public final String formatBroadcastMessage(final String msg, final String target, final String adminName, String reason, final boolean hasReason)
	{
		if (reason.isEmpty())
		{
			reason = this.config.getDefaultAsshatReason();
		}

		if (hasReason)
		{
			return msg.replace("%playername%", target).replace("%admin%", adminName);
		}
		else
		{
			return msg.replace("%playername%", target).replace("%admin%", adminName)
					.replace("%reason%", reason);
		}
	}
}
