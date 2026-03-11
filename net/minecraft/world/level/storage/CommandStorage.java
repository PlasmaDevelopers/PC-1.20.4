/*    */ package net.minecraft.world.level.storage;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.datafix.DataFixTypes;
/*    */ import net.minecraft.world.level.saveddata.SavedData;
/*    */ 
/*    */ public class CommandStorage {
/*    */   private static final String ID_PREFIX = "command_storage_";
/*    */   
/*    */   private static class Container extends SavedData {
/* 15 */     private final Map<String, CompoundTag> storage = Maps.newHashMap(); private static final String TAG_CONTENTS = "contents";
/*    */     
/*    */     Container load(CompoundTag $$0) {
/* 18 */       CompoundTag $$1 = $$0.getCompound("contents");
/* 19 */       for (String $$2 : $$1.getAllKeys()) {
/* 20 */         this.storage.put($$2, $$1.getCompound($$2));
/*    */       }
/* 22 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public CompoundTag save(CompoundTag $$0) {
/* 27 */       CompoundTag $$1 = new CompoundTag();
/* 28 */       this.storage.forEach(($$1, $$2) -> $$0.put($$1, (Tag)$$2.copy()));
/* 29 */       $$0.put("contents", (Tag)$$1);
/* 30 */       return $$0;
/*    */     }
/*    */     
/*    */     public CompoundTag get(String $$0) {
/* 34 */       CompoundTag $$1 = this.storage.get($$0);
/* 35 */       return ($$1 != null) ? $$1 : new CompoundTag();
/*    */     }
/*    */     
/*    */     public void put(String $$0, CompoundTag $$1) {
/* 39 */       if ($$1.isEmpty()) {
/* 40 */         this.storage.remove($$0);
/*    */       } else {
/* 42 */         this.storage.put($$0, $$1);
/*    */       } 
/* 44 */       setDirty();
/*    */     }
/*    */     
/*    */     public Stream<ResourceLocation> getKeys(String $$0) {
/* 48 */       return this.storage.keySet().stream().map($$1 -> new ResourceLocation($$0, $$1));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 53 */   private final Map<String, Container> namespaces = Maps.newHashMap();
/*    */   private final DimensionDataStorage storage;
/*    */   
/*    */   public CommandStorage(DimensionDataStorage $$0) {
/* 57 */     this.storage = $$0;
/*    */   }
/*    */   
/*    */   private Container newStorage(String $$0) {
/* 61 */     Container $$1 = new Container();
/* 62 */     this.namespaces.put($$0, $$1);
/* 63 */     return $$1;
/*    */   }
/*    */   
/*    */   private SavedData.Factory<Container> factory(String $$0) {
/* 67 */     return new SavedData.Factory(() -> newStorage($$0), $$1 -> newStorage($$0).load($$1), DataFixTypes.SAVED_DATA_COMMAND_STORAGE);
/*    */   }
/*    */   
/*    */   public CompoundTag get(ResourceLocation $$0) {
/* 71 */     String $$1 = $$0.getNamespace();
/* 72 */     Container $$2 = this.storage.<Container>get(factory($$1), createId($$1));
/* 73 */     return ($$2 != null) ? $$2.get($$0.getPath()) : new CompoundTag();
/*    */   }
/*    */   
/*    */   public void set(ResourceLocation $$0, CompoundTag $$1) {
/* 77 */     String $$2 = $$0.getNamespace();
/* 78 */     ((Container)this.storage.<Container>computeIfAbsent(factory($$2), createId($$2))).put($$0.getPath(), $$1);
/*    */   }
/*    */   
/*    */   public Stream<ResourceLocation> keys() {
/* 82 */     return this.namespaces.entrySet().stream().flatMap($$0 -> ((Container)$$0.getValue()).getKeys((String)$$0.getKey()));
/*    */   }
/*    */   
/*    */   private static String createId(String $$0) {
/* 86 */     return "command_storage_" + $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\CommandStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */