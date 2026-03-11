/*     */ package net.minecraft.world.scores;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.numbers.NumberFormat;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
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
/*     */ class null
/*     */   implements ScoreAccess
/*     */ {
/*     */   public int get() {
/*  83 */     return score.value();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int $$0) {
/*  88 */     if (!canModify) {
/*  89 */       throw new IllegalStateException("Cannot modify read-only score");
/*     */     }
/*     */     
/*  92 */     boolean $$1 = requiresSync.isTrue();
/*     */     
/*  94 */     if (objective.displayAutoUpdate()) {
/*  95 */       Component $$2 = scoreHolder.getDisplayName();
/*  96 */       if ($$2 != null && !$$2.equals(score.display())) {
/*  97 */         score.display($$2);
/*  98 */         $$1 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     if ($$0 != score.value()) {
/* 103 */       score.value($$0);
/* 104 */       $$1 = true;
/*     */     } 
/*     */     
/* 107 */     if ($$1) {
/* 108 */       sendScoreToPlayers();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component display() {
/* 115 */     return score.display();
/*     */   }
/*     */ 
/*     */   
/*     */   public void display(@Nullable Component $$0) {
/* 120 */     if (requiresSync.isTrue() || !Objects.equals($$0, score.display())) {
/* 121 */       score.display($$0);
/* 122 */       sendScoreToPlayers();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void numberFormatOverride(@Nullable NumberFormat $$0) {
/* 128 */     score.numberFormat($$0);
/* 129 */     sendScoreToPlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean locked() {
/* 134 */     return score.isLocked();
/*     */   }
/*     */ 
/*     */   
/*     */   public void unlock() {
/* 139 */     setLocked(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void lock() {
/* 144 */     setLocked(true);
/*     */   }
/*     */   
/*     */   private void setLocked(boolean $$0) {
/* 148 */     score.setLocked($$0);
/*     */     
/* 150 */     if (requiresSync.isTrue()) {
/* 151 */       sendScoreToPlayers();
/*     */     }
/*     */     
/* 154 */     Scoreboard.this.onScoreLockChanged(scoreHolder, objective);
/*     */   }
/*     */   
/*     */   private void sendScoreToPlayers() {
/* 158 */     Scoreboard.this.onScoreChanged(scoreHolder, objective, score);
/* 159 */     requiresSync.setFalse();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\Scoreboard$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */