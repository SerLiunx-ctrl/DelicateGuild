package github.serliunx.delicateguild.allenum;

public enum ActionType {

    PLAY_SOUND("[sound]"),
    RUN_COMMAND("[command]"),
    OPEN_MENU("[openmenu]"),
    CLOSE("[close]");

    private final String value;

    ActionType(String value){this.value = value;}

    public String getValue(){return value;}
}
