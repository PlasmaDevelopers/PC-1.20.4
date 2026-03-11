/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.tags.ItemTags;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.DispenserBlock;
/*    */ 
/*    */ public class ShieldItem
/*    */   extends Item
/*    */   implements Equipable
/*    */ {
/*    */   public static final int EFFECTIVE_BLOCK_DELAY = 5;
/*    */   public static final float MINIMUM_DURABILITY_DAMAGE = 3.0F;
/*    */   public static final String TAG_BASE_COLOR = "Base";
/*    */   
/*    */   public ShieldItem(Item.Properties $$0) {
/* 24 */     super($$0);
/*    */     
/* 26 */     DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescriptionId(ItemStack $$0) {
/* 31 */     if (BlockItem.getBlockEntityData($$0) != null) {
/* 32 */       return getDescriptionId() + "." + getDescriptionId();
/*    */     }
/* 34 */     return super.getDescriptionId($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 39 */     BannerItem.appendHoverTextFromBannerBlockEntityTag($$0, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public UseAnim getUseAnimation(ItemStack $$0) {
/* 44 */     return UseAnim.BLOCK;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getUseDuration(ItemStack $$0) {
/* 49 */     return 72000;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 54 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 55 */     $$1.startUsingItem($$2);
/* 56 */     return InteractionResultHolder.consume($$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidRepairItem(ItemStack $$0, ItemStack $$1) {
/* 61 */     return ($$1.is(ItemTags.PLANKS) || super.isValidRepairItem($$0, $$1));
/*    */   }
/*    */   
/*    */   public static DyeColor getColor(ItemStack $$0) {
/* 65 */     CompoundTag $$1 = BlockItem.getBlockEntityData($$0);
/* 66 */     return ($$1 != null) ? DyeColor.byId($$1.getInt("Base")) : DyeColor.WHITE;
/*    */   }
/*    */ 
/*    */   
/*    */   public EquipmentSlot getEquipmentSlot() {
/* 71 */     return EquipmentSlot.OFFHAND;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ShieldItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */