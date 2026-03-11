/*    */ package net.minecraft.world.item;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.MobSpawnType;
/*    */ import net.minecraft.world.entity.decoration.ArmorStand;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class ArmorStandItem extends Item {
/*    */   public ArmorStandItem(Item.Properties $$0) {
/* 24 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 29 */     Direction $$1 = $$0.getClickedFace();
/* 30 */     if ($$1 == Direction.DOWN) {
/* 31 */       return InteractionResult.FAIL;
/*    */     }
/*    */     
/* 34 */     Level $$2 = $$0.getLevel();
/* 35 */     BlockPlaceContext $$3 = new BlockPlaceContext($$0);
/* 36 */     BlockPos $$4 = $$3.getClickedPos();
/*    */     
/* 38 */     ItemStack $$5 = $$0.getItemInHand();
/* 39 */     Vec3 $$6 = Vec3.atBottomCenterOf((Vec3i)$$4);
/* 40 */     AABB $$7 = EntityType.ARMOR_STAND.getDimensions().makeBoundingBox($$6.x(), $$6.y(), $$6.z());
/*    */     
/* 42 */     if (!$$2.noCollision(null, $$7) || !$$2.getEntities(null, $$7).isEmpty()) {
/* 43 */       return InteractionResult.FAIL;
/*    */     }
/*    */     
/* 46 */     if ($$2 instanceof ServerLevel) { ServerLevel $$8 = (ServerLevel)$$2;
/* 47 */       Consumer<ArmorStand> $$9 = EntityType.createDefaultStackConfig($$8, $$5, $$0.getPlayer());
/* 48 */       ArmorStand $$10 = (ArmorStand)EntityType.ARMOR_STAND.create($$8, $$5.getTag(), $$9, $$4, MobSpawnType.SPAWN_EGG, true, true);
/*    */       
/* 50 */       if ($$10 == null) {
/* 51 */         return InteractionResult.FAIL;
/*    */       }
/*    */       
/* 54 */       float $$11 = Mth.floor((Mth.wrapDegrees($$0.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
/* 55 */       $$10.moveTo($$10.getX(), $$10.getY(), $$10.getZ(), $$11, 0.0F);
/*    */       
/* 57 */       $$8.addFreshEntityWithPassengers((Entity)$$10);
/*    */       
/* 59 */       $$2.playSound(null, $$10.getX(), $$10.getY(), $$10.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
/* 60 */       $$10.gameEvent(GameEvent.ENTITY_PLACE, (Entity)$$0.getPlayer()); }
/*    */ 
/*    */     
/* 63 */     $$5.shrink(1);
/* 64 */     return InteractionResult.sidedSuccess($$2.isClientSide);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ArmorStandItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */