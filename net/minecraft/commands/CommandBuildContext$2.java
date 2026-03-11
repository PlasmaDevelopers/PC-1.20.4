/*    */ package net.minecraft.commands;
/*    */ 
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.RegistryAccess;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements CommandBuildContext.Configurable
/*    */ {
/* 32 */   CommandBuildContext.MissingTagAccessPolicy missingTagAccessPolicy = CommandBuildContext.MissingTagAccessPolicy.FAIL;
/*    */ 
/*    */   
/*    */   public void missingTagAccessPolicy(CommandBuildContext.MissingTagAccessPolicy $$0) {
/* 36 */     this.missingTagAccessPolicy = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> HolderLookup<T> holderLookup(ResourceKey<? extends Registry<T>> $$0) {
/* 41 */     Registry<T> $$1 = registryAccess.registryOrThrow($$0);
/* 42 */     final HolderLookup.RegistryLookup<T> originalLookup = $$1.asLookup();
/* 43 */     final HolderLookup.RegistryLookup<T> originalTagAddingLookup = $$1.asTagAddingLookup();
/* 44 */     HolderLookup.RegistryLookup.Delegate<T> delegate = new HolderLookup.RegistryLookup.Delegate<T>()
/*    */       {
/*    */         protected HolderLookup.RegistryLookup<T> parent() {
/* 47 */           switch (CommandBuildContext.null.$SwitchMap$net$minecraft$commands$CommandBuildContext$MissingTagAccessPolicy[CommandBuildContext.null.this.missingTagAccessPolicy.ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: break; }  return 
/*    */             
/* 49 */             originalTagAddingLookup;
/*    */         }
/*    */       };
/*    */     
/* 53 */     return delegate.filterFeatures(enabledFeatures);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\CommandBuildContext$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */