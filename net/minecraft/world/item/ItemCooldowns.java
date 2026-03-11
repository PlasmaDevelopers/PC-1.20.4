/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class ItemCooldowns
/*    */ {
/* 10 */   private final Map<Item, CooldownInstance> cooldowns = Maps.newHashMap();
/*    */   private int tickCount;
/*    */   
/*    */   public boolean isOnCooldown(Item $$0) {
/* 14 */     return (getCooldownPercent($$0, 0.0F) > 0.0F);
/*    */   }
/*    */   
/*    */   public float getCooldownPercent(Item $$0, float $$1) {
/* 18 */     CooldownInstance $$2 = this.cooldowns.get($$0);
/*    */     
/* 20 */     if ($$2 != null) {
/* 21 */       float $$3 = ($$2.endTime - $$2.startTime);
/* 22 */       float $$4 = $$2.endTime - this.tickCount + $$1;
/* 23 */       return Mth.clamp($$4 / $$3, 0.0F, 1.0F);
/*    */     } 
/*    */     
/* 26 */     return 0.0F;
/*    */   }
/*    */   
/*    */   public void tick() {
/* 30 */     this.tickCount++;
/*    */     
/* 32 */     if (!this.cooldowns.isEmpty()) {
/* 33 */       for (Iterator<Map.Entry<Item, CooldownInstance>> $$0 = this.cooldowns.entrySet().iterator(); $$0.hasNext(); ) {
/* 34 */         Map.Entry<Item, CooldownInstance> $$1 = $$0.next();
/* 35 */         if (((CooldownInstance)$$1.getValue()).endTime <= this.tickCount) {
/* 36 */           $$0.remove();
/* 37 */           onCooldownEnded($$1.getKey());
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void addCooldown(Item $$0, int $$1) {
/* 44 */     this.cooldowns.put($$0, new CooldownInstance(this.tickCount, this.tickCount + $$1));
/* 45 */     onCooldownStarted($$0, $$1);
/*    */   }
/*    */   
/*    */   public void removeCooldown(Item $$0) {
/* 49 */     this.cooldowns.remove($$0);
/* 50 */     onCooldownEnded($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCooldownStarted(Item $$0, int $$1) {}
/*    */   
/*    */   protected void onCooldownEnded(Item $$0) {}
/*    */   
/*    */   private static class CooldownInstance
/*    */   {
/*    */     final int startTime;
/*    */     final int endTime;
/*    */     
/*    */     CooldownInstance(int $$0, int $$1) {
/* 64 */       this.startTime = $$0;
/* 65 */       this.endTime = $$1;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ItemCooldowns.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */