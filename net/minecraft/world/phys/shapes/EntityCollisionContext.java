/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ public class EntityCollisionContext
/*    */   implements CollisionContext
/*    */ {
/* 16 */   protected static final CollisionContext EMPTY = new EntityCollisionContext(false, -1.7976931348623157E308D, ItemStack.EMPTY, $$0 -> false, null)
/*    */     {
/*    */       public boolean isAbove(VoxelShape $$0, BlockPos $$1, boolean $$2) {
/* 19 */         return $$2;
/*    */       }
/*    */     };
/*    */   
/*    */   private final boolean descending;
/*    */   private final double entityBottom;
/*    */   private final ItemStack heldItem;
/*    */   private final Predicate<FluidState> canStandOnFluid;
/*    */   @Nullable
/*    */   private final Entity entity;
/*    */   
/*    */   protected EntityCollisionContext(boolean $$0, double $$1, ItemStack $$2, Predicate<FluidState> $$3, @Nullable Entity $$4) {
/* 31 */     this.descending = $$0;
/* 32 */     this.entityBottom = $$1;
/* 33 */     this.heldItem = $$2;
/* 34 */     this.canStandOnFluid = $$3;
/* 35 */     this.entity = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   protected EntityCollisionContext(Entity $$0) {
/* 41 */     this($$0
/* 42 */         .isDescending(), $$0
/* 43 */         .getY(), 
/* 44 */         ($$0 instanceof LivingEntity) ? ((LivingEntity)$$0).getMainHandItem() : ItemStack.EMPTY, 
/* 45 */         ($$0 instanceof LivingEntity) ? (LivingEntity)$$0::canStandOnFluid : ($$0 -> false), $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isHoldingItem(Item $$0) {
/* 52 */     return this.heldItem.is($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canStandOnFluid(FluidState $$0, FluidState $$1) {
/* 57 */     return (this.canStandOnFluid.test($$1) && !$$0.getType().isSame($$1.getType()));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDescending() {
/* 62 */     return this.descending;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAbove(VoxelShape $$0, BlockPos $$1, boolean $$2) {
/* 67 */     return (this.entityBottom > $$1.getY() + $$0.max(Direction.Axis.Y) - 9.999999747378752E-6D);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Entity getEntity() {
/* 72 */     return this.entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\EntityCollisionContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */