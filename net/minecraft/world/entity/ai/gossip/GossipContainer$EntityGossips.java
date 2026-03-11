/*    */ package net.minecraft.world.entity.ai.gossip;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Stream;
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
/*    */ 
/*    */ class EntityGossips
/*    */ {
/* 52 */   final Object2IntMap<GossipType> entries = (Object2IntMap<GossipType>)new Object2IntOpenHashMap();
/*    */   
/*    */   public int weightedValue(Predicate<GossipType> $$0) {
/* 55 */     return this.entries.object2IntEntrySet()
/* 56 */       .stream()
/* 57 */       .filter($$1 -> $$0.test((GossipType)$$1.getKey()))
/* 58 */       .mapToInt($$0 -> $$0.getIntValue() * ((GossipType)$$0.getKey()).weight)
/* 59 */       .sum();
/*    */   }
/*    */   
/*    */   public Stream<GossipContainer.GossipEntry> unpack(UUID $$0) {
/* 63 */     return this.entries.object2IntEntrySet().stream().map($$1 -> new GossipContainer.GossipEntry($$0, (GossipType)$$1.getKey(), $$1.getIntValue()));
/*    */   }
/*    */   
/*    */   public void decay() {
/* 67 */     ObjectIterator<Object2IntMap.Entry<GossipType>> $$0 = this.entries.object2IntEntrySet().iterator();
/* 68 */     while ($$0.hasNext()) {
/* 69 */       Object2IntMap.Entry<GossipType> $$1 = (Object2IntMap.Entry<GossipType>)$$0.next();
/* 70 */       int $$2 = $$1.getIntValue() - ((GossipType)$$1.getKey()).decayPerDay;
/* 71 */       if ($$2 < 2) {
/* 72 */         $$0.remove(); continue;
/*    */       } 
/* 74 */       $$1.setValue($$2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 80 */     return this.entries.isEmpty();
/*    */   }
/*    */   
/*    */   public void makeSureValueIsntTooLowOrTooHigh(GossipType $$0) {
/* 84 */     int $$1 = this.entries.getInt($$0);
/* 85 */     if ($$1 > $$0.max) {
/* 86 */       this.entries.put($$0, $$0.max);
/*    */     }
/* 88 */     if ($$1 < 2) {
/* 89 */       remove($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   public void remove(GossipType $$0) {
/* 94 */     this.entries.removeInt($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\gossip\GossipContainer$EntityGossips.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */