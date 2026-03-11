/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class DefaultPlayerSkin
/*    */ {
/*  9 */   private static final PlayerSkin[] DEFAULT_SKINS = new PlayerSkin[] { 
/* 10 */       create("textures/entity/player/slim/alex.png", PlayerSkin.Model.SLIM), 
/* 11 */       create("textures/entity/player/slim/ari.png", PlayerSkin.Model.SLIM), 
/* 12 */       create("textures/entity/player/slim/efe.png", PlayerSkin.Model.SLIM), 
/* 13 */       create("textures/entity/player/slim/kai.png", PlayerSkin.Model.SLIM), 
/* 14 */       create("textures/entity/player/slim/makena.png", PlayerSkin.Model.SLIM), 
/* 15 */       create("textures/entity/player/slim/noor.png", PlayerSkin.Model.SLIM), 
/* 16 */       create("textures/entity/player/slim/steve.png", PlayerSkin.Model.SLIM), 
/* 17 */       create("textures/entity/player/slim/sunny.png", PlayerSkin.Model.SLIM), 
/* 18 */       create("textures/entity/player/slim/zuri.png", PlayerSkin.Model.SLIM), 
/*    */       
/* 20 */       create("textures/entity/player/wide/alex.png", PlayerSkin.Model.WIDE), 
/* 21 */       create("textures/entity/player/wide/ari.png", PlayerSkin.Model.WIDE), 
/* 22 */       create("textures/entity/player/wide/efe.png", PlayerSkin.Model.WIDE), 
/* 23 */       create("textures/entity/player/wide/kai.png", PlayerSkin.Model.WIDE), 
/* 24 */       create("textures/entity/player/wide/makena.png", PlayerSkin.Model.WIDE), 
/* 25 */       create("textures/entity/player/wide/noor.png", PlayerSkin.Model.WIDE), 
/* 26 */       create("textures/entity/player/wide/steve.png", PlayerSkin.Model.WIDE), 
/* 27 */       create("textures/entity/player/wide/sunny.png", PlayerSkin.Model.WIDE), 
/* 28 */       create("textures/entity/player/wide/zuri.png", PlayerSkin.Model.WIDE) };
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResourceLocation getDefaultTexture() {
/* 33 */     return DEFAULT_SKINS[6].texture();
/*    */   }
/*    */   
/*    */   public static PlayerSkin get(UUID $$0) {
/* 37 */     return DEFAULT_SKINS[Math.floorMod($$0.hashCode(), DEFAULT_SKINS.length)];
/*    */   }
/*    */   
/*    */   public static PlayerSkin get(GameProfile $$0) {
/* 41 */     return get($$0.getId());
/*    */   }
/*    */   
/*    */   private static PlayerSkin create(String $$0, PlayerSkin.Model $$1) {
/* 45 */     return new PlayerSkin(new ResourceLocation($$0), null, null, null, $$1, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\DefaultPlayerSkin.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */