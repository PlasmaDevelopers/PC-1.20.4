/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Spawner
/*    */ {
/*    */   static void appendHoverText(ItemStack $$0, List<Component> $$1, String $$2) {
/* 23 */     Component $$3 = getSpawnEntityDisplayName($$0, $$2);
/*    */     
/* 25 */     if ($$3 != null) {
/* 26 */       $$1.add($$3);
/*    */     } else {
/* 28 */       $$1.add(CommonComponents.EMPTY);
/* 29 */       $$1.add(Component.translatable("block.minecraft.spawner.desc1").withStyle(ChatFormatting.GRAY));
/* 30 */       $$1.add(CommonComponents.space().append((Component)Component.translatable("block.minecraft.spawner.desc2").withStyle(ChatFormatting.BLUE)));
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   static Component getSpawnEntityDisplayName(ItemStack $$0, String $$1) {
/* 36 */     CompoundTag $$2 = BlockItem.getBlockEntityData($$0);
/*    */     
/* 38 */     if ($$2 != null) {
/* 39 */       ResourceLocation $$3 = getEntityKey($$2, $$1);
/* 40 */       if ($$3 != null) {
/* 41 */         return BuiltInRegistries.ENTITY_TYPE.getOptional($$3)
/* 42 */           .map($$0 -> Component.translatable($$0.getDescriptionId()).withStyle(ChatFormatting.GRAY))
/* 43 */           .orElse(null);
/*    */       }
/*    */     } 
/* 46 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static ResourceLocation getEntityKey(CompoundTag $$0, String $$1) {
/* 51 */     if ($$0.contains($$1, 10)) {
/* 52 */       String $$2 = $$0.getCompound($$1).getCompound("entity").getString("id");
/* 53 */       return ResourceLocation.tryParse($$2);
/*    */     } 
/* 55 */     return null;
/*    */   }
/*    */   
/*    */   void setEntityId(EntityType<?> paramEntityType, RandomSource paramRandomSource);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\Spawner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */