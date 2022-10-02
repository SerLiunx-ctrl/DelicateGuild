package github.serliunx.delicateguild.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class StringUtils {

    private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 转换颜色代码: &, 支持转换16进制的颜色代码"
     *
     * @param rawText: 需要翻译颜色代码文字.
     * @return 翻译颜色代码后的文字.
     *
     */
    public static String Color(@NotNull String rawText){
        return translateColorCodes(rawText);
    }

    /**
     *
     * @param player: 玩家, 用途: 检测是否有权限.
     * @param rawText: 需要翻译颜色代码文字.
     * @return 翻译颜色代码后的文字.
     *
     */
    public static String ColorWithPlayer(@NotNull Player player, @NotNull String rawText, String permission){
        if(player.hasPermission(permission))
            return translateColorCodes(rawText);
        return rawText;
    }

    private static String translateColorCodes(String text){
        if(!text.contains("&"))return text;

        String[] texts = text.split(String.format(WITH_DELIMITER, "&"));
        texts[1].split("1");
        StringBuilder finalText = new StringBuilder();
        for (int i = 0; i < texts.length; i++){
            if (texts[i].equalsIgnoreCase("&")){
                //get the next string
                i++;
                if (texts[i].charAt(0) == '#'){
                    finalText.append(ChatColor.of(texts[i].substring(0, 7))).append(texts[i].substring(7));
                }else{
                    finalText.append(ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                }
            }else{
                finalText.append(texts[i]);
            }
        }

        return finalText.toString();
    }


    /**
     * 返回一个文本组件
     *
     * @param text 文本
     * @param holo 悬浮文字
     * @return 文本组件
     */
    public static TextComponent newTextComponent(@NotNull String text, @NotNull List<String> holo){
        TextComponent textComponent = (TextComponent) translateColorCodesToTextComponent(text);
        textComponent.setHoverEvent(newHoverEvent(holo, HoverEvent.Action.SHOW_TEXT));
        return textComponent;
    }

    /**
     * 返回一个文本组件
     *
     * @param text 文本
     * @param holo 悬浮文字
     * @param runCommand 点击后运行的指令
     * @return 文本组件
     */
    public static TextComponent newTextComponent(@NotNull String text, @NotNull List<String> holo, String runCommand){
        TextComponent textComponent = newTextComponent(text, holo);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, runCommand));
        return textComponent;
    }

    /**
     * 返回一个新的HoverEvent
     * @param holo 悬浮文字
     * @param action Action
     * @return HoverEvent
     */
    public static HoverEvent newHoverEvent(@NotNull List<String> holo, HoverEvent.Action action){
        if(!holo.isEmpty()){
            BaseComponent[] texts = new TextComponent[holo.size()];
            for(int i = 0; i < holo.size(); i++){
                texts[i] =i == holo.size()-1 ? translateColorCodesToTextComponent(holo.get(i)) :
                        translateColorCodesToTextComponent(holo.get(i)+"\n");
            }
            return new HoverEvent(action, new Text(texts));
        }
        return new HoverEvent(action, new Text("error"));
    }

    private static BaseComponent transColor(String text, boolean setColor){

        String[] texts = text.split(String.format(WITH_DELIMITER, "&"));
        ComponentBuilder builder = new ComponentBuilder();
        for (int i = 0; i < texts.length; i++){
            TextComponent subComponent = new TextComponent();
            if (texts[i].equalsIgnoreCase("&")){
                i++;
                if (texts[i].charAt(0) == '#'){
                    subComponent.setText(texts[i].substring(7));
                    if(setColor){
                        subComponent.setColor(checkHexColor(texts[i].substring(0, 7)) ?
                                ChatColor.of(texts[i].substring(0, 7)) : ChatColor.WHITE);
                    }

                    builder.append(subComponent);
                }else{
                    if (texts[i].length() > 1){
                        subComponent.setText(texts[i].substring(1));
                    }else{
                        subComponent.setText(" ");
                    }
                    if(setColor){
                        setColor(texts, i, subComponent);
                    }
                    builder.append(subComponent);
                }
            }else{
                builder.append(texts[i]);
            }
        }
        return new TextComponent(builder.create());
    }

    private static void setColor(String[] texts, int i, TextComponent subComponent) {
        switch (texts[i].charAt(0)){
            case '0':
                subComponent.setColor(ChatColor.BLACK);
                break;
            case '1':
                subComponent.setColor(ChatColor.DARK_BLUE);
                break;
            case '2':
                subComponent.setColor(ChatColor.DARK_GREEN);
                break;
            case '3':
                subComponent.setColor(ChatColor.DARK_AQUA);
                break;
            case '4':
                subComponent.setColor(ChatColor.DARK_RED);
                break;
            case '5':
                subComponent.setColor(ChatColor.DARK_PURPLE);
                break;
            case '6':
                subComponent.setColor(ChatColor.GOLD);
                break;
            case '7':
                subComponent.setColor(ChatColor.GRAY);
                break;
            case '8':
                subComponent.setColor(ChatColor.DARK_GRAY);
                break;
            case '9':
                subComponent.setColor(ChatColor.BLUE);
                break;
            case 'a':
                subComponent.setColor(ChatColor.GREEN);
                break;
            case 'b':
                subComponent.setColor(ChatColor.AQUA);
                break;
            case 'c':
                subComponent.setColor(ChatColor.RED);
                break;
            case 'd':
                subComponent.setColor(ChatColor.LIGHT_PURPLE);
                break;
            case 'e':
                subComponent.setColor(ChatColor.YELLOW);
                break;
            case 'f':
                subComponent.setColor(ChatColor.WHITE);
                break;
            case 'k':
                subComponent.setObfuscated(true);
                break;
            case 'l':
                subComponent.setBold(true);
                break;
            case 'm':
                subComponent.setStrikethrough(true);
                break;
            case 'n':
                subComponent.setUnderlined(true);
                break;
            case 'o':
                subComponent.setItalic(true);
                break;
            case 'r':
                subComponent.setColor(ChatColor.RESET);
                break;
        }
    }

    private static boolean checkHexColor(String color){
        try{
            ChatColor.of(color);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * 快速获取一个转换过颜色的BaseComponent
     * 支持16进制颜色代码.
     * <li> 检查所给的权限节点
     * @param text 基础文本
     * @param player 玩家
     * @param permission 权限
     * @return BaseComponent
     */
    public static BaseComponent translateColorCodesToTextComponent(String text, Player player, String permission){
        return transColor(text, player.hasPermission(permission));
    }

    /**
     * 快速获取一个转换过颜色的BaseComponent
     * 支持16进制颜色代码.
     * @param text 基础文本
     * @return BaseComponent
     */
    public static BaseComponent translateColorCodesToTextComponent(String text){
        return transColor(text, true);
    }

    /**
     * 格式化时间
     *
     * @param format 文字
     * @param duration 时间
     * @return 格式化之后的文字
     */
    public static String formatDuration(String format, Duration duration) {
        double hours = duration.toHours();
        double minutes = duration.toMinutes() / 60.0;
        double seconds = duration.toMillis() / 1000.0;

        return format.replace("{hours}", String.valueOf(hours))
                .replace("{minutes}", String.format("%.1f",minutes))
                .replace("{seconds}", String.format("%.1f",seconds));
    }

    public static String rainbowColored(@NotNull String text) {
        text = text.replace("<r>","");
        String[] coloredText = new String[text.length()];

        for(int i = 0; i < text.length(); i++){
            coloredText[i] = text.substring(i,i+1);
            coloredText[i] = randomColor() + coloredText[i];
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : coloredText) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    public static String randomColor(){
        String red, green, blue;
        Random random = new Random();

        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

        red = red.length() == 1 ? "0" + red : red;
        green = green.length() == 1 ? "0" + green : green;
        blue = blue.length() == 1 ? "0" + blue : blue;

        return  "&#" + red + green + blue;
    }

    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(date);
    }

}
