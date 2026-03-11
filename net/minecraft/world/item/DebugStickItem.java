/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class DebugStickItem
/*     */   extends Item {
/*     */   public DebugStickItem(Item.Properties $$0) {
/*  25 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFoil(ItemStack $$0) {
/*  30 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAttackBlock(BlockState $$0, Level $$1, BlockPos $$2, Player $$3) {
/*  35 */     if (!$$1.isClientSide) {
/*  36 */       handleInteraction($$3, $$0, (LevelAccessor)$$1, $$2, false, $$3.getItemInHand(InteractionHand.MAIN_HAND));
/*     */     }
/*     */     
/*  39 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  44 */     Player $$1 = $$0.getPlayer();
/*  45 */     Level $$2 = $$0.getLevel();
/*     */     
/*  47 */     if (!$$2.isClientSide && $$1 != null) {
/*  48 */       BlockPos $$3 = $$0.getClickedPos();
/*  49 */       if (!handleInteraction($$1, $$2.getBlockState($$3), (LevelAccessor)$$2, $$3, true, $$0.getItemInHand())) {
/*  50 */         return InteractionResult.FAIL;
/*     */       }
/*     */     } 
/*     */     
/*  54 */     return InteractionResult.sidedSuccess($$2.isClientSide);
/*     */   }
/*     */   
/*     */   private boolean handleInteraction(Player $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3, boolean $$4, ItemStack $$5) {
/*  58 */     if (!$$0.canUseGameMasterBlocks()) {
/*  59 */       return false;
/*     */     }
/*     */     
/*  62 */     Block $$6 = $$1.getBlock();
/*  63 */     StateDefinition<Block, BlockState> $$7 = $$6.getStateDefinition();
/*  64 */     Collection<Property<?>> $$8 = $$7.getProperties();
/*     */     
/*  66 */     String $$9 = BuiltInRegistries.BLOCK.getKey($$6).toString();
/*  67 */     if ($$8.isEmpty()) {
/*  68 */       message($$0, (Component)Component.translatable(getDescriptionId() + ".empty", new Object[] { $$9 }));
/*  69 */       return false;
/*     */     } 
/*     */     
/*  72 */     CompoundTag $$10 = $$5.getOrCreateTagElement("DebugProperty");
/*  73 */     String $$11 = $$10.getString($$9);
/*     */     
/*  75 */     Property<?> $$12 = $$7.getProperty($$11);
/*  76 */     if ($$4) {
/*  77 */       if ($$12 == null) {
/*  78 */         $$12 = $$8.iterator().next();
/*     */       }
/*     */       
/*  81 */       BlockState $$13 = cycleState($$1, $$12, $$0.isSecondaryUseActive());
/*  82 */       $$2.setBlock($$3, $$13, 18);
/*  83 */       message($$0, (Component)Component.translatable(getDescriptionId() + ".update", new Object[] { $$12.getName(), getNameHelper($$13, $$12) }));
/*     */     } else {
/*  85 */       $$12 = getRelative((Iterable)$$8, $$12, $$0.isSecondaryUseActive());
/*     */       
/*  87 */       String $$14 = $$12.getName();
/*  88 */       $$10.putString($$9, $$14);
/*  89 */       message($$0, (Component)Component.translatable(getDescriptionId() + ".select", new Object[] { $$14, getNameHelper($$1, $$12) }));
/*     */     } 
/*  91 */     return true;
/*     */   }
/*     */   
/*     */   private static <T extends Comparable<T>> BlockState cycleState(BlockState $$0, Property<T> $$1, boolean $$2) {
/*  95 */     return (BlockState)$$0.setValue($$1, getRelative($$1.getPossibleValues(), $$0.getValue($$1), $$2));
/*     */   }
/*     */   
/*     */   private static <T> T getRelative(Iterable<T> $$0, @Nullable T $$1, boolean $$2) {
/*  99 */     return $$2 ? (T)Util.findPreviousInIterable($$0, $$1) : (T)Util.findNextInIterable($$0, $$1);
/*     */   }
/*     */   
/*     */   private static void message(Player $$0, Component $$1) {
/* 103 */     ((ServerPlayer)$$0).sendSystemMessage($$1, true);
/*     */   }
/*     */   
/*     */   private static <T extends Comparable<T>> String getNameHelper(BlockState $$0, Property<T> $$1) {
/* 107 */     return $$1.getName($$0.getValue($$1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\DebugStickItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */