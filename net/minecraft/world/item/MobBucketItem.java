/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.MobSpawnType;
/*    */ import net.minecraft.world.entity.animal.Bucketable;
/*    */ import net.minecraft.world.entity.animal.TropicalFish;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ 
/*    */ public class MobBucketItem
/*    */   extends BucketItem
/*    */ {
/*    */   private final EntityType<?> type;
/*    */   private final SoundEvent emptySound;
/*    */   
/*    */   public MobBucketItem(EntityType<?> $$0, Fluid $$1, SoundEvent $$2, Item.Properties $$3) {
/* 31 */     super($$1, $$3);
/* 32 */     this.type = $$0;
/* 33 */     this.emptySound = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void checkExtraContent(@Nullable Player $$0, Level $$1, ItemStack $$2, BlockPos $$3) {
/* 38 */     if ($$1 instanceof ServerLevel) {
/* 39 */       spawn((ServerLevel)$$1, $$2, $$3);
/* 40 */       $$1.gameEvent((Entity)$$0, GameEvent.ENTITY_PLACE, $$3);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void playEmptySound(@Nullable Player $$0, LevelAccessor $$1, BlockPos $$2) {
/* 46 */     $$1.playSound($$0, $$2, this.emptySound, SoundSource.NEUTRAL, 1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   private void spawn(ServerLevel $$0, ItemStack $$1, BlockPos $$2) {
/* 50 */     Entity $$3 = this.type.spawn($$0, $$1, null, $$2, MobSpawnType.BUCKET, true, false);
/*    */     
/* 52 */     if ($$3 instanceof Bucketable) { Bucketable $$4 = (Bucketable)$$3;
/* 53 */       $$4.loadFromBucketTag($$1.getOrCreateTag());
/* 54 */       $$4.setFromBucket(true); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 60 */     if (this.type == EntityType.TROPICAL_FISH) {
/* 61 */       CompoundTag $$4 = $$0.getTag();
/* 62 */       if ($$4 != null && $$4.contains("BucketVariantTag", 3)) {
/* 63 */         int $$5 = $$4.getInt("BucketVariantTag");
/* 64 */         ChatFormatting[] $$6 = { ChatFormatting.ITALIC, ChatFormatting.GRAY };
/* 65 */         String $$7 = "color.minecraft." + TropicalFish.getBaseColor($$5);
/* 66 */         String $$8 = "color.minecraft." + TropicalFish.getPatternColor($$5);
/*    */         
/* 68 */         for (int $$9 = 0; $$9 < TropicalFish.COMMON_VARIANTS.size(); $$9++) {
/* 69 */           if ($$5 == ((TropicalFish.Variant)TropicalFish.COMMON_VARIANTS.get($$9)).getPackedId()) {
/* 70 */             $$2.add(Component.translatable(TropicalFish.getPredefinedName($$9)).withStyle($$6));
/*    */             
/*    */             return;
/*    */           } 
/*    */         } 
/* 75 */         $$2.add(TropicalFish.getPattern($$5).displayName().plainCopy().withStyle($$6));
/* 76 */         MutableComponent $$10 = Component.translatable($$7);
/* 77 */         if (!$$7.equals($$8)) {
/* 78 */           $$10.append(", ").append((Component)Component.translatable($$8));
/*    */         }
/* 80 */         $$10.withStyle($$6);
/* 81 */         $$2.add($$10);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\MobBucketItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */