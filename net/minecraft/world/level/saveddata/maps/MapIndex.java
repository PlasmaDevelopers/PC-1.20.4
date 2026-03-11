/*    */ package net.minecraft.world.level.saveddata.maps;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.util.datafix.DataFixTypes;
/*    */ import net.minecraft.world.level.saveddata.SavedData;
/*    */ 
/*    */ public class MapIndex extends SavedData {
/*    */   public static final String FILE_NAME = "idcounts";
/* 13 */   private final Object2IntMap<String> usedAuxIds = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*    */   
/*    */   public static SavedData.Factory<MapIndex> factory() {
/* 16 */     return new SavedData.Factory(MapIndex::new, MapIndex::load, DataFixTypes.SAVED_DATA_MAP_INDEX);
/*    */   }
/*    */   
/*    */   public MapIndex() {
/* 20 */     this.usedAuxIds.defaultReturnValue(-1);
/*    */   }
/*    */   
/*    */   public static MapIndex load(CompoundTag $$0) {
/* 24 */     MapIndex $$1 = new MapIndex();
/* 25 */     for (String $$2 : $$0.getAllKeys()) {
/* 26 */       if ($$0.contains($$2, 99)) {
/* 27 */         $$1.usedAuxIds.put($$2, $$0.getInt($$2));
/*    */       }
/*    */     } 
/* 30 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag save(CompoundTag $$0) {
/* 35 */     for (ObjectIterator<Object2IntMap.Entry<String>> objectIterator = this.usedAuxIds.object2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<String> $$1 = objectIterator.next();
/* 36 */       $$0.putInt((String)$$1.getKey(), $$1.getIntValue()); }
/*    */     
/* 38 */     return $$0;
/*    */   }
/*    */   
/*    */   public int getFreeAuxValueForMap() {
/* 42 */     int $$0 = this.usedAuxIds.getInt("map") + 1;
/* 43 */     this.usedAuxIds.put("map", $$0);
/* 44 */     setDirty();
/* 45 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\saveddata\maps\MapIndex.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */