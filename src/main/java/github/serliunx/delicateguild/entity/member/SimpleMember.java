package github.serliunx.delicateguild.entity.member;

import github.serliunx.delicateguild.entity.Guild;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SimpleMember extends AbstractMember{

    public SimpleMember(UUID uuid, String name, int contributionPoint, @Nullable Guild guildBelong) {
        super(uuid, name, contributionPoint,guildBelong);
    }

    public SimpleMember(UUID uuid, String name, int contributionPoint) {
        super(uuid, name, contributionPoint);
    }
}
