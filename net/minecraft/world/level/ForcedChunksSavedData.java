/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.util.datafix.DataFixTypes;
/*    */ import net.minecraft.world.level.saveddata.SavedData;
/*    */ 
/*    */ public class ForcedChunksSavedData
/*    */   extends SavedData {
/*    */   public static final String FILE_ID = "chunks";
/*    */   
/*    */   public static SavedData.Factory<ForcedChunksSavedData> factory() {
/* 15 */     return new SavedData.Factory(ForcedChunksSavedData::new, ForcedChunksSavedData::load, DataFixTypes.SAVED_DATA_FORCED_CHUNKS);
/*    */   }
/*    */   private static final String TAG_FORCED = "Forced"; private final LongSet chunks;
/*    */   private ForcedChunksSavedData(LongSet $$0) {
/* 19 */     this.chunks = $$0;
/*    */   }
/*    */   
/*    */   public ForcedChunksSavedData() {
/* 23 */     this((LongSet)new LongOpenHashSet());
/*    */   }
/*    */   
/*    */   public static ForcedChunksSavedData load(CompoundTag $$0) {
/* 27 */     return new ForcedChunksSavedData((LongSet)new LongOpenHashSet($$0.getLongArray("Forced")));
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag save(CompoundTag $$0) {
/* 32 */     $$0.putLongArray("Forced", this.chunks.toLongArray());
/* 33 */     return $$0;
/*    */   }
/*    */   
/*    */   public LongSet getChunks() {
/* 37 */     return this.chunks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\ForcedChunksSavedData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */