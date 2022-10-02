package github.serliunx.delicateguild.entity.guild;

import github.serliunx.delicateguild.entity.Member;
import org.jetbrains.annotations.Nullable;
import java.util.Set;

public class SimpleGuild extends AbstractGuild{

    public SimpleGuild(Set<Member> members, @Nullable Member owner, String id, String alias, int points,
                       double money, int maxMembers, int level, int expNow) {
        super(members, owner, id, alias, points, money, maxMembers, level, expNow);
    }

    public SimpleGuild(Member owner, String id, int maxMembers) {
        super(owner, id, maxMembers);
    }

    public SimpleGuild(String id, int maxMembers) {
        super(id, maxMembers);
    }
}
