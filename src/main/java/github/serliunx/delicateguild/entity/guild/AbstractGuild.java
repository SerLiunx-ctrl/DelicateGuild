package github.serliunx.delicateguild.entity.guild;

import github.serliunx.delicateguild.DelicateGuild;
import github.serliunx.delicateguild.entity.Guild;
import github.serliunx.delicateguild.entity.Member;
import github.serliunx.delicateguild.entity.member.SimpleMember;
import github.serliunx.delicateguild.util.StringUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class AbstractGuild implements Guild {

    private final Set<Member> members;
    @Nullable
    private Member owner;
    private final String id;
    private String alias;
    private int points, maxMembers, level, expNow;
    private double money;

    private Date createDate;

    public AbstractGuild(Set<Member> members,@Nullable Member owner, String id, String alias, int points,
                         double money, int maxMembers, int level, int expNow) {
        this.members = members;
        this.owner = owner;
        this.id = id;
        this.alias = alias;
        this.points = points;
        this.money = money;
        this.maxMembers = maxMembers > 0 ? maxMembers : 5;
        this.level = Math.max(level, 0);
        this.expNow = Math.max(expNow, 0);
        if(owner != null)
            addMember(owner);
    }

    public AbstractGuild(Member owner, String id, int maxMembers){
        this(new HashSet<>(), owner, id, id, 0, 0.0, maxMembers, 0,0);
    }

    public AbstractGuild(String id, int maxMembers){
        this(new HashSet<>(), null, id, id, 0, 0.0, maxMembers, 0, 0);
    }

    @Nullable
    public Member getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Member member) {
        this.owner = members.contains(member) ? member : getOwner();
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
    public String getId() {
        return id;
    }

    @Override
    public void addMember(Member member) {
        if(members.size() >= maxMembers){
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
        addMember(new SimpleMember(player.getUniqueId(), player.getName(), 0));
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = Math.max(level, 0);
        countExp();
    }

    @Override
    public int getExpNow() {
        return expNow;
    }

    @Override
    public void setExpNow(int expNow) {
        if(level >= DelicateGuild.getGuildMaxLevel())return;
        this.expNow = Math.max(expNow, 0);
        countExp();
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public void addExp(int exp){
        if(level >= DelicateGuild.getGuildMaxLevel())return;
        this.expNow += Math.max(exp, 0);
        countExp();
    }

    private void countExp(){
        List<Integer> expLevelList = DelicateGuild.getInstance().getLevelExpList();
        if(level >= DelicateGuild.getGuildMaxLevel()) return;
        while((expNow > expLevelList.get(level))){
            setLevel(++level);
            setExpNow(expNow - expLevelList.get(level));
        }
    }

    @Override
    public int getMaxExpToLevelUp(){
        return DelicateGuild.getInstance().getLevelExpList().get(level);
    }

    @Override
    public String toString(){
        return  "========================" + "\n"
                + "ID: " + id + "\n"
                + "Alias: " + alias + "\n"
                + "Members: " + members.size() + "\n"
                + "CreateDate: " + StringUtils.formatDate(createDate) + "\n"
                + "Level: " + level + " (" + expNow +")" + "\n"
                + "Money: " + money + "\n"
                + "Points: " + points + "\n"
                + "Owner: " + (owner == null ? "null" : owner.getName()) + "\n"
                + "========================";
    }
}
