/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.ChatFormatting;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum BossBarColor
/*     */ {
/*  90 */   PINK("pink", ChatFormatting.RED),
/*  91 */   BLUE("blue", ChatFormatting.BLUE),
/*  92 */   RED("red", ChatFormatting.DARK_RED),
/*  93 */   GREEN("green", ChatFormatting.GREEN),
/*  94 */   YELLOW("yellow", ChatFormatting.YELLOW),
/*  95 */   PURPLE("purple", ChatFormatting.DARK_BLUE),
/*  96 */   WHITE("white", ChatFormatting.WHITE);
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final ChatFormatting formatting;
/*     */   
/*     */   BossBarColor(String $$0, ChatFormatting $$1) {
/* 103 */     this.name = $$0;
/* 104 */     this.formatting = $$1;
/*     */   }
/*     */   
/*     */   public ChatFormatting getFormatting() {
/* 108 */     return this.formatting;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 112 */     return this.name;
/*     */   }
/*     */   
/*     */   public static BossBarColor byName(String $$0) {
/* 116 */     for (BossBarColor $$1 : values()) {
/* 117 */       if ($$1.name.equals($$0)) {
/* 118 */         return $$1;
/*     */       }
/*     */     } 
/* 121 */     return WHITE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\BossEvent$BossBarColor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */