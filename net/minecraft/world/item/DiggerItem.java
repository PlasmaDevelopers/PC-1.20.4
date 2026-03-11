/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMultimap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*    */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*    */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class DiggerItem
/*    */   extends TieredItem implements Vanishable {
/*    */   private final TagKey<Block> blocks;
/*    */   protected final float speed;
/*    */   
/*    */   protected DiggerItem(float $$0, float $$1, Tier $$2, TagKey<Block> $$3, Item.Properties $$4) {
/* 24 */     super($$2, $$4);
/* 25 */     this.blocks = $$3;
/* 26 */     this.speed = $$2.getSpeed();
/* 27 */     this.attackDamageBaseline = $$0 + $$2.getAttackDamageBonus();
/*    */ 
/*    */     
/* 30 */     ImmutableMultimap.Builder<Attribute, AttributeModifier> $$5 = ImmutableMultimap.builder();
/* 31 */     $$5.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", this.attackDamageBaseline, AttributeModifier.Operation.ADDITION));
/* 32 */     $$5.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", $$1, AttributeModifier.Operation.ADDITION));
/* 33 */     this.defaultModifiers = (Multimap<Attribute, AttributeModifier>)$$5.build();
/*    */   }
/*    */   private final float attackDamageBaseline; private final Multimap<Attribute, AttributeModifier> defaultModifiers;
/*    */   
/*    */   public float getDestroySpeed(ItemStack $$0, BlockState $$1) {
/* 38 */     return $$1.is(this.blocks) ? this.speed : 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hurtEnemy(ItemStack $$0, LivingEntity $$1, LivingEntity $$2) {
/* 43 */     $$0.hurtAndBreak(2, $$2, $$0 -> $$0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mineBlock(ItemStack $$0, Level $$1, BlockState $$2, BlockPos $$3, LivingEntity $$4) {
/* 50 */     if (!$$1.isClientSide && $$2.getDestroySpeed((BlockGetter)$$1, $$3) != 0.0F) {
/* 51 */       $$0.hurtAndBreak(1, $$4, $$0 -> $$0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
/*    */     }
/* 53 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot $$0) {
/* 58 */     if ($$0 == EquipmentSlot.MAINHAND) {
/* 59 */       return this.defaultModifiers;
/*    */     }
/* 61 */     return super.getDefaultAttributeModifiers($$0);
/*    */   }
/*    */   
/*    */   public float getAttackDamage() {
/* 65 */     return this.attackDamageBaseline;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCorrectToolForDrops(BlockState $$0) {
/* 70 */     int $$1 = getTier().getLevel();
/*    */     
/* 72 */     if ($$1 < 3 && $$0.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
/* 73 */       return false;
/*    */     }
/*    */     
/* 76 */     if ($$1 < 2 && $$0.is(BlockTags.NEEDS_IRON_TOOL)) {
/* 77 */       return false;
/*    */     }
/*    */     
/* 80 */     if ($$1 < 1 && $$0.is(BlockTags.NEEDS_STONE_TOOL)) {
/* 81 */       return false;
/*    */     }
/*    */     
/* 84 */     return $$0.is(this.blocks);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\DiggerItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */