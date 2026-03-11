/*     */ package net.minecraft.world.item;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.dispenser.BlockSource;
/*     */ import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
/*     */ import net.minecraft.core.dispenser.DispenseItemBehavior;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.BaseRailBlock;
/*     */ import net.minecraft.world.level.block.DispenserBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RailShape;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class MinecartItem extends Item {
/*  23 */   private static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = (DispenseItemBehavior)new DefaultDispenseItemBehavior() {
/*  24 */       private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
/*     */       
/*     */       public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/*     */         double $$16;
/*  28 */         Direction $$2 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/*  29 */         ServerLevel $$3 = $$0.level();
/*  30 */         Vec3 $$4 = $$0.center();
/*     */ 
/*     */ 
/*     */         
/*  34 */         double $$5 = $$4.x() + $$2.getStepX() * 1.125D;
/*  35 */         double $$6 = Math.floor($$4.y()) + $$2.getStepY();
/*  36 */         double $$7 = $$4.z() + $$2.getStepZ() * 1.125D;
/*     */         
/*  38 */         BlockPos $$8 = $$0.pos().relative($$2);
/*  39 */         BlockState $$9 = $$3.getBlockState($$8);
/*  40 */         RailShape $$10 = ($$9.getBlock() instanceof BaseRailBlock) ? (RailShape)$$9.getValue(((BaseRailBlock)$$9.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
/*     */ 
/*     */         
/*  43 */         if ($$9.is(BlockTags.RAILS)) {
/*  44 */           if ($$10.isAscending()) {
/*  45 */             double $$11 = 0.6D;
/*     */           } else {
/*  47 */             double $$12 = 0.1D;
/*     */           } 
/*  49 */         } else if ($$9.isAir() && $$3.getBlockState($$8.below()).is(BlockTags.RAILS)) {
/*  50 */           BlockState $$13 = $$3.getBlockState($$8.below());
/*  51 */           RailShape $$14 = ($$13.getBlock() instanceof BaseRailBlock) ? (RailShape)$$13.getValue(((BaseRailBlock)$$13.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
/*  52 */           if ($$2 == Direction.DOWN || !$$14.isAscending()) {
/*  53 */             double $$15 = -0.9D;
/*     */           } else {
/*  55 */             $$16 = -0.4D;
/*     */           } 
/*     */         } else {
/*  58 */           return this.defaultDispenseItemBehavior.dispense($$0, $$1);
/*     */         } 
/*     */         
/*  61 */         AbstractMinecart $$18 = AbstractMinecart.createMinecart($$3, $$5, $$6 + $$16, $$7, ((MinecartItem)$$1.getItem()).type, $$1, null);
/*  62 */         $$3.addFreshEntity((Entity)$$18);
/*     */         
/*  64 */         $$1.shrink(1);
/*  65 */         return $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       protected void playSound(BlockSource $$0) {
/*  70 */         $$0.level().levelEvent(1000, $$0.pos(), 0);
/*     */       }
/*     */     };
/*     */   
/*     */   final AbstractMinecart.Type type;
/*     */   
/*     */   public MinecartItem(AbstractMinecart.Type $$0, Item.Properties $$1) {
/*  77 */     super($$1);
/*  78 */     this.type = $$0;
/*  79 */     DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  84 */     Level $$1 = $$0.getLevel();
/*  85 */     BlockPos $$2 = $$0.getClickedPos();
/*     */     
/*  87 */     BlockState $$3 = $$1.getBlockState($$2);
/*  88 */     if (!$$3.is(BlockTags.RAILS)) {
/*  89 */       return InteractionResult.FAIL;
/*     */     }
/*     */     
/*  92 */     ItemStack $$4 = $$0.getItemInHand();
/*  93 */     if ($$1 instanceof ServerLevel) { ServerLevel $$5 = (ServerLevel)$$1;
/*  94 */       RailShape $$6 = ($$3.getBlock() instanceof BaseRailBlock) ? (RailShape)$$3.getValue(((BaseRailBlock)$$3.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
/*  95 */       double $$7 = 0.0D;
/*  96 */       if ($$6.isAscending()) {
/*  97 */         $$7 = 0.5D;
/*     */       }
/*  99 */       AbstractMinecart $$8 = AbstractMinecart.createMinecart($$5, $$2.getX() + 0.5D, $$2.getY() + 0.0625D + $$7, $$2.getZ() + 0.5D, this.type, $$4, $$0.getPlayer());
/* 100 */       $$5.addFreshEntity((Entity)$$8);
/* 101 */       $$5.gameEvent(GameEvent.ENTITY_PLACE, $$2, GameEvent.Context.of((Entity)$$0.getPlayer(), $$5.getBlockState($$2.below()))); }
/*     */     
/* 103 */     $$4.shrink(1);
/* 104 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\MinecartItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */