/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.material.PushReaction;
/*    */ 
/*    */ public class Marker extends Entity {
/*    */   private static final String DATA_TAG = "data";
/* 12 */   private CompoundTag data = new CompoundTag();
/*    */   
/*    */   public Marker(EntityType<?> $$0, Level $$1) {
/* 15 */     super($$0, $$1);
/* 16 */     this.noPhysics = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick() {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void defineSynchedData() {}
/*    */ 
/*    */   
/*    */   protected void readAdditionalSaveData(CompoundTag $$0) {
/* 29 */     this.data = $$0.getCompound("data");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addAdditionalSaveData(CompoundTag $$0) {
/* 34 */     $$0.put("data", (Tag)this.data.copy());
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 39 */     throw new IllegalStateException("Markers should never be sent");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canAddPassenger(Entity $$0) {
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean couldAcceptPassenger() {
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addPassenger(Entity $$0) {
/* 54 */     throw new IllegalStateException("Should never addPassenger without checking couldAcceptPassenger()");
/*    */   }
/*    */ 
/*    */   
/*    */   public PushReaction getPistonPushReaction() {
/* 59 */     return PushReaction.IGNORE;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isIgnoringBlockTriggers() {
/* 64 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\Marker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */