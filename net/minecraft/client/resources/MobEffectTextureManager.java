/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.effect.MobEffect;
/*    */ 
/*    */ public class MobEffectTextureManager extends TextureAtlasHolder {
/*    */   public MobEffectTextureManager(TextureManager $$0) {
/* 11 */     super($$0, new ResourceLocation("textures/atlas/mob_effects.png"), new ResourceLocation("mob_effects"));
/*    */   }
/*    */   
/*    */   public TextureAtlasSprite get(MobEffect $$0) {
/* 15 */     return getSprite(BuiltInRegistries.MOB_EFFECT.getKey($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\MobEffectTextureManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */