/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BannerPattern;
/*    */ 
/*    */ public class BannerPatternItem
/*    */   extends Item {
/*    */   private final TagKey<BannerPattern> bannerPattern;
/*    */   
/*    */   public BannerPatternItem(TagKey<BannerPattern> $$0, Item.Properties $$1) {
/* 17 */     super($$1);
/* 18 */     this.bannerPattern = $$0;
/*    */   }
/*    */   
/*    */   public TagKey<BannerPattern> getBannerPattern() {
/* 22 */     return this.bannerPattern;
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 27 */     $$2.add(getDisplayName().withStyle(ChatFormatting.GRAY));
/*    */   }
/*    */   
/*    */   public MutableComponent getDisplayName() {
/* 31 */     return Component.translatable(getDescriptionId() + ".desc");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BannerPatternItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */