/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import net.minecraft.world.entity.MobCategory;
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
/*    */ class MobCounts
/*    */ {
/* 48 */   private final Object2IntMap<MobCategory> counts = (Object2IntMap<MobCategory>)new Object2IntOpenHashMap((MobCategory.values()).length);
/*    */   
/*    */   public void add(MobCategory $$0) {
/* 51 */     this.counts.computeInt($$0, ($$0, $$1) -> Integer.valueOf(($$1 == null) ? 1 : ($$1.intValue() + 1)));
/*    */   }
/*    */   
/*    */   public boolean canSpawn(MobCategory $$0) {
/* 55 */     return (this.counts.getOrDefault($$0, 0) < $$0.getMaxInstancesPerChunk());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\LocalMobCapCalculator$MobCounts.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */