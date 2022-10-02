package github.serliunx.delicateguild.entity;

import github.serliunx.delicateguild.allenum.Role;

import java.util.Date;
import java.util.UUID;

public interface Member {

    /**
     * 获取该用户的UUID
     * @return UUID
     */
    UUID getUuid();

    /**
     * 获取该用户的名称
     * @return 用户名称
     */
    String getName();

    /**
     * 获取该用户所属的公会
     *
     * <li> 可能为空
     * @return 所属公会
     */
    Guild getGuildBelong();

    /**
     * 设置该用户的公会
     *
     * <li> 不建议直接使用该方法! 以免造成不必要的麻烦
     * <li> 如果有需要, 请使用公会的{@link Guild#addMember(Member)}方法来添加成员
     * @param guildBelong 指定公会
     */
    void setGuildBelong(Guild guildBelong);

    /**
     * 获取该用户对当前公会的贡献值
     * @return 贡献值
     */
    int getContributionPoint();

    /**
     * 设置该用户对当前公会的工会值
     * @param contributionPoint 贡献值
     */
    void setContributionPoint(int contributionPoint);

    /**
     * 为该用户添加对当前公会的贡献值
     * @param contributionPoint 贡献值
     */
    void addContributionPoint(int contributionPoint);

    /**
     * 获取当前用户的公会角色
     * <li> 所有者
     * <li> 副所有者
     * <li> 普通成员
     * @return 公会角色
     */
    Role getRole();

    /**
     * 设置当前用户的公会角色
     *
     * <li> 必须拥有公会
     * @param role 公会角色
     */
    void setRole(Role role);

    /**
     * 获取该用户加入公会的时间
     * @return 加入时间
     */
    Date getJoinedDate();

    /**
     * 设置该用户加入公会的时间
     * @param joinedDate 加入时间
     */
    void setJoinedDate(Date joinedDate);
}
