package github.serliunx.delicateguild.entity;

import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public interface Guild {

    /**
     * 获取该公会的所有者, 也是该公会的成员: {@link Member}
     *
     * <li> 可能为空, 即一个公会可以没有所有者
     * @return 所有者, 若无则返回空值
     */
    Member getOwner();

    /**
     * 设置该公会的所有者
     *
     * <li> 使用该方法时, 该用户必须为此公会的成员
     * <p> 否则将无法生效, 将保持原有的公会所有者 {@link Guild#getOwner()}
     * @param member 目标成员
     */
    void setOwner(Member member);

    /**
     * 设置该公会的所有者
     *
     * <li> 使用该方法时, 该用户可以不属于该公会, 但不能属于其他公会
     * <li> 设置成功后, 该玩家会自动加入该公会
     * <p> 即成为该公会的成员 {@link Guild#getMembers()}
     * @param player 目标玩家
     */
    void setOwner(Player player);

    /**
     * 获取该公会的名称
     * @return 公会名称
     */
    String getAlias();

    /**
     * 设置该公会的名称
     * @param alias 公会名称
     */
    void setAlias(String alias);

    /**
     * 获取该公会的点数
     * @return 公会点数
     */
    int getPoints();

    /**
     * 设置该公会的点数
     * @param points 公会点数
     */
    void setPoints(int points);

    /**
     * 给该公会添加指定点数
     * @param points 公会点数
     */
    void addPoints(int points);

    /**
     * 减少该公会的点数
     *
     * <li> 如果减少的点数大于该公会原有的点数, 则会将该工会的点数清零
     * @param points 公会点数
     */
    void decreasePoints(int points);

    /**
     * 获取该公会的存款
     * @return 公会存款
     */
    double getMoney();

    /**
     * 设置该公会的存款
     * @param money 公会存款 (必须大于0)
     */
    void setMoney(double money);

    /**
     * 往公会存款中存一笔金币
     * @param money 金币数量
     */
    void depositMoney(double money);

    /**
     * 从公会存款中取一笔金币
     *
     * <li> 如果取出的金币大于存款, 则会返回公会所有的金币
     * @param money 金币数量
     * @return 金币
     */
    double withdrawMoney(double money);

    /**
     * 获取该公会的最大成员数量
     * @return 最大成员数量
     */
    int getMaxMembers();

    /**
     * 设置该公会的最大成员数量
     *
     * <li> 必须大于等于当前的成员数量, 否则将保持原有的最大成员数量
     * @param maxMembers 最大成员数量
     */
    void setMaxMembers(int maxMembers);

    /**
     * 获取该公会的所有成员
     * @return 所有公会成员
     */
    Set<Member> getMembers();

    /**
     * 获取该公会的 UUID
     * @return 公会UUID
     */
    String getId();

    /**
     * 向该公会添加一名成员
     * @param member 成员
     */
    void addMember(Member member);

    /**
     * 向该公会添加一名成员
     *
     * <li> 不建议轻易使用该方法
     * <li> 用于该玩家不存在本插件的数据库时
     * <p> 正常情况下, 玩家都会存在于本插件的数据库中
     * @param player 玩家
     */
    void addMember(Player player);

    /**
     * 获取该公会当前的等级
     * @return 公会等级
     */
    int getLevel();

    /**
     * 设置当前公会的等级
     * @param level 公会等级(这个方法可以忽略配置文件中的最大等级限制)
     */
    void setLevel(int level);

    /**
     * 获取该公会当前的经验值
     * @return 经验值
     */
    int getExpNow();

    /**
     * 设置该公会的经验值
     * <p>
     * <li> 会根据最大等级,以及经验增长倍率自动计算公会等级
     * @param expNow 经验值
     */
    void setExpNow(int expNow);

    /**
     * 添加该公会的经验值
     * <p>
     * <li> 会根据最大等级,以及经验增长倍率自动计算公会等级
     * @param exp 想要添加的经验值
     */
    void addExp(int exp);

    /**
     * 获取该公会创建的日期
     * @return 创建日期
     */
    Date getCreateDate();

    /**
     * 设置该公会创建的日期
     * @param createDate 创建日期
     */
    void setCreateDate(Date createDate);

    /**
     * 获取该公会当前等级想要升至下一级所需要的最大经验值
     * @return 升级所需要的最大经验值
     */
    int getMaxExpToLevelUp();
}
