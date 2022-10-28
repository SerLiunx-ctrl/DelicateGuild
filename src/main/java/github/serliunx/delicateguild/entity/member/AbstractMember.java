package github.serliunx.delicateguild.entity.member;

import github.serliunx.delicateguild.allenum.Role;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.UUID;

public abstract class AbstractMember implements Member {

    private final UUID uuid;
    private final String name;
    private @Nullable Guild guildBelong;
    private int contributionPoint;
    private Role role;

    private Date joinedDate;

    public AbstractMember(UUID uuid, String name, int contributionPoint, @Nullable Guild guildBelong) {
        this.uuid = uuid;
        this.name = name;
        this.contributionPoint = contributionPoint;
        this.guildBelong = guildBelong;
    }

    public AbstractMember(UUID uuid, String name, int contributionPoint) {
        this(uuid, name, contributionPoint, null);
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
    }

    @Override
    public int getContributionPoint() {
        return contributionPoint;
    }

    @Override
    public void setContributionPoint(int contributionPoint) {
        this.contributionPoint = Math.max(contributionPoint, 0);
    }

    @Override
    public void addContributionPoint(int contributionPoint){
        this.contributionPoint += contributionPoint;
    }

    @Nullable
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = guildBelong == null ? null : role;
    }

    @Nullable
    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }
}
