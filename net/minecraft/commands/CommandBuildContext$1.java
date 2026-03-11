/*    */ package net.minecraft.commands;
/*    */ 
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.flag.FeatureFlagSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements CommandBuildContext
/*    */ {
/*    */   public <T> HolderLookup<T> holderLookup(ResourceKey<? extends Registry<T>> $$0) {
/* 25 */     return access.lookupOrThrow($$0).filterFeatures(enabledFeatures);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\CommandBuildContext$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */