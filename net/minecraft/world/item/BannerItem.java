/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.AbstractBannerBlock;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.entity.BannerPattern;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class BannerItem
/*    */   extends StandingAndWallBlockItem {
/*    */   private static final String PATTERN_PREFIX = "block.minecraft.banner.";
/*    */   
/*    */   public BannerItem(Block $$0, Block $$1, Item.Properties $$2) {
/* 24 */     super($$0, $$1, $$2, Direction.DOWN);
/*    */     
/* 26 */     Validate.isInstanceOf(AbstractBannerBlock.class, $$0);
/* 27 */     Validate.isInstanceOf(AbstractBannerBlock.class, $$1);
/*    */   }
/*    */   
/*    */   public static void appendHoverTextFromBannerBlockEntityTag(ItemStack $$0, List<Component> $$1) {
/* 31 */     CompoundTag $$2 = BlockItem.getBlockEntityData($$0);
/* 32 */     if ($$2 == null || !$$2.contains("Patterns")) {
/*    */       return;
/*    */     }
/*    */     
/* 36 */     ListTag $$3 = $$2.getList("Patterns", 10);
/* 37 */     for (int $$4 = 0; $$4 < $$3.size() && $$4 < 6; $$4++) {
/* 38 */       CompoundTag $$5 = $$3.getCompound($$4);
/* 39 */       DyeColor $$6 = DyeColor.byId($$5.getInt("Color"));
/* 40 */       Holder<BannerPattern> $$7 = BannerPattern.byHash($$5.getString("Pattern"));
/*    */       
/* 42 */       if ($$7 != null) {
/* 43 */         $$7.unwrapKey().map($$0 -> $$0.location().toShortLanguageKey()).ifPresent($$2 -> $$0.add(Component.translatable("block.minecraft.banner." + $$2 + "." + $$1.getName()).withStyle(ChatFormatting.GRAY)));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DyeColor getColor() {
/* 51 */     return ((AbstractBannerBlock)getBlock()).getColor();
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 56 */     appendHoverTextFromBannerBlockEntityTag($$0, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BannerItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */