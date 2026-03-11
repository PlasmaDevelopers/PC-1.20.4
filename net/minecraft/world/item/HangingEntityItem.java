/*     */ package net.minecraft.world.item;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.decoration.GlowItemFrame;
/*     */ import net.minecraft.world.entity.decoration.HangingEntity;
/*     */ import net.minecraft.world.entity.decoration.Painting;
/*     */ import net.minecraft.world.entity.decoration.PaintingVariant;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class HangingEntityItem extends Item {
/*  25 */   private static final Component TOOLTIP_RANDOM_VARIANT = (Component)Component.translatable("painting.random").withStyle(ChatFormatting.GRAY);
/*     */   
/*     */   private final EntityType<? extends HangingEntity> type;
/*     */   
/*     */   public HangingEntityItem(EntityType<? extends HangingEntity> $$0, Item.Properties $$1) {
/*  30 */     super($$1);
/*  31 */     this.type = $$0;
/*     */   }
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*     */     GlowItemFrame glowItemFrame;
/*  36 */     BlockPos $$1 = $$0.getClickedPos();
/*  37 */     Direction $$2 = $$0.getClickedFace();
/*     */     
/*  39 */     BlockPos $$3 = $$1.relative($$2);
/*  40 */     Player $$4 = $$0.getPlayer();
/*  41 */     ItemStack $$5 = $$0.getItemInHand();
/*     */     
/*  43 */     if ($$4 != null && !mayPlace($$4, $$2, $$5, $$3)) {
/*  44 */       return InteractionResult.FAIL;
/*     */     }
/*     */     
/*  47 */     Level $$6 = $$0.getLevel();
/*     */     
/*  49 */     if (this.type == EntityType.PAINTING) {
/*  50 */       Optional<Painting> $$7 = Painting.create($$6, $$3, $$2);
/*  51 */       if ($$7.isEmpty()) {
/*  52 */         return InteractionResult.CONSUME;
/*     */       }
/*  54 */       HangingEntity $$8 = (HangingEntity)$$7.get();
/*  55 */     } else if (this.type == EntityType.ITEM_FRAME) {
/*  56 */       ItemFrame itemFrame = new ItemFrame($$6, $$3, $$2);
/*  57 */     } else if (this.type == EntityType.GLOW_ITEM_FRAME) {
/*  58 */       glowItemFrame = new GlowItemFrame($$6, $$3, $$2);
/*     */     } else {
/*  60 */       return InteractionResult.sidedSuccess($$6.isClientSide);
/*     */     } 
/*     */     
/*  63 */     CompoundTag $$12 = $$5.getTag();
/*  64 */     if ($$12 != null) {
/*  65 */       EntityType.updateCustomEntityTag($$6, $$4, (Entity)glowItemFrame, $$12);
/*     */     }
/*     */     
/*  68 */     if (glowItemFrame.survives()) {
/*  69 */       if (!$$6.isClientSide) {
/*  70 */         glowItemFrame.playPlacementSound();
/*  71 */         $$6.gameEvent((Entity)$$4, GameEvent.ENTITY_PLACE, glowItemFrame.position());
/*  72 */         $$6.addFreshEntity((Entity)glowItemFrame);
/*     */       } 
/*  74 */       $$5.shrink(1);
/*  75 */       return InteractionResult.sidedSuccess($$6.isClientSide);
/*     */     } 
/*     */     
/*  78 */     return InteractionResult.CONSUME;
/*     */   }
/*     */   
/*     */   protected boolean mayPlace(Player $$0, Direction $$1, ItemStack $$2, BlockPos $$3) {
/*  82 */     return (!$$1.getAxis().isVertical() && $$0.mayUseItemAt($$3, $$1, $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/*  87 */     super.appendHoverText($$0, $$1, $$2, $$3);
/*  88 */     if (this.type == EntityType.PAINTING) {
/*  89 */       CompoundTag $$4 = $$0.getTag();
/*  90 */       if ($$4 != null && $$4.contains("EntityTag", 10)) {
/*  91 */         CompoundTag $$5 = $$4.getCompound("EntityTag");
/*  92 */         Painting.loadVariant($$5)
/*  93 */           .ifPresentOrElse($$1 -> {
/*     */               $$1.unwrapKey().ifPresent(());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               $$0.add(Component.translatable("painting.dimensions", new Object[] { Integer.valueOf(Mth.positiveCeilDiv(((PaintingVariant)$$1.value()).getWidth(), 16)), Integer.valueOf(Mth.positiveCeilDiv(((PaintingVariant)$$1.value()).getHeight(), 16)) }));
/*     */             }() -> $$0.add(TOOLTIP_RANDOM_VARIANT));
/* 104 */       } else if ($$3.isCreative()) {
/* 105 */         $$2.add(TOOLTIP_RANDOM_VARIANT);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\HangingEntityItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */