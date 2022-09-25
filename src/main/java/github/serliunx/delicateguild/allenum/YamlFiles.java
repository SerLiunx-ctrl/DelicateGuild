package github.serliunx.delicateguild.allenum;

public enum YamlFiles {

    YAML_MAIN("config"),
    YAML_COMMAND("command"),
    YAML_LANGUAGE("lang");

    private final String value;

    YamlFiles(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
