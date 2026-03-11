/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class StatsCounter {
/*  9 */   protected final Object2IntMap<Stat<?>> stats = Object2IntMaps.synchronize((Object2IntMap)new Object2IntOpenHashMap());
/*    */   
/*    */   public StatsCounter() {
/* 12 */     this.stats.defaultReturnValue(0);
/*    */   }
/*    */   
/*    */   public void increment(Player $$0, Stat<?> $$1, int $$2) {
/* 16 */     int $$3 = (int)Math.min(getValue($$1) + $$2, 2147483647L);
/* 17 */     setValue($$0, $$1, $$3);
/*    */   }
/*    */   
/*    */   public void setValue(Player $$0, Stat<?> $$1, int $$2) {
/* 21 */     this.stats.put($$1, $$2);
/*    */   }
/*    */   
/*    */   public <T> int getValue(StatType<T> $$0, T $$1) {
/* 25 */     return $$0.contains($$1) ? getValue($$0.get($$1)) : 0;
/*    */   }
/*    */   
/*    */   public int getValue(Stat<?> $$0) {
/* 29 */     return this.stats.getInt($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\stats\StatsCounter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */