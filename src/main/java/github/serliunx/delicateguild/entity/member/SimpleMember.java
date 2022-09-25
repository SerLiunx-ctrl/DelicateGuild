package github.serliunx.delicateguild.entity.member;

import github.serliunx.delicateguild.entity.guild.Guild;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SimpleMember extends AbstractMember{

    public SimpleMember(UUID uuid, String name, @Nullable Guild guildBelong) {
        super(uuid, name, guildBelong);
    }

    public SimpleMember(UUID uuid, String name) {
        super(uuid, name);
    }
}
