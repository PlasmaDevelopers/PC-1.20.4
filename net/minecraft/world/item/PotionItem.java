/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ public class PotionItem extends Item {
/*     */   private static final int DRINK_DURATION = 32;
/*     */   
/*     */   public PotionItem(Item.Properties $$0) {
/*  36 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getDefaultInstance() {
/*  41 */     return PotionUtils.setPotion(super.getDefaultInstance(), Potions.WATER);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack finishUsingItem(ItemStack $$0, Level $$1, LivingEntity $$2) {
/*  46 */     Player $$3 = ($$2 instanceof Player) ? (Player)$$2 : null;
/*     */     
/*  48 */     if ($$3 instanceof ServerPlayer) {
/*  49 */       CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)$$3, $$0);
/*     */     }
/*  51 */     if (!$$1.isClientSide) {
/*  52 */       List<MobEffectInstance> $$4 = PotionUtils.getMobEffects($$0);
/*  53 */       for (MobEffectInstance $$5 : $$4) {
/*  54 */         if ($$5.getEffect().isInstantenous()) {
/*  55 */           $$5.getEffect().applyInstantenousEffect((Entity)$$3, (Entity)$$3, $$2, $$5.getAmplifier(), 1.0D); continue;
/*     */         } 
/*  57 */         $$2.addEffect(new MobEffectInstance($$5));
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     if ($$3 != null) {
/*  62 */       $$3.awardStat(Stats.ITEM_USED.get(this));
/*  63 */       if (!($$3.getAbilities()).instabuild) {
/*  64 */         $$0.shrink(1);
/*     */       }
/*     */     } 
/*     */     
/*  68 */     if ($$3 == null || !($$3.getAbilities()).instabuild) {
/*  69 */       if ($$0.isEmpty())
/*  70 */         return new ItemStack(Items.GLASS_BOTTLE); 
/*  71 */       if ($$3 != null) {
/*  72 */         $$3.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
/*     */       }
/*     */     } 
/*  75 */     $$2.gameEvent(GameEvent.DRINK);
/*     */     
/*  77 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  82 */     Level $$1 = $$0.getLevel();
/*  83 */     BlockPos $$2 = $$0.getClickedPos();
/*  84 */     Player $$3 = $$0.getPlayer();
/*  85 */     ItemStack $$4 = $$0.getItemInHand();
/*     */     
/*  87 */     BlockState $$5 = $$1.getBlockState($$2);
/*  88 */     if ($$0.getClickedFace() != Direction.DOWN && $$5.is(BlockTags.CONVERTABLE_TO_MUD) && PotionUtils.getPotion($$4) == Potions.WATER) {
/*  89 */       $$1.playSound(null, $$2, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */       
/*  91 */       $$3.setItemInHand($$0.getHand(), ItemUtils.createFilledResult($$4, $$3, new ItemStack(Items.GLASS_BOTTLE)));
/*  92 */       $$3.awardStat(Stats.ITEM_USED.get($$4.getItem()));
/*     */       
/*  94 */       if (!$$1.isClientSide) {
/*  95 */         ServerLevel $$6 = (ServerLevel)$$1;
/*  96 */         for (int $$7 = 0; $$7 < 5; $$7++) {
/*  97 */           $$6.sendParticles((ParticleOptions)ParticleTypes.SPLASH, $$2.getX() + $$1.random.nextDouble(), ($$2.getY() + 1), $$2.getZ() + $$1.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
/*     */         }
/*     */       } 
/*     */       
/* 101 */       $$1.playSound(null, $$2, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 102 */       $$1.gameEvent(null, GameEvent.FLUID_PLACE, $$2);
/*     */       
/* 104 */       $$1.setBlockAndUpdate($$2, Blocks.MUD.defaultBlockState());
/* 105 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */     
/* 108 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUseDuration(ItemStack $$0) {
/* 113 */     return 32;
/*     */   }
/*     */ 
/*     */   
/*     */   public UseAnim getUseAnimation(ItemStack $$0) {
/* 118 */     return UseAnim.DRINK;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 123 */     return ItemUtils.startUsingInstantly($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionId(ItemStack $$0) {
/* 128 */     return PotionUtils.getPotion($$0).getName(getDescriptionId() + ".effect.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 133 */     PotionUtils.addPotionTooltip($$0, $$2, 1.0F, ($$1 == null) ? 20.0F : $$1.tickRateManager().tickrate());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\PotionItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */