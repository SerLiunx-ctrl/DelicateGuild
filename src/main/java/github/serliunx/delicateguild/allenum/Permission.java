package github.serliunx.delicateguild.allenum;

public enum Permission {

    COMMAND_ADMIN_RELOAD("delicateguild.command.admin.reload");

    private final String value;

    Permission(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
