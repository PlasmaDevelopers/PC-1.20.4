/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Data
/*    */ {
/*    */   final Heightmap.Types heightMap;
/*    */   final SpawnPlacements.Type placement;
/*    */   final SpawnPlacements.SpawnPredicate<?> predicate;
/*    */   
/*    */   public Data(Heightmap.Types $$0, SpawnPlacements.Type $$1, SpawnPlacements.SpawnPredicate<?> $$2) {
/* 58 */     this.heightMap = $$0;
/* 59 */     this.placement = $$1;
/* 60 */     this.predicate = $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\SpawnPlacements$Data.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */