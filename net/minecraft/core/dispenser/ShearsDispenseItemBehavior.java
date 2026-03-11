/*    */ package net.minecraft.core.dispenser;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.entity.EntitySelector;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Shearable;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.BeehiveBlock;
/*    */ import net.minecraft.world.level.block.DispenserBlock;
/*    */ import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class ShearsDispenseItemBehavior extends OptionalDispenseItemBehavior {
/*    */   protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 24 */     ServerLevel $$2 = $$0.level();
/* 25 */     if (!$$2.isClientSide()) {
/* 26 */       BlockPos $$3 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/*    */       
/* 28 */       setSuccess((tryShearBeehive($$2, $$3) || tryShearLivingEntity($$2, $$3)));
/* 29 */       if (isSuccess() && $$1.hurt(1, $$2.getRandom(), null)) {
/* 30 */         $$1.setCount(0);
/*    */       }
/*    */     } 
/* 33 */     return $$1;
/*    */   }
/*    */   
/*    */   private static boolean tryShearBeehive(ServerLevel $$0, BlockPos $$1) {
/* 37 */     BlockState $$2 = $$0.getBlockState($$1);
/* 38 */     if ($$2.is(BlockTags.BEEHIVES, $$0 -> ($$0.hasProperty((Property)BeehiveBlock.HONEY_LEVEL) && $$0.getBlock() instanceof BeehiveBlock))) {
/* 39 */       int $$3 = ((Integer)$$2.getValue((Property)BeehiveBlock.HONEY_LEVEL)).intValue();
/*    */       
/* 41 */       if ($$3 >= 5) {
/* 42 */         $$0.playSound(null, $$1, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
/*    */         
/* 44 */         BeehiveBlock.dropHoneycomb((Level)$$0, $$1);
/* 45 */         ((BeehiveBlock)$$2.getBlock()).releaseBeesAndResetHoneyLevel((Level)$$0, $$2, $$1, null, BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED);
/* 46 */         $$0.gameEvent(null, GameEvent.SHEAR, $$1);
/* 47 */         return true;
/*    */       } 
/*    */     } 
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   private static boolean tryShearLivingEntity(ServerLevel $$0, BlockPos $$1) {
/* 54 */     List<LivingEntity> $$2 = $$0.getEntitiesOfClass(LivingEntity.class, new AABB($$1), EntitySelector.NO_SPECTATORS);
/* 55 */     for (LivingEntity $$3 : $$2) {
/* 56 */       if ($$3 instanceof Shearable) { Shearable $$4 = (Shearable)$$3;
/* 57 */         if ($$4.readyForShearing()) {
/* 58 */           $$4.shear(SoundSource.BLOCKS);
/* 59 */           $$0.gameEvent(null, GameEvent.SHEAR, $$1);
/* 60 */           return true;
/*    */         }  }
/*    */     
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\dispenser\ShearsDispenseItemBehavior.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */