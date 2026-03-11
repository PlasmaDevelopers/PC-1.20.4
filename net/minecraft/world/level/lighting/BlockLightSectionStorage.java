/*    */ package net.minecraft.world.level.lighting;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.world.level.LightLayer;
/*    */ import net.minecraft.world.level.chunk.DataLayer;
/*    */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*    */ 
/*    */ public class BlockLightSectionStorage extends LayerLightSectionStorage<BlockLightSectionStorage.BlockDataLayerStorageMap> {
/*    */   protected BlockLightSectionStorage(LightChunkGetter $$0) {
/* 12 */     super(LightLayer.BLOCK, $$0, new BlockDataLayerStorageMap(new Long2ObjectOpenHashMap()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getLightValue(long $$0) {
/* 17 */     long $$1 = SectionPos.blockToSection($$0);
/* 18 */     DataLayer $$2 = getDataLayer($$1, false);
/* 19 */     if ($$2 == null) {
/* 20 */       return 0;
/*    */     }
/* 22 */     return $$2.get(
/* 23 */         SectionPos.sectionRelative(BlockPos.getX($$0)), 
/* 24 */         SectionPos.sectionRelative(BlockPos.getY($$0)), 
/* 25 */         SectionPos.sectionRelative(BlockPos.getZ($$0)));
/*    */   }
/*    */   
/*    */   protected static final class BlockDataLayerStorageMap
/*    */     extends DataLayerStorageMap<BlockDataLayerStorageMap> {
/*    */     public BlockDataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> $$0) {
/* 31 */       super($$0);
/*    */     }
/*    */ 
/*    */     
/*    */     public BlockDataLayerStorageMap copy() {
/* 36 */       return new BlockDataLayerStorageMap(this.map.clone());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\BlockLightSectionStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */