package github.serliunx.delicateguild.entity;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

public class GuildLog {
    private final int id;
    private final String guildId;
    private final UUID playerUUID;
    private final Date time;
    private final LogType logType;

    public GuildLog(int id, @NotNull String guildId, @NotNull UUID playerUUID, @NotNull Date time,
                    LogType logType) {
        this.id = id;
        this.guildId = guildId;
        this.playerUUID = playerUUID;
        this.time = time;
        this.logType = logType;
    }

    enum LogType{
        GUILD_CREATE,
        GUILD_JOIN,
        GUILD_LEAVE,
        MONEY_WITHDRAW,
        MONEY_DEPOSIT,
    }

}
