/*    */ package net.minecraft.world.entity.boss;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityDimensions;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class EnderDragonPart
/*    */   extends Entity
/*    */ {
/*    */   public final EnderDragon parentMob;
/*    */   public final String name;
/*    */   private final EntityDimensions size;
/*    */   
/*    */   public EnderDragonPart(EnderDragon $$0, String $$1, float $$2, float $$3) {
/* 22 */     super($$0.getType(), $$0.level());
/* 23 */     this.size = EntityDimensions.scalable($$2, $$3);
/* 24 */     refreshDimensions();
/* 25 */     this.parentMob = $$0;
/* 26 */     this.name = $$1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void defineSynchedData() {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readAdditionalSaveData(CompoundTag $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void addAdditionalSaveData(CompoundTag $$0) {}
/*    */ 
/*    */   
/*    */   public boolean isPickable() {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ItemStack getPickResult() {
/* 49 */     return this.parentMob.getPickResult();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hurt(DamageSource $$0, float $$1) {
/* 54 */     if (isInvulnerableTo($$0)) {
/* 55 */       return false;
/*    */     }
/* 57 */     return this.parentMob.hurt(this, $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean is(Entity $$0) {
/* 62 */     return (this == $$0 || this.parentMob == $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 68 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityDimensions getDimensions(Pose $$0) {
/* 73 */     return this.size;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldBeSaved() {
/* 78 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\EnderDragonPart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */