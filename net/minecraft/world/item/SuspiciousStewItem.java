/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtOps;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.alchemy.PotionUtils;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.SuspiciousEffectHolder;
/*    */ 
/*    */ public class SuspiciousStewItem
/*    */   extends Item {
/*    */   public static final String EFFECTS_TAG = "effects";
/*    */   public static final int DEFAULT_DURATION = 160;
/*    */   
/*    */   public SuspiciousStewItem(Item.Properties $$0) {
/* 26 */     super($$0);
/*    */   }
/*    */   
/*    */   public static void saveMobEffects(ItemStack $$0, List<SuspiciousEffectHolder.EffectEntry> $$1) {
/* 30 */     CompoundTag $$2 = $$0.getOrCreateTag();
/* 31 */     SuspiciousEffectHolder.EffectEntry.LIST_CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$1).result().ifPresent($$1 -> $$0.put("effects", $$1));
/*    */   }
/*    */   
/*    */   public static void appendMobEffects(ItemStack $$0, List<SuspiciousEffectHolder.EffectEntry> $$1) {
/* 35 */     CompoundTag $$2 = $$0.getOrCreateTag();
/* 36 */     List<SuspiciousEffectHolder.EffectEntry> $$3 = new ArrayList<>();
/* 37 */     Objects.requireNonNull($$3); listPotionEffects($$0, $$3::add);
/* 38 */     $$3.addAll($$1);
/* 39 */     SuspiciousEffectHolder.EffectEntry.LIST_CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$3).result().ifPresent($$1 -> $$0.put("effects", $$1));
/*    */   }
/*    */   
/*    */   private static void listPotionEffects(ItemStack $$0, Consumer<SuspiciousEffectHolder.EffectEntry> $$1) {
/* 43 */     CompoundTag $$2 = $$0.getTag();
/* 44 */     if ($$2 != null && $$2.contains("effects", 9)) {
/* 45 */       SuspiciousEffectHolder.EffectEntry.LIST_CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$2.getList("effects", 10)).result().ifPresent($$1 -> $$1.forEach($$0));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 53 */     super.appendHoverText($$0, $$1, $$2, $$3);
/*    */     
/* 55 */     if ($$3.isCreative()) {
/* 56 */       List<MobEffectInstance> $$4 = new ArrayList<>();
/* 57 */       listPotionEffects($$0, $$1 -> $$0.add($$1.createEffectInstance()));
/* 58 */       PotionUtils.addPotionTooltip($$4, $$2, 1.0F, ($$1 == null) ? 20.0F : $$1.tickRateManager().tickrate());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack finishUsingItem(ItemStack $$0, Level $$1, LivingEntity $$2) {
/* 64 */     ItemStack $$3 = super.finishUsingItem($$0, $$1, $$2);
/*    */     
/* 66 */     listPotionEffects($$3, $$1 -> $$0.addEffect($$1.createEffectInstance()));
/* 67 */     if ($$2 instanceof Player && (((Player)$$2).getAbilities()).instabuild) {
/* 68 */       return $$3;
/*    */     }
/* 70 */     return new ItemStack(Items.BOWL);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SuspiciousStewItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */