package github.serliunx.delicateguild.entity.guild;

import github.serliunx.delicateguild.entity.member.Member;
import github.serliunx.delicateguild.entity.member.SimpleMember;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class AbstractGuild implements Guild{

    /**
     * 公会成员
     * <li> 包括该工会的所有者
     */
    private final Set<Member> members;

    /**
     * 公会所有者
     */
    @Nullable
    private Member owner;

    /**
     * 该公会的UUID, 唯一识别码
     */
    private final UUID uuid;

    /**
     * 公会别名
     */
    private String alias;

    /**
     * 公会点数
     * <li> 主要用于工会排名
     */
    private int points;

    /**
     * 公会最大允许的成员数量
     * <li> 大于 0
     */
    private int maxMembers;

    /**
     * 公会存款
     * <li> 成员可以捐款
     * <li> 部分成员可以取用存款
     * <li> .....
     */
    private double money;

    public AbstractGuild(Set<Member> members,@Nullable Member owner, UUID uuid, String alias, int points,
                         double money, int maxMembers) {
        this.members = members;
        this.owner = owner;
        this.uuid = uuid;
        this.alias = alias;
        this.points = points;
        this.money = money;
        this.maxMembers = maxMembers;
    }

    public AbstractGuild(Member owner, String alias, int maxMembers){
        this(new HashSet<>(), owner, UUID.randomUUID(), alias, 0, 0.0, maxMembers);
    }

    public AbstractGuild(String alias, int maxMembers){
        this(new HashSet<>(), null, UUID.randomUUID(), alias, 0, 0.0, maxMembers);
    }

    @Nullable
    public Member getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Member member) {
        this.owner = members.contains(member) ? member : null;
    }

    @Override
    public void setOwner(Player player) {

    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public void addPoints(int points){
        this.points = Math.max(points, 0);
    }

    @Override
    public void decreasePoints(int points){
        this.points = points < this.points ? this.points - points : 0;
    }

    @Override
    public double getMoney() {
        return money;
    }

    @Override
    public void setMoney(double money) {
        this.money = money > 0 ? money : 0.0;
    }

    @Override
    public void depositMoney(double money) {
        this.money += money;
    }

    @Override
    public double withdrawMoney(double money) {
        this.money = money < this.money ? this.money - money : 0.0;
        return this.money;
    }

    @Override
    public int getMaxMembers() {
        return maxMembers;
    }

    @Override
    public void setMaxMembers(int maxMembers) {
        this.maxMembers = Math.max(maxMembers, this.members.size());
    }

    @Override
    public Set<Member> getMembers() {
        return members;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void addMember(Member member) {
        if(members.size() > maxMembers){
            return;
        }

        Guild guild = member.getGuildBelong();
        if(guild == null || guild.equals(this)){
            member.setGuildBelong(this);
            this.members.add(member);
        }
    }

    @Override
    public void addMember(Player player) {
        addMember(new SimpleMember(player.getUniqueId(), player.getName()));
    }
}
