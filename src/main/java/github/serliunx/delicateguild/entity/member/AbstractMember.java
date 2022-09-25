package github.serliunx.delicateguild.entity.member;

import github.serliunx.delicateguild.allenum.Role;
import github.serliunx.delicateguild.entity.guild.Guild;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class AbstractMember implements Member{

    private final UUID uuid;
    private final String name;
    @Nullable
    private Guild guildBelong;
    private Role role;

    public AbstractMember(UUID uuid, String name, @Nullable Guild guildBelong) {
        this.uuid = uuid;
        this.name = name;
        this.guildBelong = guildBelong;
    }

    public AbstractMember(UUID uuid, String name) {
        this(uuid, name, null);
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    @Nullable
    public Guild getGuildBelong() {
        return guildBelong;
    }

    @Override
    public void setGuildBelong(@Nullable Guild guildBelong) {
        this.guildBelong = guildBelong;
        this.role = Role.MEMBER;
    }

    @Nullable
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = guildBelong == null ? null : role;
    }
}
