/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ 
/*    */ public abstract class Goal
/*    */ {
/*  9 */   private final EnumSet<Flag> flags = EnumSet.noneOf(Flag.class);
/*    */   
/*    */   public abstract boolean canUse();
/*    */   
/*    */   public boolean canContinueToUse() {
/* 14 */     return canUse();
/*    */   }
/*    */   
/*    */   public boolean isInterruptable() {
/* 18 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {}
/*    */ 
/*    */   
/*    */   public void stop() {}
/*    */   
/*    */   public boolean requiresUpdateEveryTick() {
/* 28 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {}
/*    */   
/*    */   public void setFlags(EnumSet<Flag> $$0) {
/* 35 */     this.flags.clear();
/* 36 */     this.flags.addAll($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 41 */     return getClass().getSimpleName();
/*    */   }
/*    */   
/*    */   public EnumSet<Flag> getFlags() {
/* 45 */     return this.flags;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int adjustedTickDelay(int $$0) {
/* 50 */     return requiresUpdateEveryTick() ? $$0 : reducedTickDelay($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static int reducedTickDelay(int $$0) {
/* 57 */     return Mth.positiveCeilDiv($$0, 2);
/*    */   }
/*    */   
/*    */   public enum Flag {
/* 61 */     MOVE,
/* 62 */     LOOK,
/* 63 */     JUMP,
/* 64 */     TARGET;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\Goal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */