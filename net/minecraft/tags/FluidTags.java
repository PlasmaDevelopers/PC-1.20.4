/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FluidTags
/*    */ {
/* 11 */   public static final TagKey<Fluid> WATER = create("water");
/* 12 */   public static final TagKey<Fluid> LAVA = create("lava");
/*    */   
/*    */   private static TagKey<Fluid> create(String $$0) {
/* 15 */     return TagKey.create(Registries.FLUID, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\FluidTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */