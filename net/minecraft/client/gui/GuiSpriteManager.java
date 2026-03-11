/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.resources.TextureAtlasHolder;
/*    */ import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
/*    */ import net.minecraft.client.resources.metadata.gui.GuiMetadataSection;
/*    */ import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ 
/*    */ public class GuiSpriteManager
/*    */   extends TextureAtlasHolder {
/* 15 */   private static final Set<MetadataSectionSerializer<?>> METADATA_SECTIONS = (Set)Set.of(AnimationMetadataSection.SERIALIZER, GuiMetadataSection.TYPE);
/*    */   
/*    */   public GuiSpriteManager(TextureManager $$0) {
/* 18 */     super($$0, new ResourceLocation("textures/atlas/gui.png"), new ResourceLocation("gui"), METADATA_SECTIONS);
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite getSprite(ResourceLocation $$0) {
/* 23 */     return super.getSprite($$0);
/*    */   }
/*    */   
/*    */   public GuiSpriteScaling getSpriteScaling(TextureAtlasSprite $$0) {
/* 27 */     return getMetadata($$0).scaling();
/*    */   }
/*    */   
/*    */   private GuiMetadataSection getMetadata(TextureAtlasSprite $$0) {
/* 31 */     return $$0.contents().metadata().getSection((MetadataSectionSerializer)GuiMetadataSection.TYPE).orElse(GuiMetadataSection.DEFAULT);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\GuiSpriteManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */