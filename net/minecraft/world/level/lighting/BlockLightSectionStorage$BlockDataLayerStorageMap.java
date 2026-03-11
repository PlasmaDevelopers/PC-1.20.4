/*    */ package net.minecraft.world.level.lighting;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import net.minecraft.world.level.chunk.DataLayer;
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
/*    */ public final class BlockDataLayerStorageMap
/*    */   extends DataLayerStorageMap<BlockLightSectionStorage.BlockDataLayerStorageMap>
/*    */ {
/*    */   public BlockDataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> $$0) {
/* 31 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockDataLayerStorageMap copy() {
/* 36 */     return new BlockDataLayerStorageMap(this.map.clone());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\BlockLightSectionStorage$BlockDataLayerStorageMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */