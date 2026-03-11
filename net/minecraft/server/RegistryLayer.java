/*    */ package net.minecraft.server;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.LayeredRegistryAccess;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public enum RegistryLayer {
/*    */   private static final List<RegistryLayer> VALUES;
/*    */   private static final RegistryAccess.Frozen STATIC_ACCESS;
/* 10 */   STATIC,
/* 11 */   WORLDGEN,
/* 12 */   DIMENSIONS,
/* 13 */   RELOADABLE;
/*    */   
/*    */   static {
/* 16 */     VALUES = List.of(values());
/*    */     
/* 18 */     STATIC_ACCESS = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
/*    */   }
/*    */   public static LayeredRegistryAccess<RegistryLayer> createRegistryAccess() {
/* 21 */     return (new LayeredRegistryAccess(VALUES)).replaceFrom(STATIC, new RegistryAccess.Frozen[] { STATIC_ACCESS });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\RegistryLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */