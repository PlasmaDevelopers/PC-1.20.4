/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class FireworkStarItem
/*    */   extends Item {
/*    */   public FireworkStarItem(Item.Properties $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 20 */     CompoundTag $$4 = $$0.getTagElement("Explosion");
/* 21 */     if ($$4 != null) {
/* 22 */       appendHoverText($$4, $$2);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void appendHoverText(CompoundTag $$0, List<Component> $$1) {
/* 27 */     FireworkRocketItem.Shape $$2 = FireworkRocketItem.Shape.byId($$0.getByte("Type"));
/* 28 */     $$1.add(Component.translatable("item.minecraft.firework_star.shape." + $$2.getName()).withStyle(ChatFormatting.GRAY));
/*    */ 
/*    */     
/* 31 */     int[] $$3 = $$0.getIntArray("Colors");
/* 32 */     if ($$3.length > 0) {
/* 33 */       $$1.add(appendColors(Component.empty().withStyle(ChatFormatting.GRAY), $$3));
/*    */     }
/*    */ 
/*    */     
/* 37 */     int[] $$4 = $$0.getIntArray("FadeColors");
/* 38 */     if ($$4.length > 0) {
/* 39 */       $$1.add(appendColors(Component.translatable("item.minecraft.firework_star.fade_to").append(CommonComponents.SPACE).withStyle(ChatFormatting.GRAY), $$4));
/*    */     }
/*    */ 
/*    */     
/* 43 */     if ($$0.getBoolean("Trail")) {
/* 44 */       $$1.add(Component.translatable("item.minecraft.firework_star.trail").withStyle(ChatFormatting.GRAY));
/*    */     }
/*    */ 
/*    */     
/* 48 */     if ($$0.getBoolean("Flicker")) {
/* 49 */       $$1.add(Component.translatable("item.minecraft.firework_star.flicker").withStyle(ChatFormatting.GRAY));
/*    */     }
/*    */   }
/*    */   
/*    */   private static Component appendColors(MutableComponent $$0, int[] $$1) {
/* 54 */     for (int $$2 = 0; $$2 < $$1.length; $$2++) {
/* 55 */       if ($$2 > 0) {
/* 56 */         $$0.append(", ");
/*    */       }
/* 58 */       $$0.append(getColorName($$1[$$2]));
/*    */     } 
/*    */     
/* 61 */     return (Component)$$0;
/*    */   }
/*    */   
/*    */   private static Component getColorName(int $$0) {
/* 65 */     DyeColor $$1 = DyeColor.byFireworkColor($$0);
/* 66 */     if ($$1 == null) {
/* 67 */       return (Component)Component.translatable("item.minecraft.firework_star.custom_color");
/*    */     }
/* 69 */     return (Component)Component.translatable("item.minecraft.firework_star." + $$1.getName());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\FireworkStarItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */