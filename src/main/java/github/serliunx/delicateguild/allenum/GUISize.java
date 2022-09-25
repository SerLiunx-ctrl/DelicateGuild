package github.serliunx.delicateguild.allenum;

/**
 * <p>gui尺寸
 * <li>因为尺寸相对固定，不建议填写数字.可以避免不必要的问题发生
 * <li>故使用枚举类型来限制所能填写的数字
 * <p>
 * <li>使用方法:
 * <p>调用对应枚举值的{@link GUISize#getValue()}, 返回值类型为int
 */
public enum GUISize {
    /**
     * one row, 0 ~ 8
     */
    ONE_ROW(9),

    /**
     * two rows, 0 ~ 17
     */
    TWO_ROWS(18),

    /**
     * three rows, 0 ~ 26
     */
    THREE_ROWS(27),

    /**
     * four rows, 0 ~ 35
     */
    FOUR_ROWS(36),

    /**
     * five rows, 0 ~ 44
     */
    FIVE_ROWS(45),

    /**
     * six rows, 0 ~ 53
     */
    SIX_ROWS(54);

    private final int value;

    GUISize(int value){this.value = value;}

    public int getValue(){return value;}
}
