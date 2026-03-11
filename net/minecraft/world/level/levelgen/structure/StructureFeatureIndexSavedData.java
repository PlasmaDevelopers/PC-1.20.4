/*    */ package net.minecraft.world.level.levelgen.structure;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.util.datafix.DataFixTypes;
/*    */ import net.minecraft.world.level.saveddata.SavedData;
/*    */ 
/*    */ public class StructureFeatureIndexSavedData
/*    */   extends SavedData {
/*    */   private static final String TAG_REMAINING_INDEXES = "Remaining";
/*    */   private static final String TAG_All_INDEXES = "All";
/*    */   
/*    */   public static SavedData.Factory<StructureFeatureIndexSavedData> factory() {
/* 16 */     return new SavedData.Factory(StructureFeatureIndexSavedData::new, StructureFeatureIndexSavedData::load, DataFixTypes.SAVED_DATA_STRUCTURE_FEATURE_INDICES);
/*    */   }
/*    */   private final LongSet all; private final LongSet remaining;
/*    */   private StructureFeatureIndexSavedData(LongSet $$0, LongSet $$1) {
/* 20 */     this.all = $$0;
/* 21 */     this.remaining = $$1;
/*    */   }
/*    */   
/*    */   public StructureFeatureIndexSavedData() {
/* 25 */     this((LongSet)new LongOpenHashSet(), (LongSet)new LongOpenHashSet());
/*    */   }
/*    */   
/*    */   public static StructureFeatureIndexSavedData load(CompoundTag $$0) {
/* 29 */     return new StructureFeatureIndexSavedData((LongSet)new LongOpenHashSet($$0.getLongArray("All")), (LongSet)new LongOpenHashSet($$0.getLongArray("Remaining")));
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag save(CompoundTag $$0) {
/* 34 */     $$0.putLongArray("All", this.all.toLongArray());
/* 35 */     $$0.putLongArray("Remaining", this.remaining.toLongArray());
/* 36 */     return $$0;
/*    */   }
/*    */   
/*    */   public void addIndex(long $$0) {
/* 40 */     this.all.add($$0);
/* 41 */     this.remaining.add($$0);
/*    */   }
/*    */   
/*    */   public boolean hasStartIndex(long $$0) {
/* 45 */     return this.all.contains($$0);
/*    */   }
/*    */   
/*    */   public boolean hasUnhandledIndex(long $$0) {
/* 49 */     return this.remaining.contains($$0);
/*    */   }
/*    */   
/*    */   public void removeIndex(long $$0) {
/* 53 */     this.remaining.remove($$0);
/*    */   }
/*    */   
/*    */   public LongSet getAll() {
/* 57 */     return this.all;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\StructureFeatureIndexSavedData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */