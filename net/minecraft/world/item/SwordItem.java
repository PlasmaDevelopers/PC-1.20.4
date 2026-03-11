/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMultimap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*    */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*    */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class SwordItem extends TieredItem implements Vanishable {
/*    */   private final float attackDamage;
/*    */   private final Multimap<Attribute, AttributeModifier> defaultModifiers;
/*    */   
/*    */   public SwordItem(Tier $$0, int $$1, float $$2, Item.Properties $$3) {
/* 23 */     super($$0, $$3);
/*    */     
/* 25 */     this.attackDamage = $$1 + $$0.getAttackDamageBonus();
/*    */ 
/*    */     
/* 28 */     ImmutableMultimap.Builder<Attribute, AttributeModifier> $$4 = ImmutableMultimap.builder();
/* 29 */     $$4.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
/* 30 */     $$4.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", $$2, AttributeModifier.Operation.ADDITION));
/* 31 */     this.defaultModifiers = (Multimap<Attribute, AttributeModifier>)$$4.build();
/*    */   }
/*    */   
/*    */   public float getDamage() {
/* 35 */     return this.attackDamage;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canAttackBlock(BlockState $$0, Level $$1, BlockPos $$2, Player $$3) {
/* 40 */     return !$$3.isCreative();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getDestroySpeed(ItemStack $$0, BlockState $$1) {
/* 46 */     if ($$1.is(Blocks.COBWEB)) {
/* 47 */       return 15.0F;
/*    */     }
/*    */     
/* 50 */     return $$1.is(BlockTags.SWORD_EFFICIENT) ? 1.5F : 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hurtEnemy(ItemStack $$0, LivingEntity $$1, LivingEntity $$2) {
/* 55 */     $$0.hurtAndBreak(1, $$2, $$0 -> $$0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mineBlock(ItemStack $$0, Level $$1, BlockState $$2, BlockPos $$3, LivingEntity $$4) {
/* 62 */     if ($$2.getDestroySpeed((BlockGetter)$$1, $$3) != 0.0F) {
/* 63 */       $$0.hurtAndBreak(2, $$4, $$0 -> $$0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
/*    */     }
/* 65 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCorrectToolForDrops(BlockState $$0) {
/* 70 */     return $$0.is(Blocks.COBWEB);
/*    */   }
/*    */ 
/*    */   
/*    */   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot $$0) {
/* 75 */     if ($$0 == EquipmentSlot.MAINHAND) {
/* 76 */       return this.defaultModifiers;
/*    */     }
/* 78 */     return super.getDefaultAttributeModifiers($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SwordItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */