/*    */ package net.minecraft.client.multiplayer;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.LayeredRegistryAccess;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public enum ClientRegistryLayer {
/*    */   private static final List<ClientRegistryLayer> VALUES;
/*    */   private static final RegistryAccess.Frozen STATIC_ACCESS;
/* 10 */   STATIC,
/* 11 */   REMOTE;
/*    */   
/*    */   static {
/* 14 */     VALUES = List.of(values());
/*    */     
/* 16 */     STATIC_ACCESS = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
/*    */   }
/*    */   public static LayeredRegistryAccess<ClientRegistryLayer> createRegistryAccess() {
/* 19 */     return (new LayeredRegistryAccess(VALUES)).replaceFrom(STATIC, new RegistryAccess.Frozen[] { STATIC_ACCESS });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientRegistryLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */