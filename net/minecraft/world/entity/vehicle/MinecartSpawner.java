/*    */ package net.minecraft.world.entity.vehicle;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.BaseSpawner;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class MinecartSpawner extends AbstractMinecart {
/* 15 */   private final BaseSpawner spawner = new BaseSpawner()
/*    */     {
/*    */       public void broadcastEvent(Level $$0, BlockPos $$1, int $$2) {
/* 18 */         $$0.broadcastEntityEvent(MinecartSpawner.this, (byte)$$2);
/*    */       }
/*    */     };
/*    */   
/*    */   private final Runnable ticker;
/*    */   
/*    */   public MinecartSpawner(EntityType<? extends MinecartSpawner> $$0, Level $$1) {
/* 25 */     super($$0, $$1);
/* 26 */     this.ticker = createTicker($$1);
/*    */   }
/*    */   
/*    */   public MinecartSpawner(Level $$0, double $$1, double $$2, double $$3) {
/* 30 */     super(EntityType.SPAWNER_MINECART, $$0, $$1, $$2, $$3);
/* 31 */     this.ticker = createTicker($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getDropItem() {
/* 36 */     return Items.MINECART;
/*    */   }
/*    */   
/*    */   private Runnable createTicker(Level $$0) {
/* 40 */     return ($$0 instanceof ServerLevel) ? (() -> this.spawner.serverTick((ServerLevel)$$0, blockPosition())) : (() -> this.spawner.clientTick($$0, blockPosition()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractMinecart.Type getMinecartType() {
/* 47 */     return AbstractMinecart.Type.SPAWNER;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getDefaultDisplayBlockState() {
/* 52 */     return Blocks.SPAWNER.defaultBlockState();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void readAdditionalSaveData(CompoundTag $$0) {
/* 57 */     super.readAdditionalSaveData($$0);
/* 58 */     this.spawner.load(level(), blockPosition(), $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addAdditionalSaveData(CompoundTag $$0) {
/* 63 */     super.addAdditionalSaveData($$0);
/* 64 */     this.spawner.save($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleEntityEvent(byte $$0) {
/* 69 */     this.spawner.onEventTriggered(level(), $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 74 */     super.tick();
/* 75 */     this.ticker.run();
/*    */   }
/*    */   
/*    */   public BaseSpawner getSpawner() {
/* 79 */     return this.spawner;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onlyOpCanSetNbt() {
/* 84 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\MinecartSpawner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */