/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.InfestedBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
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
/*     */ class SilverfishWakeUpFriendsGoal
/*     */   extends Goal
/*     */ {
/*     */   private final Silverfish silverfish;
/*     */   private int lookForFriends;
/*     */   
/*     */   public SilverfishWakeUpFriendsGoal(Silverfish $$0) {
/* 158 */     this.silverfish = $$0;
/*     */   }
/*     */   
/*     */   public void notifyHurt() {
/* 162 */     if (this.lookForFriends == 0) {
/* 163 */       this.lookForFriends = adjustedTickDelay(20);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 169 */     return (this.lookForFriends > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 174 */     this.lookForFriends--;
/* 175 */     if (this.lookForFriends <= 0) {
/* 176 */       Level $$0 = this.silverfish.level();
/* 177 */       RandomSource $$1 = this.silverfish.getRandom();
/*     */ 
/*     */       
/* 180 */       BlockPos $$2 = this.silverfish.blockPosition();
/*     */       
/*     */       int $$3;
/* 183 */       for ($$3 = 0; $$3 <= 5 && $$3 >= -5; $$3 = (($$3 <= 0) ? 1 : 0) - $$3) {
/* 184 */         int $$4; for ($$4 = 0; $$4 <= 10 && $$4 >= -10; $$4 = (($$4 <= 0) ? 1 : 0) - $$4) {
/* 185 */           int $$5; for ($$5 = 0; $$5 <= 10 && $$5 >= -10; $$5 = (($$5 <= 0) ? 1 : 0) - $$5) {
/* 186 */             BlockPos $$6 = $$2.offset($$4, $$3, $$5);
/* 187 */             BlockState $$7 = $$0.getBlockState($$6);
/*     */             
/* 189 */             Block $$8 = $$7.getBlock();
/* 190 */             if ($$8 instanceof InfestedBlock) {
/* 191 */               if ($$0.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 192 */                 $$0.destroyBlock($$6, true, (Entity)this.silverfish);
/*     */               } else {
/* 194 */                 $$0.setBlock($$6, ((InfestedBlock)$$8).hostStateByInfested($$0.getBlockState($$6)), 3);
/*     */               } 
/* 196 */               if ($$1.nextBoolean())
/*     */                 // Byte code: goto -> 242 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Silverfish$SilverfishWakeUpFriendsGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */