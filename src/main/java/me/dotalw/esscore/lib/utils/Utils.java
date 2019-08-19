/*
 *    Copyright (C) 2011-2019 dotalw.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package me.dotalw.esscore.lib.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.text.MessageFormat;
import java.util.stream.IntStream;

public class Utils {
    private static final char COLORCODE = '&';

    public Utils() {
    }

    public static class Chat {
        public static String color(String s) {
            return color(COLORCODE, s);
        }

        public static String color(char altCode, String s) {
            return ChatColor.translateAlternateColorCodes(altCode, s);
        }

        public static String stripColor(String s) {
            return ChatColor.stripColor(s);
        }

        public static String removeAltColorCodes(String s) {
            return removeAltColorCodes(COLORCODE, s);
        }


        public static String removeAltColorCodes(char altColorChar, String textToTranslate) {
            char[] b = textToTranslate.toCharArray();
            IntStream.range(0, b.length - 1).filter(i -> b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1).forEach(i -> {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            });
            return new String(b);
        }
    }

    public static class Text {
        public static String between(String value, String a, String b) {
            // Return a substring between the two strings.
            int posA = value.indexOf(a);
            if (posA == -1) return "";
            int posB = value.lastIndexOf(b);
            if (posB == -1) return "";
            int adjustedPosA = posA + a.length();
            if (adjustedPosA >= posB) return "";
            return value.substring(adjustedPosA, posB);
        }

        public static String before(String value, String a) {
            // Return substring containing all characters before a string.
            int posA = value.indexOf(a);
            if (posA == -1) return "";
            return value.substring(0, posA);
        }

        public static String after(String value, String a) {
            // Returns a substring containing all characters after a string.
            int posA = value.lastIndexOf(a);
            if (posA == -1) return "";
            int adjustedPosA = posA + a.length();
            if (adjustedPosA >= value.length()) return "";
            return value.substring(adjustedPosA);
        }
    }

    public void sendMessage(CommandSender commandSender, String message, Object... replacements) {
        message = MessageFormat.format(Chat.color(message).replace("'", "''"), replacements);
        commandSender.sendMessage(message);
    }

    /*
    public String getTranslatableMessage(CommandSender commandSender, String key) {
        CommandIssuer issuer = manager.getCommandIssuer(commandSender);
        return manager.getLocales().getMessage(issuer, MessageKey.of(key));
    }
    */
}