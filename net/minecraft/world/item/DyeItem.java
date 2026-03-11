/*    */ package net.minecraft.world.item;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Sheep;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.SignText;
/*    */ 
/*    */ public class DyeItem extends Item implements SignApplicator {
/* 17 */   private static final Map<DyeColor, DyeItem> ITEM_BY_COLOR = Maps.newEnumMap(DyeColor.class);
/*    */   
/*    */   private final DyeColor dyeColor;
/*    */   
/*    */   public DyeItem(DyeColor $$0, Item.Properties $$1) {
/* 22 */     super($$1);
/* 23 */     this.dyeColor = $$0;
/* 24 */     ITEM_BY_COLOR.put($$0, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult interactLivingEntity(ItemStack $$0, Player $$1, LivingEntity $$2, InteractionHand $$3) {
/* 29 */     if ($$2 instanceof Sheep) { Sheep $$4 = (Sheep)$$2;
/* 30 */       if ($$4.isAlive() && !$$4.isSheared() && $$4.getColor() != this.dyeColor) {
/* 31 */         $$4.level().playSound($$1, (Entity)$$4, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
/* 32 */         if (!($$1.level()).isClientSide) {
/* 33 */           $$4.setColor(this.dyeColor);
/* 34 */           $$0.shrink(1);
/*    */         } 
/* 36 */         return InteractionResult.sidedSuccess(($$1.level()).isClientSide);
/*    */       }  }
/*    */     
/* 39 */     return InteractionResult.PASS;
/*    */   }
/*    */   
/*    */   public DyeColor getDyeColor() {
/* 43 */     return this.dyeColor;
/*    */   }
/*    */   
/*    */   public static DyeItem byColor(DyeColor $$0) {
/* 47 */     return ITEM_BY_COLOR.get($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean tryApplyToSign(Level $$0, SignBlockEntity $$1, boolean $$2, Player $$3) {
/* 52 */     if ($$1.updateText($$0 -> $$0.setColor(getDyeColor()), $$2)) {
/* 53 */       $$0.playSound(null, $$1.getBlockPos(), SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 54 */       return true;
/*    */     } 
/* 56 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\DyeItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */