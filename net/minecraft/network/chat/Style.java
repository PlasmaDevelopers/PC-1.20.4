/*     */ package net.minecraft.network.chat;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ 
/*     */ public class Style {
/*  15 */   public static final Style EMPTY = new Style(null, null, null, null, null, null, null, null, null, null);
/*     */   public static class Serializer { public static final MapCodec<Style> MAP_CODEC;
/*     */     
/*     */     static {
/*  19 */       MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(TextColor.CODEC, "color").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "bold").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "italic").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "underlined").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "strikethrough").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "obfuscated").forGetter(()), (App)ExtraCodecs.strictOptionalField(ClickEvent.CODEC, "clickEvent").forGetter(()), (App)ExtraCodecs.strictOptionalField(HoverEvent.CODEC, "hoverEvent").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "insertion").forGetter(()), (App)ExtraCodecs.strictOptionalField(ResourceLocation.CODEC, "font").forGetter(())).apply((Applicative)$$0, Style::create));
/*     */     }
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
/*  33 */     public static final Codec<Style> CODEC = MAP_CODEC.codec(); }
/*     */ 
/*     */   
/*  36 */   public static final ResourceLocation DEFAULT_FONT = new ResourceLocation("minecraft", "default");
/*     */   
/*     */   @Nullable
/*     */   final TextColor color;
/*     */   @Nullable
/*     */   final Boolean bold;
/*     */   @Nullable
/*     */   final Boolean italic;
/*     */   @Nullable
/*     */   final Boolean underlined;
/*     */   @Nullable
/*     */   final Boolean strikethrough;
/*     */   @Nullable
/*     */   final Boolean obfuscated;
/*     */   @Nullable
/*     */   final ClickEvent clickEvent;
/*     */   @Nullable
/*     */   final HoverEvent hoverEvent;
/*     */   @Nullable
/*     */   final String insertion;
/*     */   @Nullable
/*     */   final ResourceLocation font;
/*     */   
/*     */   private static Style create(Optional<TextColor> $$0, Optional<Boolean> $$1, Optional<Boolean> $$2, Optional<Boolean> $$3, Optional<Boolean> $$4, Optional<Boolean> $$5, Optional<ClickEvent> $$6, Optional<HoverEvent> $$7, Optional<String> $$8, Optional<ResourceLocation> $$9) {
/*  60 */     Style $$10 = new Style($$0.orElse(null), $$1.orElse(null), $$2.orElse(null), $$3.orElse(null), $$4.orElse(null), $$5.orElse(null), $$6.orElse(null), $$7.orElse(null), $$8.orElse(null), $$9.orElse(null));
/*  61 */     if ($$10.equals(EMPTY)) {
/*  62 */       return EMPTY;
/*     */     }
/*  64 */     return $$10;
/*     */   }
/*     */   
/*     */   private Style(@Nullable TextColor $$0, @Nullable Boolean $$1, @Nullable Boolean $$2, @Nullable Boolean $$3, @Nullable Boolean $$4, @Nullable Boolean $$5, @Nullable ClickEvent $$6, @Nullable HoverEvent $$7, @Nullable String $$8, @Nullable ResourceLocation $$9) {
/*  68 */     this.color = $$0;
/*  69 */     this.bold = $$1;
/*  70 */     this.italic = $$2;
/*  71 */     this.underlined = $$3;
/*  72 */     this.strikethrough = $$4;
/*  73 */     this.obfuscated = $$5;
/*  74 */     this.clickEvent = $$6;
/*  75 */     this.hoverEvent = $$7;
/*  76 */     this.insertion = $$8;
/*  77 */     this.font = $$9;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TextColor getColor() {
/*  82 */     return this.color;
/*     */   }
/*     */   
/*     */   public boolean isBold() {
/*  86 */     return (this.bold == Boolean.TRUE);
/*     */   }
/*     */   
/*     */   public boolean isItalic() {
/*  90 */     return (this.italic == Boolean.TRUE);
/*     */   }
/*     */   
/*     */   public boolean isStrikethrough() {
/*  94 */     return (this.strikethrough == Boolean.TRUE);
/*     */   }
/*     */   
/*     */   public boolean isUnderlined() {
/*  98 */     return (this.underlined == Boolean.TRUE);
/*     */   }
/*     */   
/*     */   public boolean isObfuscated() {
/* 102 */     return (this.obfuscated == Boolean.TRUE);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 106 */     return (this == EMPTY);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ClickEvent getClickEvent() {
/* 111 */     return this.clickEvent;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public HoverEvent getHoverEvent() {
/* 116 */     return this.hoverEvent;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getInsertion() {
/* 121 */     return this.insertion;
/*     */   }
/*     */   
/*     */   public ResourceLocation getFont() {
/* 125 */     return (this.font != null) ? this.font : DEFAULT_FONT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> Style checkEmptyAfterChange(Style $$0, @Nullable T $$1, @Nullable T $$2) {
/* 131 */     if ($$1 != null && $$2 == null && $$0.equals(EMPTY)) {
/* 132 */       return EMPTY;
/*     */     }
/* 134 */     return $$0;
/*     */   }
/*     */   
/*     */   public Style withColor(@Nullable TextColor $$0) {
/* 138 */     if (Objects.equals(this.color, $$0)) {
/* 139 */       return this;
/*     */     }
/* 141 */     return checkEmptyAfterChange(new Style($$0, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.color, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style withColor(@Nullable ChatFormatting $$0) {
/* 148 */     return withColor(($$0 != null) ? TextColor.fromLegacyFormat($$0) : null);
/*     */   }
/*     */   
/*     */   public Style withColor(int $$0) {
/* 152 */     return withColor(TextColor.fromRgb($$0));
/*     */   }
/*     */   
/*     */   public Style withBold(@Nullable Boolean $$0) {
/* 156 */     if (Objects.equals(this.bold, $$0)) {
/* 157 */       return this;
/*     */     }
/* 159 */     return checkEmptyAfterChange(new Style(this.color, $$0, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.bold, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style withItalic(@Nullable Boolean $$0) {
/* 166 */     if (Objects.equals(this.italic, $$0)) {
/* 167 */       return this;
/*     */     }
/* 169 */     return checkEmptyAfterChange(new Style(this.color, this.bold, $$0, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.italic, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style withUnderlined(@Nullable Boolean $$0) {
/* 176 */     if (Objects.equals(this.underlined, $$0)) {
/* 177 */       return this;
/*     */     }
/* 179 */     return checkEmptyAfterChange(new Style(this.color, this.bold, this.italic, $$0, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.underlined, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style withStrikethrough(@Nullable Boolean $$0) {
/* 186 */     if (Objects.equals(this.strikethrough, $$0)) {
/* 187 */       return this;
/*     */     }
/* 189 */     return checkEmptyAfterChange(new Style(this.color, this.bold, this.italic, this.underlined, $$0, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.strikethrough, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style withObfuscated(@Nullable Boolean $$0) {
/* 196 */     if (Objects.equals(this.obfuscated, $$0)) {
/* 197 */       return this;
/*     */     }
/* 199 */     return checkEmptyAfterChange(new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, $$0, this.clickEvent, this.hoverEvent, this.insertion, this.font), this.obfuscated, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style withClickEvent(@Nullable ClickEvent $$0) {
/* 206 */     if (Objects.equals(this.clickEvent, $$0)) {
/* 207 */       return this;
/*     */     }
/* 209 */     return checkEmptyAfterChange(new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, $$0, this.hoverEvent, this.insertion, this.font), this.clickEvent, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style withHoverEvent(@Nullable HoverEvent $$0) {
/* 216 */     if (Objects.equals(this.hoverEvent, $$0)) {
/* 217 */       return this;
/*     */     }
/* 219 */     return checkEmptyAfterChange(new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, $$0, this.insertion, this.font), this.hoverEvent, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style withInsertion(@Nullable String $$0) {
/* 226 */     if (Objects.equals(this.insertion, $$0)) {
/* 227 */       return this;
/*     */     }
/* 229 */     return checkEmptyAfterChange(new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, $$0, this.font), this.insertion, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style withFont(@Nullable ResourceLocation $$0) {
/* 236 */     if (Objects.equals(this.font, $$0)) {
/* 237 */       return this;
/*     */     }
/* 239 */     return checkEmptyAfterChange(new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, $$0), this.font, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style applyFormat(ChatFormatting $$0) {
/* 246 */     TextColor $$1 = this.color;
/* 247 */     Boolean $$2 = this.bold;
/* 248 */     Boolean $$3 = this.italic;
/* 249 */     Boolean $$4 = this.strikethrough;
/* 250 */     Boolean $$5 = this.underlined;
/* 251 */     Boolean $$6 = this.obfuscated;
/*     */     
/* 253 */     switch ($$0)
/*     */     { case OBFUSCATED:
/* 255 */         $$6 = Boolean.valueOf(true);
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
/*     */ 
/*     */         
/* 275 */         return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);case BOLD: $$2 = Boolean.valueOf(true); return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);case STRIKETHROUGH: $$4 = Boolean.valueOf(true); return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);case UNDERLINE: $$5 = Boolean.valueOf(true); return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);case ITALIC: $$3 = Boolean.valueOf(true); return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);case RESET: return EMPTY; }  $$1 = TextColor.fromLegacyFormat($$0); return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);
/*     */   }
/*     */   
/*     */   public Style applyLegacyFormat(ChatFormatting $$0) {
/* 279 */     TextColor $$1 = this.color;
/* 280 */     Boolean $$2 = this.bold;
/* 281 */     Boolean $$3 = this.italic;
/* 282 */     Boolean $$4 = this.strikethrough;
/* 283 */     Boolean $$5 = this.underlined;
/* 284 */     Boolean $$6 = this.obfuscated;
/*     */     
/* 286 */     switch ($$0)
/*     */     { case OBFUSCATED:
/* 288 */         $$6 = Boolean.valueOf(true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 314 */         return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);case BOLD: $$2 = Boolean.valueOf(true); return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);case STRIKETHROUGH: $$4 = Boolean.valueOf(true); return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);case UNDERLINE: $$5 = Boolean.valueOf(true); return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);case ITALIC: $$3 = Boolean.valueOf(true); return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);case RESET: return EMPTY; }  $$6 = Boolean.valueOf(false); $$2 = Boolean.valueOf(false); $$4 = Boolean.valueOf(false); $$5 = Boolean.valueOf(false); $$3 = Boolean.valueOf(false); $$1 = TextColor.fromLegacyFormat($$0); return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);
/*     */   }
/*     */   
/*     */   public Style applyFormats(ChatFormatting... $$0) {
/* 318 */     TextColor $$1 = this.color;
/* 319 */     Boolean $$2 = this.bold;
/* 320 */     Boolean $$3 = this.italic;
/* 321 */     Boolean $$4 = this.strikethrough;
/* 322 */     Boolean $$5 = this.underlined;
/* 323 */     Boolean $$6 = this.obfuscated;
/*     */     
/* 325 */     for (ChatFormatting $$7 : $$0) {
/* 326 */       switch ($$7) {
/*     */         case OBFUSCATED:
/* 328 */           $$6 = Boolean.valueOf(true);
/*     */           break;
/*     */         case BOLD:
/* 331 */           $$2 = Boolean.valueOf(true);
/*     */           break;
/*     */         case STRIKETHROUGH:
/* 334 */           $$4 = Boolean.valueOf(true);
/*     */           break;
/*     */         case UNDERLINE:
/* 337 */           $$5 = Boolean.valueOf(true);
/*     */           break;
/*     */         case ITALIC:
/* 340 */           $$3 = Boolean.valueOf(true);
/*     */           break;
/*     */         case RESET:
/* 343 */           return EMPTY;
/*     */         default:
/* 345 */           $$1 = TextColor.fromLegacyFormat($$7);
/*     */           break;
/*     */       } 
/*     */     } 
/* 349 */     return new Style($$1, $$2, $$3, $$5, $$4, $$6, this.clickEvent, this.hoverEvent, this.insertion, this.font);
/*     */   }
/*     */   
/*     */   public Style applyTo(Style $$0) {
/* 353 */     if (this == EMPTY) {
/* 354 */       return $$0;
/*     */     }
/*     */     
/* 357 */     if ($$0 == EMPTY) {
/* 358 */       return this;
/*     */     }
/*     */     
/* 361 */     return new Style(
/* 362 */         (this.color != null) ? this.color : $$0.color, 
/* 363 */         (this.bold != null) ? this.bold : $$0.bold, 
/* 364 */         (this.italic != null) ? this.italic : $$0.italic, 
/* 365 */         (this.underlined != null) ? this.underlined : $$0.underlined, 
/* 366 */         (this.strikethrough != null) ? this.strikethrough : $$0.strikethrough, 
/* 367 */         (this.obfuscated != null) ? this.obfuscated : $$0.obfuscated, 
/* 368 */         (this.clickEvent != null) ? this.clickEvent : $$0.clickEvent, 
/* 369 */         (this.hoverEvent != null) ? this.hoverEvent : $$0.hoverEvent, 
/* 370 */         (this.insertion != null) ? this.insertion : $$0.insertion, 
/* 371 */         (this.font != null) ? this.font : $$0.font);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 377 */     final StringBuilder result = new StringBuilder("{");
/*     */     class Collector
/*     */     {
/*     */       private boolean isNotFirst;
/*     */       
/*     */       private void prependSeparator() {
/* 383 */         if (this.isNotFirst) {
/* 384 */           result.append(',');
/*     */         }
/* 386 */         this.isNotFirst = true;
/*     */       }
/*     */       
/*     */       void addFlagString(String $$0, @Nullable Boolean $$1) {
/* 390 */         if ($$1 != null) {
/* 391 */           prependSeparator();
/* 392 */           if (!$$1.booleanValue()) {
/* 393 */             result.append('!');
/*     */           }
/* 395 */           result.append($$0);
/*     */         } 
/*     */       }
/*     */       
/*     */       void addValueString(String $$0, @Nullable Object $$1) {
/* 400 */         if ($$1 != null) {
/* 401 */           prependSeparator();
/* 402 */           result.append($$0);
/* 403 */           result.append('=');
/* 404 */           result.append($$1);
/*     */         } 
/*     */       }
/*     */     };
/*     */     
/* 409 */     Collector $$1 = new Collector();
/*     */     
/* 411 */     $$1.addValueString("color", this.color);
/*     */     
/* 413 */     $$1.addFlagString("bold", this.bold);
/* 414 */     $$1.addFlagString("italic", this.italic);
/* 415 */     $$1.addFlagString("underlined", this.underlined);
/* 416 */     $$1.addFlagString("strikethrough", this.strikethrough);
/* 417 */     $$1.addFlagString("obfuscated", this.obfuscated);
/*     */     
/* 419 */     $$1.addValueString("clickEvent", this.clickEvent);
/* 420 */     $$1.addValueString("hoverEvent", this.hoverEvent);
/* 421 */     $$1.addValueString("insertion", this.insertion);
/* 422 */     $$1.addValueString("font", this.font);
/*     */     
/* 424 */     $$0.append("}");
/* 425 */     return $$0.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 430 */     if (this == $$0) {
/* 431 */       return true;
/*     */     }
/* 433 */     if ($$0 instanceof Style) { Style $$1 = (Style)$$0;
/* 434 */       return (this.bold == $$1.bold && 
/* 435 */         Objects.equals(getColor(), $$1.getColor()) && this.italic == $$1.italic && this.obfuscated == $$1.obfuscated && this.strikethrough == $$1.strikethrough && this.underlined == $$1.underlined && 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 440 */         Objects.equals(this.clickEvent, $$1.clickEvent) && 
/* 441 */         Objects.equals(this.hoverEvent, $$1.hoverEvent) && 
/* 442 */         Objects.equals(this.insertion, $$1.insertion) && 
/* 443 */         Objects.equals(this.font, $$1.font)); }
/*     */ 
/*     */ 
/*     */     
/* 447 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 452 */     return Objects.hash(new Object[] { this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\Style.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */