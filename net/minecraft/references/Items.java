/*    */ package net.minecraft.references;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.Item;
/*    */ 
/*    */ public class Items {
/*  9 */   public static final ResourceKey<Item> PUMPKIN_SEEDS = createKey("pumpkin_seeds");
/* 10 */   public static final ResourceKey<Item> MELON_SEEDS = createKey("melon_seeds");
/*    */   
/*    */   private static ResourceKey<Item> createKey(String $$0) {
/* 13 */     return ResourceKey.create(Registries.ITEM, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\references\Items.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */