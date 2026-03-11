/*     */ package net.minecraft.world;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public abstract class BossEvent
/*     */ {
/*     */   private final UUID id;
/*     */   protected Component name;
/*     */   protected float progress;
/*     */   protected BossBarColor color;
/*     */   protected BossBarOverlay overlay;
/*     */   protected boolean darkenScreen;
/*     */   protected boolean playBossMusic;
/*     */   protected boolean createWorldFog;
/*     */   
/*     */   public BossEvent(UUID $$0, Component $$1, BossBarColor $$2, BossBarOverlay $$3) {
/*  19 */     this.id = $$0;
/*  20 */     this.name = $$1;
/*  21 */     this.color = $$2;
/*  22 */     this.overlay = $$3;
/*  23 */     this.progress = 1.0F;
/*     */   }
/*     */   
/*     */   public UUID getId() {
/*  27 */     return this.id;
/*     */   }
/*     */   
/*     */   public Component getName() {
/*  31 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(Component $$0) {
/*  35 */     this.name = $$0;
/*     */   }
/*     */   
/*     */   public float getProgress() {
/*  39 */     return this.progress;
/*     */   }
/*     */   
/*     */   public void setProgress(float $$0) {
/*  43 */     this.progress = $$0;
/*     */   }
/*     */   
/*     */   public BossBarColor getColor() {
/*  47 */     return this.color;
/*     */   }
/*     */   
/*     */   public void setColor(BossBarColor $$0) {
/*  51 */     this.color = $$0;
/*     */   }
/*     */   
/*     */   public BossBarOverlay getOverlay() {
/*  55 */     return this.overlay;
/*     */   }
/*     */   
/*     */   public void setOverlay(BossBarOverlay $$0) {
/*  59 */     this.overlay = $$0;
/*     */   }
/*     */   
/*     */   public boolean shouldDarkenScreen() {
/*  63 */     return this.darkenScreen;
/*     */   }
/*     */   
/*     */   public BossEvent setDarkenScreen(boolean $$0) {
/*  67 */     this.darkenScreen = $$0;
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public boolean shouldPlayBossMusic() {
/*  72 */     return this.playBossMusic;
/*     */   }
/*     */   
/*     */   public BossEvent setPlayBossMusic(boolean $$0) {
/*  76 */     this.playBossMusic = $$0;
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   public BossEvent setCreateWorldFog(boolean $$0) {
/*  81 */     this.createWorldFog = $$0;
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public boolean shouldCreateWorldFog() {
/*  86 */     return this.createWorldFog;
/*     */   }
/*     */   
/*     */   public enum BossBarColor {
/*  90 */     PINK("pink", ChatFormatting.RED),
/*  91 */     BLUE("blue", ChatFormatting.BLUE),
/*  92 */     RED("red", ChatFormatting.DARK_RED),
/*  93 */     GREEN("green", ChatFormatting.GREEN),
/*  94 */     YELLOW("yellow", ChatFormatting.YELLOW),
/*  95 */     PURPLE("purple", ChatFormatting.DARK_BLUE),
/*  96 */     WHITE("white", ChatFormatting.WHITE);
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private final ChatFormatting formatting;
/*     */     
/*     */     BossBarColor(String $$0, ChatFormatting $$1) {
/* 103 */       this.name = $$0;
/* 104 */       this.formatting = $$1;
/*     */     }
/*     */     
/*     */     public ChatFormatting getFormatting() {
/* 108 */       return this.formatting;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 112 */       return this.name;
/*     */     }
/*     */     
/*     */     public static BossBarColor byName(String $$0) {
/* 116 */       for (BossBarColor $$1 : values()) {
/* 117 */         if ($$1.name.equals($$0)) {
/* 118 */           return $$1;
/*     */         }
/*     */       } 
/* 121 */       return WHITE;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum BossBarOverlay {
/* 126 */     PROGRESS("progress"),
/* 127 */     NOTCHED_6("notched_6"),
/* 128 */     NOTCHED_10("notched_10"),
/* 129 */     NOTCHED_12("notched_12"),
/* 130 */     NOTCHED_20("notched_20");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     BossBarOverlay(String $$0) {
/* 136 */       this.name = $$0;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 140 */       return this.name;
/*     */     }
/*     */     
/*     */     public static BossBarOverlay byName(String $$0) {
/* 144 */       for (BossBarOverlay $$1 : values()) {
/* 145 */         if ($$1.name.equals($$0)) {
/* 146 */           return $$1;
/*     */         }
/*     */       } 
/* 149 */       return PROGRESS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\BossEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */