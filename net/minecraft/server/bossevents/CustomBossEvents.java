/*    */ package net.minecraft.server.bossevents;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ 
/*    */ public class CustomBossEvents {
/* 14 */   private final Map<ResourceLocation, CustomBossEvent> events = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public CustomBossEvent get(ResourceLocation $$0) {
/* 21 */     return this.events.get($$0);
/*    */   }
/*    */   
/*    */   public CustomBossEvent create(ResourceLocation $$0, Component $$1) {
/* 25 */     CustomBossEvent $$2 = new CustomBossEvent($$0, $$1);
/* 26 */     this.events.put($$0, $$2);
/* 27 */     return $$2;
/*    */   }
/*    */   
/*    */   public void remove(CustomBossEvent $$0) {
/* 31 */     this.events.remove($$0.getTextId());
/*    */   }
/*    */   
/*    */   public Collection<ResourceLocation> getIds() {
/* 35 */     return this.events.keySet();
/*    */   }
/*    */   
/*    */   public Collection<CustomBossEvent> getEvents() {
/* 39 */     return this.events.values();
/*    */   }
/*    */   
/*    */   public CompoundTag save() {
/* 43 */     CompoundTag $$0 = new CompoundTag();
/*    */     
/* 45 */     for (CustomBossEvent $$1 : this.events.values()) {
/* 46 */       $$0.put($$1.getTextId().toString(), (Tag)$$1.save());
/*    */     }
/*    */     
/* 49 */     return $$0;
/*    */   }
/*    */   
/*    */   public void load(CompoundTag $$0) {
/* 53 */     for (String $$1 : $$0.getAllKeys()) {
/* 54 */       ResourceLocation $$2 = new ResourceLocation($$1);
/* 55 */       this.events.put($$2, CustomBossEvent.load($$0.getCompound($$1), $$2));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onPlayerConnect(ServerPlayer $$0) {
/* 60 */     for (CustomBossEvent $$1 : this.events.values()) {
/* 61 */       $$1.onPlayerConnect($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   public void onPlayerDisconnect(ServerPlayer $$0) {
/* 66 */     for (CustomBossEvent $$1 : this.events.values())
/* 67 */       $$1.onPlayerDisconnect($$0); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\bossevents\CustomBossEvents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */