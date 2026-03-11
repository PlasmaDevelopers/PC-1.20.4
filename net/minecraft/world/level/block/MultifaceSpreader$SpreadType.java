/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum SpreadType
/*     */ {
/* 165 */   SAME_POSITION
/*     */   {
/*     */     public MultifaceSpreader.SpreadPos getSpreadPos(BlockPos $$0, Direction $$1, Direction $$2) {
/* 168 */       return new MultifaceSpreader.SpreadPos($$0, $$1);
/*     */     }
/*     */   },
/* 171 */   SAME_PLANE
/*     */   {
/*     */     public MultifaceSpreader.SpreadPos getSpreadPos(BlockPos $$0, Direction $$1, Direction $$2) {
/* 174 */       return new MultifaceSpreader.SpreadPos($$0.relative($$1), $$2);
/*     */     }
/*     */   },
/* 177 */   WRAP_AROUND
/*     */   {
/*     */     public MultifaceSpreader.SpreadPos getSpreadPos(BlockPos $$0, Direction $$1, Direction $$2) {
/* 180 */       return new MultifaceSpreader.SpreadPos($$0.relative($$1).relative($$2), $$1.getOpposite());
/*     */     }
/*     */   };
/*     */   
/*     */   public abstract MultifaceSpreader.SpreadPos getSpreadPos(BlockPos paramBlockPos, Direction paramDirection1, Direction paramDirection2);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MultifaceSpreader$SpreadType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */