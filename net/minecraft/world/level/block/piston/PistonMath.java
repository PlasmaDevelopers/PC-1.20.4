/*    */ package net.minecraft.world.level.block.piston;
/*    */ 
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PistonMath
/*    */ {
/*    */   public static AABB getMovementArea(AABB $$0, Direction $$1, double $$2) {
/* 15 */     double $$3 = $$2 * $$1.getAxisDirection().getStep();
/* 16 */     double $$4 = Math.min($$3, 0.0D);
/* 17 */     double $$5 = Math.max($$3, 0.0D);
/* 18 */     switch ($$1)
/*    */     { case WEST:
/* 20 */         return new AABB($$0.minX + $$4, $$0.minY, $$0.minZ, $$0.minX + $$5, $$0.maxY, $$0.maxZ);
/*    */       case EAST:
/* 22 */         return new AABB($$0.maxX + $$4, $$0.minY, $$0.minZ, $$0.maxX + $$5, $$0.maxY, $$0.maxZ);
/*    */       case DOWN:
/* 24 */         return new AABB($$0.minX, $$0.minY + $$4, $$0.minZ, $$0.maxX, $$0.minY + $$5, $$0.maxZ);
/*    */       
/*    */       default:
/* 27 */         return new AABB($$0.minX, $$0.maxY + $$4, $$0.minZ, $$0.maxX, $$0.maxY + $$5, $$0.maxZ);
/*    */       case NORTH:
/* 29 */         return new AABB($$0.minX, $$0.minY, $$0.minZ + $$4, $$0.maxX, $$0.maxY, $$0.minZ + $$5);
/*    */       case SOUTH:
/* 31 */         break; }  return new AABB($$0.minX, $$0.minY, $$0.maxZ + $$4, $$0.maxX, $$0.maxY, $$0.maxZ + $$5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\piston\PistonMath.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */