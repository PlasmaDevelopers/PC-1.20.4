/*     */ package net.minecraft.world.item;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.StructureTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.EyeOfEnder;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.EndPortalFrameBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ 
/*     */ public class EnderEyeItem extends Item {
/*     */   public EnderEyeItem(Item.Properties $$0) {
/*  31 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  36 */     Level $$1 = $$0.getLevel();
/*  37 */     BlockPos $$2 = $$0.getClickedPos();
/*     */     
/*  39 */     BlockState $$3 = $$1.getBlockState($$2);
/*     */     
/*  41 */     if (!$$3.is(Blocks.END_PORTAL_FRAME) || ((Boolean)$$3.getValue((Property)EndPortalFrameBlock.HAS_EYE)).booleanValue()) {
/*  42 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/*  45 */     if ($$1.isClientSide) {
/*  46 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/*  49 */     BlockState $$4 = (BlockState)$$3.setValue((Property)EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(true));
/*  50 */     Block.pushEntitiesUp($$3, $$4, (LevelAccessor)$$1, $$2);
/*  51 */     $$1.setBlock($$2, $$4, 2);
/*  52 */     $$1.updateNeighbourForOutputSignal($$2, Blocks.END_PORTAL_FRAME);
/*  53 */     $$0.getItemInHand().shrink(1);
/*     */     
/*  55 */     $$1.levelEvent(1503, $$2, 0);
/*     */ 
/*     */     
/*  58 */     BlockPattern.BlockPatternMatch $$5 = EndPortalFrameBlock.getOrCreatePortalShape().find((LevelReader)$$1, $$2);
/*  59 */     if ($$5 != null) {
/*  60 */       BlockPos $$6 = $$5.getFrontTopLeft().offset(-3, 0, -3);
/*  61 */       for (int $$7 = 0; $$7 < 3; $$7++) {
/*  62 */         for (int $$8 = 0; $$8 < 3; $$8++) {
/*  63 */           $$1.setBlock($$6.offset($$7, 0, $$8), Blocks.END_PORTAL.defaultBlockState(), 2);
/*     */         }
/*     */       } 
/*  66 */       $$1.globalLevelEvent(1038, $$6.offset(1, 0, 1), 0);
/*     */     } 
/*     */     
/*  69 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/*  74 */     ItemStack $$3 = $$1.getItemInHand($$2);
/*  75 */     BlockHitResult $$4 = getPlayerPOVHitResult($$0, $$1, ClipContext.Fluid.NONE);
/*  76 */     if ($$4.getType() == HitResult.Type.BLOCK && 
/*  77 */       $$0.getBlockState($$4.getBlockPos()).is(Blocks.END_PORTAL_FRAME)) {
/*  78 */       return InteractionResultHolder.pass($$3);
/*     */     }
/*     */ 
/*     */     
/*  82 */     $$1.startUsingItem($$2);
/*  83 */     if ($$0 instanceof ServerLevel) { ServerLevel $$5 = (ServerLevel)$$0;
/*  84 */       BlockPos $$6 = $$5.findNearestMapStructure(StructureTags.EYE_OF_ENDER_LOCATED, $$1.blockPosition(), 100, false);
/*  85 */       if ($$6 != null) {
/*  86 */         EyeOfEnder $$7 = new EyeOfEnder($$0, $$1.getX(), $$1.getY(0.5D), $$1.getZ());
/*  87 */         $$7.setItem($$3);
/*  88 */         $$7.signalTo($$6);
/*  89 */         $$0.gameEvent(GameEvent.PROJECTILE_SHOOT, $$7.position(), GameEvent.Context.of((Entity)$$1));
/*  90 */         $$0.addFreshEntity((Entity)$$7);
/*     */         
/*  92 */         if ($$1 instanceof ServerPlayer) {
/*  93 */           CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)$$1, $$6);
/*     */         }
/*     */         
/*  96 */         $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / ($$0.getRandom().nextFloat() * 0.4F + 0.8F));
/*  97 */         $$0.levelEvent(null, 1003, $$1.blockPosition(), 0);
/*  98 */         if (!($$1.getAbilities()).instabuild) {
/*  99 */           $$3.shrink(1);
/*     */         }
/* 101 */         $$1.awardStat(Stats.ITEM_USED.get(this));
/* 102 */         $$1.swing($$2, true);
/* 103 */         return InteractionResultHolder.success($$3);
/*     */       }  }
/*     */     
/* 106 */     return InteractionResultHolder.consume($$3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\EnderEyeItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */