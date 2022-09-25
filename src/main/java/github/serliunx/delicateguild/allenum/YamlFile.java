package github.serliunx.delicateguild.allenum;

public enum YamlFile {

    YAML_MAIN("config"),
    YAML_COMMAND("command"),
    YAML_LANGUAGE("lang");

    private final String value;

    YamlFile(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
