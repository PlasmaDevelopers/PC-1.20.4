/*     */ package net.minecraft.client.renderer.item;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements ClampedItemPropertyFunction
/*     */ {
/*     */   private double rotation;
/*     */   private double rota;
/*     */   private long lastUpdateTick;
/*     */   
/*     */   public float unclampedCall(ItemStack $$0, @Nullable ClientLevel $$1, @Nullable LivingEntity $$2, int $$3) {
/* 120 */     Entity $$4 = ($$2 != null) ? (Entity)$$2 : $$0.getEntityRepresentation();
/*     */     
/* 122 */     if ($$4 == null) {
/* 123 */       return 0.0F;
/*     */     }
/*     */     
/* 126 */     if ($$1 == null && $$4.level() instanceof ClientLevel) {
/* 127 */       $$1 = (ClientLevel)$$4.level();
/*     */     }
/*     */     
/* 130 */     if ($$1 == null) {
/* 131 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 135 */     if ($$1.dimensionType().natural()) {
/* 136 */       double $$5 = $$1.getTimeOfDay(1.0F);
/*     */     } else {
/* 138 */       $$6 = Math.random();
/*     */     } 
/*     */     
/* 141 */     double $$6 = wobble((Level)$$1, $$6);
/*     */     
/* 143 */     return (float)$$6;
/*     */   }
/*     */   
/*     */   private double wobble(Level $$0, double $$1) {
/* 147 */     if ($$0.getGameTime() != this.lastUpdateTick) {
/* 148 */       this.lastUpdateTick = $$0.getGameTime();
/*     */       
/* 150 */       double $$2 = $$1 - this.rotation;
/* 151 */       $$2 = Mth.positiveModulo($$2 + 0.5D, 1.0D) - 0.5D;
/*     */       
/* 153 */       this.rota += $$2 * 0.1D;
/* 154 */       this.rota *= 0.9D;
/* 155 */       this.rotation = Mth.positiveModulo(this.rotation + this.rota, 1.0D);
/*     */     } 
/*     */     
/* 158 */     return this.rotation;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\item\ItemProperties$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */