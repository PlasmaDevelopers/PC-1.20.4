/*    */ package net.minecraft.world.level.storage;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.saveddata.SavedData;
/*    */ 
/*    */ class Container
/*    */   extends SavedData
/*    */ {
/*    */   private static final String TAG_CONTENTS = "contents";
/* 15 */   private final Map<String, CompoundTag> storage = Maps.newHashMap();
/*    */   
/*    */   Container load(CompoundTag $$0) {
/* 18 */     CompoundTag $$1 = $$0.getCompound("contents");
/* 19 */     for (String $$2 : $$1.getAllKeys()) {
/* 20 */       this.storage.put($$2, $$1.getCompound($$2));
/*    */     }
/* 22 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag save(CompoundTag $$0) {
/* 27 */     CompoundTag $$1 = new CompoundTag();
/* 28 */     this.storage.forEach(($$1, $$2) -> $$0.put($$1, (Tag)$$2.copy()));
/* 29 */     $$0.put("contents", (Tag)$$1);
/* 30 */     return $$0;
/*    */   }
/*    */   
/*    */   public CompoundTag get(String $$0) {
/* 34 */     CompoundTag $$1 = this.storage.get($$0);
/* 35 */     return ($$1 != null) ? $$1 : new CompoundTag();
/*    */   }
/*    */   
/*    */   public void put(String $$0, CompoundTag $$1) {
/* 39 */     if ($$1.isEmpty()) {
/* 40 */       this.storage.remove($$0);
/*    */     } else {
/* 42 */       this.storage.put($$0, $$1);
/*    */     } 
/* 44 */     setDirty();
/*    */   }
/*    */   
/*    */   public Stream<ResourceLocation> getKeys(String $$0) {
/* 48 */     return this.storage.keySet().stream().map($$1 -> new ResourceLocation($$0, $$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\CommandStorage$Container.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */