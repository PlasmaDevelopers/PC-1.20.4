/*     */ package net.minecraft;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ public enum ChatFormatting implements StringRepresentable {
/*     */   public static final Codec<ChatFormatting> CODEC;
/*     */   public static final char PREFIX_CODE = '§';
/*     */   private static final Map<String, ChatFormatting> FORMATTING_BY_NAME;
/*     */   private static final Pattern STRIP_FORMATTING_PATTERN;
/*     */   private final String name;
/*  17 */   BLACK("BLACK", '0', 0, Integer.valueOf(0)),
/*  18 */   DARK_BLUE("DARK_BLUE", '1', 1, Integer.valueOf(170)),
/*  19 */   DARK_GREEN("DARK_GREEN", '2', 2, Integer.valueOf(43520)),
/*  20 */   DARK_AQUA("DARK_AQUA", '3', 3, Integer.valueOf(43690)),
/*  21 */   DARK_RED("DARK_RED", '4', 4, Integer.valueOf(11141120)),
/*  22 */   DARK_PURPLE("DARK_PURPLE", '5', 5, Integer.valueOf(11141290)),
/*  23 */   GOLD("GOLD", '6', 6, Integer.valueOf(16755200)),
/*  24 */   GRAY("GRAY", '7', 7, Integer.valueOf(11184810)),
/*  25 */   DARK_GRAY("DARK_GRAY", '8', 8, Integer.valueOf(5592405)),
/*  26 */   BLUE("BLUE", '9', 9, Integer.valueOf(5592575)),
/*  27 */   GREEN("GREEN", 'a', 10, Integer.valueOf(5635925)),
/*  28 */   AQUA("AQUA", 'b', 11, Integer.valueOf(5636095)),
/*  29 */   RED("RED", 'c', 12, Integer.valueOf(16733525)),
/*  30 */   LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, Integer.valueOf(16733695)),
/*  31 */   YELLOW("YELLOW", 'e', 14, Integer.valueOf(16777045)),
/*  32 */   WHITE("WHITE", 'f', 15, Integer.valueOf(16777215)),
/*  33 */   OBFUSCATED("OBFUSCATED", 'k', true),
/*  34 */   BOLD("BOLD", 'l', true),
/*  35 */   STRIKETHROUGH("STRIKETHROUGH", 'm', true),
/*  36 */   UNDERLINE("UNDERLINE", 'n', true),
/*  37 */   ITALIC("ITALIC", 'o', true),
/*  38 */   RESET("RESET", 'r', -1, null); private final char code; private final boolean isFormat;
/*     */   static {
/*  40 */     CODEC = (Codec<ChatFormatting>)StringRepresentable.fromEnum(ChatFormatting::values);
/*     */ 
/*     */     
/*  43 */     FORMATTING_BY_NAME = (Map<String, ChatFormatting>)Arrays.<ChatFormatting>stream(values()).collect(Collectors.toMap($$0 -> cleanName($$0.name), $$0 -> $$0));
/*  44 */     STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
/*     */   } private final String toString; private final int id; @Nullable
/*     */   private final Integer color; private static String cleanName(String $$0) {
/*  47 */     return $$0.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChatFormatting(String $$0, char $$1, @Nullable boolean $$2, int $$3, Integer $$4) {
/*  67 */     this.name = $$0;
/*  68 */     this.code = $$1;
/*  69 */     this.isFormat = $$2;
/*  70 */     this.id = $$3;
/*  71 */     this.color = $$4;
/*     */     
/*  73 */     this.toString = "§" + String.valueOf($$1);
/*     */   }
/*     */   
/*     */   public char getChar() {
/*  77 */     return this.code;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  81 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean isFormat() {
/*  85 */     return this.isFormat;
/*     */   }
/*     */   
/*     */   public boolean isColor() {
/*  89 */     return (!this.isFormat && this != RESET);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Integer getColor() {
/*  94 */     return this.color;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  98 */     return name().toLowerCase(Locale.ROOT);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return this.toString;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static String stripFormatting(@Nullable String $$0) {
/* 108 */     return ($$0 == null) ? null : STRIP_FORMATTING_PATTERN.matcher($$0).replaceAll("");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ChatFormatting getByName(@Nullable String $$0) {
/* 113 */     if ($$0 == null) {
/* 114 */       return null;
/*     */     }
/* 116 */     return FORMATTING_BY_NAME.get(cleanName($$0));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ChatFormatting getById(int $$0) {
/* 121 */     if ($$0 < 0) {
/* 122 */       return RESET;
/*     */     }
/* 124 */     for (ChatFormatting $$1 : values()) {
/* 125 */       if ($$1.getId() == $$0) {
/* 126 */         return $$1;
/*     */       }
/*     */     } 
/* 129 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ChatFormatting getByCode(char $$0) {
/* 134 */     char $$1 = Character.toLowerCase($$0);
/* 135 */     for (ChatFormatting $$2 : values()) {
/* 136 */       if ($$2.code == $$1) {
/* 137 */         return $$2;
/*     */       }
/*     */     } 
/* 140 */     return null;
/*     */   }
/*     */   
/*     */   public static Collection<String> getNames(boolean $$0, boolean $$1) {
/* 144 */     List<String> $$2 = Lists.newArrayList();
/*     */     
/* 146 */     for (ChatFormatting $$3 : values()) {
/* 147 */       if (!$$3.isColor() || $$0)
/*     */       {
/*     */         
/* 150 */         if (!$$3.isFormat() || $$1)
/*     */         {
/*     */           
/* 153 */           $$2.add($$3.getName()); } 
/*     */       }
/*     */     } 
/* 156 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 161 */     return getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\ChatFormatting.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */