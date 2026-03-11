/*     */ package net.minecraft.client.renderer.block;
/*     */ 
/*     */ import net.minecraft.Util;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ enum AmbientVertexRemap
/*     */ {
/*     */   final int vert0;
/*     */   final int vert1;
/*     */   final int vert2;
/* 260 */   DOWN(0, 1, 2, 3),
/* 261 */   UP(2, 3, 0, 1),
/* 262 */   NORTH(3, 0, 1, 2),
/* 263 */   SOUTH(0, 1, 2, 3),
/* 264 */   WEST(3, 0, 1, 2),
/* 265 */   EAST(1, 2, 3, 0);
/*     */   
/*     */   final int vert3;
/*     */   
/*     */   private static final AmbientVertexRemap[] BY_FACING;
/*     */   
/*     */   static {
/* 272 */     BY_FACING = (AmbientVertexRemap[])Util.make(new AmbientVertexRemap[6], $$0 -> {
/*     */           $$0[Direction.DOWN.get3DDataValue()] = DOWN;
/*     */           $$0[Direction.UP.get3DDataValue()] = UP;
/*     */           $$0[Direction.NORTH.get3DDataValue()] = NORTH;
/*     */           $$0[Direction.SOUTH.get3DDataValue()] = SOUTH;
/*     */           $$0[Direction.WEST.get3DDataValue()] = WEST;
/*     */           $$0[Direction.EAST.get3DDataValue()] = EAST;
/*     */         });
/*     */   }
/*     */   AmbientVertexRemap(int $$0, int $$1, int $$2, int $$3) {
/* 282 */     this.vert0 = $$0;
/* 283 */     this.vert1 = $$1;
/* 284 */     this.vert2 = $$2;
/* 285 */     this.vert3 = $$3;
/*     */   }
/*     */   
/*     */   public static AmbientVertexRemap fromFacing(Direction $$0) {
/* 289 */     return BY_FACING[$$0.get3DDataValue()];
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\ModelBlockRenderer$AmbientVertexRemap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */