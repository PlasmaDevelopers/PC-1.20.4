/*    */ package net.minecraft.references;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class Blocks {
/*  9 */   public static final ResourceKey<Block> PUMPKIN = createKey("pumpkin");
/* 10 */   public static final ResourceKey<Block> PUMPKIN_STEM = createKey("pumpkin_stem");
/* 11 */   public static final ResourceKey<Block> ATTACHED_PUMPKIN_STEM = createKey("attached_pumpkin_stem");
/* 12 */   public static final ResourceKey<Block> MELON = createKey("melon");
/* 13 */   public static final ResourceKey<Block> MELON_STEM = createKey("melon_stem");
/* 14 */   public static final ResourceKey<Block> ATTACHED_MELON_STEM = createKey("attached_melon_stem");
/*    */   
/*    */   private static ResourceKey<Block> createKey(String $$0) {
/* 17 */     return ResourceKey.create(Registries.BLOCK, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\references\Blocks.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */