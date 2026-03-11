/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntitySelector;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.entity.vehicle.Boat;
/*    */ import net.minecraft.world.entity.vehicle.ChestBoat;
/*    */ import net.minecraft.world.level.ClipContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class BoatItem extends Item {
/* 24 */   private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
/*    */   
/*    */   private final Boat.Type type;
/*    */   private final boolean hasChest;
/*    */   
/*    */   public BoatItem(boolean $$0, Boat.Type $$1, Item.Properties $$2) {
/* 30 */     super($$2);
/* 31 */     this.hasChest = $$0;
/* 32 */     this.type = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 37 */     ItemStack $$3 = $$1.getItemInHand($$2);
/*    */     
/* 39 */     BlockHitResult blockHitResult = getPlayerPOVHitResult($$0, $$1, ClipContext.Fluid.ANY);
/* 40 */     if (blockHitResult.getType() == HitResult.Type.MISS) {
/* 41 */       return InteractionResultHolder.pass($$3);
/*    */     }
/*    */ 
/*    */     
/* 45 */     Vec3 $$5 = $$1.getViewVector(1.0F);
/* 46 */     double $$6 = 5.0D;
/* 47 */     List<Entity> $$7 = $$0.getEntities((Entity)$$1, $$1.getBoundingBox().expandTowards($$5.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
/* 48 */     if (!$$7.isEmpty()) {
/* 49 */       Vec3 $$8 = $$1.getEyePosition();
/* 50 */       for (Entity $$9 : $$7) {
/* 51 */         AABB $$10 = $$9.getBoundingBox().inflate($$9.getPickRadius());
/* 52 */         if ($$10.contains($$8)) {
/* 53 */           return InteractionResultHolder.pass($$3);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     if (blockHitResult.getType() == HitResult.Type.BLOCK) {
/* 59 */       Boat $$11 = getBoat($$0, (HitResult)blockHitResult, $$3, $$1);
/* 60 */       $$11.setVariant(this.type);
/* 61 */       $$11.setYRot($$1.getYRot());
/* 62 */       if (!$$0.noCollision((Entity)$$11, $$11.getBoundingBox())) {
/* 63 */         return InteractionResultHolder.fail($$3);
/*    */       }
/* 65 */       if (!$$0.isClientSide) {
/* 66 */         $$0.addFreshEntity((Entity)$$11);
/* 67 */         $$0.gameEvent((Entity)$$1, GameEvent.ENTITY_PLACE, blockHitResult.getLocation());
/* 68 */         if (!($$1.getAbilities()).instabuild) {
/* 69 */           $$3.shrink(1);
/*    */         }
/*    */       } 
/* 72 */       $$1.awardStat(Stats.ITEM_USED.get(this));
/*    */       
/* 74 */       return InteractionResultHolder.sidedSuccess($$3, $$0.isClientSide());
/*    */     } 
/*    */     
/* 77 */     return InteractionResultHolder.pass($$3);
/*    */   }
/*    */   
/*    */   private Boat getBoat(Level $$0, HitResult $$1, ItemStack $$2, Player $$3) {
/* 81 */     Vec3 $$4 = $$1.getLocation();
/*    */     
/* 83 */     Boat $$5 = this.hasChest ? (Boat)new ChestBoat($$0, $$4.x, $$4.y, $$4.z) : new Boat($$0, $$4.x, $$4.y, $$4.z);
/*    */     
/* 85 */     if ($$0 instanceof ServerLevel) { ServerLevel $$6 = (ServerLevel)$$0;
/* 86 */       EntityType.createDefaultStackConfig($$6, $$2, $$3)
/* 87 */         .accept($$5); }
/*    */     
/* 89 */     return $$5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BoatItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */