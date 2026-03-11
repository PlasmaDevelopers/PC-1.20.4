/*    */ package com.mojang.realmsclient.util;
/*    */ 
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.levelgen.presets.WorldPreset;
/*    */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*    */ 
/*    */ public enum LevelType {
/*  9 */   DEFAULT(0, WorldPresets.NORMAL),
/* 10 */   FLAT(1, WorldPresets.FLAT),
/* 11 */   LARGE_BIOMES(2, WorldPresets.LARGE_BIOMES),
/* 12 */   AMPLIFIED(3, WorldPresets.AMPLIFIED);
/*    */   
/*    */   private final int index;
/*    */   
/*    */   private final Component name;
/*    */   
/*    */   LevelType(int $$0, ResourceKey<WorldPreset> $$1) {
/* 19 */     this.index = $$0;
/* 20 */     this.name = (Component)Component.translatable($$1.location().toLanguageKey("generator"));
/*    */   }
/*    */   
/*    */   public Component getName() {
/* 24 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getDtoIndex() {
/* 28 */     return this.index;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclien\\util\LevelType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */