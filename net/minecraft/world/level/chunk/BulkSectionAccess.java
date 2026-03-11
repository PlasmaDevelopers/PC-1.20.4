/*    */ package net.minecraft.world.level.chunk;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BulkSectionAccess implements AutoCloseable {
/*    */   private final LevelAccessor level;
/* 15 */   private final Long2ObjectMap<LevelChunkSection> acquiredSections = (Long2ObjectMap<LevelChunkSection>)new Long2ObjectOpenHashMap();
/*    */   @Nullable
/*    */   private LevelChunkSection lastSection;
/*    */   private long lastSectionKey;
/*    */   
/*    */   public BulkSectionAccess(LevelAccessor $$0) {
/* 21 */     this.level = $$0;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public LevelChunkSection getSection(BlockPos $$0) {
/* 26 */     int $$1 = this.level.getSectionIndex($$0.getY());
/* 27 */     if ($$1 < 0 || $$1 >= this.level.getSectionsCount()) {
/* 28 */       return null;
/*    */     }
/* 30 */     long $$2 = SectionPos.asLong($$0);
/* 31 */     if (this.lastSection == null || this.lastSectionKey != $$2) {
/* 32 */       this.lastSection = (LevelChunkSection)this.acquiredSections.computeIfAbsent($$2, $$2 -> {
/*    */             ChunkAccess $$3 = this.level.getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
/*    */             LevelChunkSection $$4 = $$3.getSection($$1);
/*    */             $$4.acquire();
/*    */             return $$4;
/*    */           });
/* 38 */       this.lastSectionKey = $$2;
/*    */     } 
/* 40 */     return this.lastSection;
/*    */   }
/*    */   
/*    */   public BlockState getBlockState(BlockPos $$0) {
/* 44 */     LevelChunkSection $$1 = getSection($$0);
/*    */     
/* 46 */     if ($$1 == null) {
/* 47 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/* 49 */     int $$2 = SectionPos.sectionRelative($$0.getX());
/* 50 */     int $$3 = SectionPos.sectionRelative($$0.getY());
/* 51 */     int $$4 = SectionPos.sectionRelative($$0.getZ());
/* 52 */     return $$1.getBlockState($$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 57 */     for (ObjectIterator<LevelChunkSection> objectIterator = this.acquiredSections.values().iterator(); objectIterator.hasNext(); ) { LevelChunkSection $$0 = objectIterator.next();
/* 58 */       $$0.release(); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\BulkSectionAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */