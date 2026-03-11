/*    */ package net.minecraft.commands;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.flag.FeatureFlagSet;
/*    */ 
/*    */ public interface CommandBuildContext {
/*    */   <T> HolderLookup<T> holderLookup(ResourceKey<? extends Registry<T>> paramResourceKey);
/*    */   
/*    */   public enum MissingTagAccessPolicy {
/* 11 */     CREATE_NEW,
/* 12 */     FAIL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static CommandBuildContext simple(final HolderLookup.Provider access, final FeatureFlagSet enabledFeatures) {
/* 22 */     return new CommandBuildContext()
/*    */       {
/*    */         public <T> HolderLookup<T> holderLookup(ResourceKey<? extends Registry<T>> $$0) {
/* 25 */           return access.lookupOrThrow($$0).filterFeatures(enabledFeatures);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static Configurable configurable(final RegistryAccess registryAccess, final FeatureFlagSet enabledFeatures) {
/* 31 */     return new Configurable() {
/* 32 */         CommandBuildContext.MissingTagAccessPolicy missingTagAccessPolicy = CommandBuildContext.MissingTagAccessPolicy.FAIL;
/*    */ 
/*    */         
/*    */         public void missingTagAccessPolicy(CommandBuildContext.MissingTagAccessPolicy $$0) {
/* 36 */           this.missingTagAccessPolicy = $$0;
/*    */         }
/*    */ 
/*    */         
/*    */         public <T> HolderLookup<T> holderLookup(ResourceKey<? extends Registry<T>> $$0) {
/* 41 */           Registry<T> $$1 = registryAccess.registryOrThrow($$0);
/* 42 */           final HolderLookup.RegistryLookup<T> originalLookup = $$1.asLookup();
/* 43 */           final HolderLookup.RegistryLookup<T> originalTagAddingLookup = $$1.asTagAddingLookup();
/* 44 */           HolderLookup.RegistryLookup.Delegate<T> delegate = new HolderLookup.RegistryLookup.Delegate<T>()
/*    */             {
/*    */               protected HolderLookup.RegistryLookup<T> parent()
/*    */               {
/*    */                 // Byte code:
/*    */                 //   0: getstatic net/minecraft/commands/CommandBuildContext$3.$SwitchMap$net$minecraft$commands$CommandBuildContext$MissingTagAccessPolicy : [I
/*    */                 //   3: aload_0
/*    */                 //   4: getfield this$0 : Lnet/minecraft/commands/CommandBuildContext$2;
/*    */                 //   7: getfield missingTagAccessPolicy : Lnet/minecraft/commands/CommandBuildContext$MissingTagAccessPolicy;
/*    */                 //   10: invokevirtual ordinal : ()I
/*    */                 //   13: iaload
/*    */                 //   14: lookupswitch default -> 40, 1 -> 48, 2 -> 55
/*    */                 //   40: new java/lang/IncompatibleClassChangeError
/*    */                 //   43: dup
/*    */                 //   44: invokespecial <init> : ()V
/*    */                 //   47: athrow
/*    */                 //   48: aload_0
/*    */                 //   49: getfield val$originalLookup : Lnet/minecraft/core/HolderLookup$RegistryLookup;
/*    */                 //   52: goto -> 59
/*    */                 //   55: aload_0
/*    */                 //   56: getfield val$originalTagAddingLookup : Lnet/minecraft/core/HolderLookup$RegistryLookup;
/*    */                 //   59: areturn
/*    */                 // Line number table:
/*    */                 //   Java source line number -> byte code offset
/*    */                 //   #47	-> 0
/*    */                 //   #48	-> 48
/*    */                 //   #49	-> 55
/*    */                 //   #47	-> 59
/*    */                 // Local variable table:
/*    */                 //   start	length	slot	name	descriptor
/*    */                 //   0	60	0	this	Lnet/minecraft/commands/CommandBuildContext$2$1;
/*    */               }
/*    */             };
/*    */ 
/*    */           
/* 53 */           return delegate.filterFeatures(enabledFeatures);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public static interface Configurable extends CommandBuildContext {
/*    */     void missingTagAccessPolicy(CommandBuildContext.MissingTagAccessPolicy param1MissingTagAccessPolicy);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\CommandBuildContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */