package github.serliunx.delicateguild.entity.guild;

import github.serliunx.delicateguild.entity.member.Member;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class SimpleGuild extends AbstractGuild{

    public SimpleGuild(Set<Member> members, @Nullable Member owner, UUID uuid, String alias, int points, double money, int maxMembers) {
        super(members, owner, uuid, alias, points, money, maxMembers);
    }

    public SimpleGuild(Member owner, String alias, int maxMembers) {
        super(owner, alias, maxMembers);
    }

    public SimpleGuild(String alias, int maxMembers) {
        super(alias, maxMembers);
    }
}
