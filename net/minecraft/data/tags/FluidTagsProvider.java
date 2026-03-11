/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ 
/*    */ public class FluidTagsProvider extends IntrinsicHolderTagsProvider<Fluid> {
/*    */   public FluidTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 14 */     super($$0, Registries.FLUID, $$1, $$0 -> $$0.builtInRegistryHolder().key());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 19 */     tag(FluidTags.WATER).add(new Fluid[] { (Fluid)Fluids.WATER, (Fluid)Fluids.FLOWING_WATER });
/* 20 */     tag(FluidTags.LAVA).add(new Fluid[] { (Fluid)Fluids.LAVA, (Fluid)Fluids.FLOWING_LAVA });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\FluidTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */