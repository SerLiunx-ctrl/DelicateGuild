package github.serliunx.delicateguild.manager;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 冷却类, 给类提供了冷却时间的功能.
 * @param <T> 可以冷却的类.
 */
public class CooldownProvider<T> {

    private final Map<T, Duration> cooldownTimes = new HashMap<>();
    private final Duration duration;

    private CooldownProvider(Duration duration) {
        this.duration = duration;
    }

    /**
     * 检查该实体是否还在冷却中.
     *
     * @param t 实体.
     * @return  如果在冷却中则返回真,否则返回假
     */
    public boolean isOnCooldown(T t) {
        return cooldownTimes.containsKey(t) && cooldownTimes.get(t).toMillis() > System.currentTimeMillis();
    }

    /**
     * 获取实体剩余的冷却时间,没有则返回0.
     *
     * @param t 实例
     * @return  剩余时间.
     */
    public Duration getRemainingTime(T t) {
        if (!isOnCooldown(t)) return Duration.ZERO;

        return cooldownTimes.get(t).minusMillis(System.currentTimeMillis());
    }

    /**
     * 使用{@link CooldownProvider} 的持续时间重置指定实体的冷却时间。
     * 在方法 {@link CooldownProvider#isOnCooldown(Object)} 之前使用.
     *
     * @param t 实体
     */
    public void applyCooldown(T t) {
        cooldownTimes.put(t, duration.plusMillis(System.currentTimeMillis()));
    }

    /**
     * 创建一个冷却实例并返回.
     *
     * @param duration 时间
     * @param <T>      类名
     * @return      冷却实例
     */
    public static <T> CooldownProvider<T> newInstance(Duration duration) {
        return new CooldownProvider<>(duration);
    }
}