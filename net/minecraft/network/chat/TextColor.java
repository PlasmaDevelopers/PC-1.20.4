/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ 
/*     */ 
/*     */ public final class TextColor
/*     */ {
/*     */   private static final String CUSTOM_COLOR_PREFIX = "#";
/*  19 */   public static final Codec<TextColor> CODEC = Codec.STRING.comapFlatMap(TextColor::parseColor, TextColor::serialize);
/*     */   private static final Map<ChatFormatting, TextColor> LEGACY_FORMAT_TO_COLOR;
/*     */   private static final Map<String, TextColor> NAMED_COLORS;
/*     */   
/*     */   static {
/*  24 */     LEGACY_FORMAT_TO_COLOR = (Map<ChatFormatting, TextColor>)Stream.<ChatFormatting>of(ChatFormatting.values()).filter(ChatFormatting::isColor).collect(ImmutableMap.toImmutableMap(Function.identity(), $$0 -> new TextColor($$0.getColor().intValue(), $$0.getName())));
/*  25 */     NAMED_COLORS = (Map<String, TextColor>)LEGACY_FORMAT_TO_COLOR.values().stream().collect(ImmutableMap.toImmutableMap($$0 -> $$0.name, Function.identity()));
/*     */   }
/*     */   
/*     */   private final int value;
/*     */   @Nullable
/*     */   private final String name;
/*     */   
/*     */   private TextColor(int $$0, String $$1) {
/*  33 */     this.value = $$0 & 0xFFFFFF;
/*  34 */     this.name = $$1;
/*     */   }
/*     */   
/*     */   private TextColor(int $$0) {
/*  38 */     this.value = $$0 & 0xFFFFFF;
/*  39 */     this.name = null;
/*     */   }
/*     */   
/*     */   public int getValue() {
/*  43 */     return this.value;
/*     */   }
/*     */   
/*     */   public String serialize() {
/*  47 */     return (this.name != null) ? this.name : formatValue();
/*     */   }
/*     */   
/*     */   private String formatValue() {
/*  51 */     return String.format(Locale.ROOT, "#%06X", new Object[] { Integer.valueOf(this.value) });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  56 */     if (this == $$0) {
/*  57 */       return true;
/*     */     }
/*  59 */     if ($$0 == null || getClass() != $$0.getClass()) {
/*  60 */       return false;
/*     */     }
/*  62 */     TextColor $$1 = (TextColor)$$0;
/*  63 */     return (this.value == $$1.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  68 */     return Objects.hash(new Object[] { Integer.valueOf(this.value), this.name });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  73 */     return serialize();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static TextColor fromLegacyFormat(ChatFormatting $$0) {
/*  78 */     return LEGACY_FORMAT_TO_COLOR.get($$0);
/*     */   }
/*     */   
/*     */   public static TextColor fromRgb(int $$0) {
/*  82 */     return new TextColor($$0);
/*     */   }
/*     */   
/*     */   public static DataResult<TextColor> parseColor(String $$0) {
/*  86 */     if ($$0.startsWith("#")) {
/*     */       try {
/*  88 */         int $$1 = Integer.parseInt($$0.substring(1), 16);
/*  89 */         if ($$1 < 0 || $$1 > 16777215) {
/*  90 */           return DataResult.error(() -> "Color value out of range: " + $$0);
/*     */         }
/*  92 */         return DataResult.success(fromRgb($$1), Lifecycle.stable());
/*  93 */       } catch (NumberFormatException $$2) {
/*  94 */         return DataResult.error(() -> "Invalid color value: " + $$0);
/*     */       } 
/*     */     }
/*  97 */     TextColor $$3 = NAMED_COLORS.get($$0);
/*  98 */     if ($$3 == null) {
/*  99 */       return DataResult.error(() -> "Invalid color name: " + $$0);
/*     */     }
/* 101 */     return DataResult.success($$3, Lifecycle.stable());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\TextColor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */