/*    */ package com.mojang.blaze3d.platform;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public class Lighting {
/*  8 */   private static final Vector3f DIFFUSE_LIGHT_0 = (new Vector3f(0.2F, 1.0F, -0.7F)).normalize();
/*  9 */   private static final Vector3f DIFFUSE_LIGHT_1 = (new Vector3f(-0.2F, 1.0F, 0.7F)).normalize();
/*    */   
/* 11 */   private static final Vector3f NETHER_DIFFUSE_LIGHT_0 = (new Vector3f(0.2F, 1.0F, -0.7F)).normalize();
/* 12 */   private static final Vector3f NETHER_DIFFUSE_LIGHT_1 = (new Vector3f(-0.2F, -1.0F, 0.7F)).normalize();
/*    */   
/* 14 */   private static final Vector3f INVENTORY_DIFFUSE_LIGHT_0 = (new Vector3f(0.2F, -1.0F, -1.0F)).normalize();
/* 15 */   private static final Vector3f INVENTORY_DIFFUSE_LIGHT_1 = (new Vector3f(-0.2F, -1.0F, 0.0F)).normalize();
/*    */   
/*    */   public static void setupNetherLevel(Matrix4f $$0) {
/* 18 */     RenderSystem.setupLevelDiffuseLighting(NETHER_DIFFUSE_LIGHT_0, NETHER_DIFFUSE_LIGHT_1, $$0);
/*    */   }
/*    */   
/*    */   public static void setupLevel(Matrix4f $$0) {
/* 22 */     RenderSystem.setupLevelDiffuseLighting(DIFFUSE_LIGHT_0, DIFFUSE_LIGHT_1, $$0);
/*    */   }
/*    */   
/*    */   public static void setupForFlatItems() {
/* 26 */     RenderSystem.setupGuiFlatDiffuseLighting(DIFFUSE_LIGHT_0, DIFFUSE_LIGHT_1);
/*    */   }
/*    */   
/*    */   public static void setupFor3DItems() {
/* 30 */     RenderSystem.setupGui3DDiffuseLighting(DIFFUSE_LIGHT_0, DIFFUSE_LIGHT_1);
/*    */   }
/*    */   
/*    */   public static void setupForEntityInInventory() {
/* 34 */     RenderSystem.setShaderLights(INVENTORY_DIFFUSE_LIGHT_0, INVENTORY_DIFFUSE_LIGHT_1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\Lighting.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */