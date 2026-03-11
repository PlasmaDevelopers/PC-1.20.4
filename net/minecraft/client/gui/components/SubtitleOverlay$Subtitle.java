/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Subtitle
/*     */ {
/*     */   private final Component text;
/*     */   private final float range;
/*     */   private long time;
/*     */   private Vec3 location;
/*     */   
/*     */   public Subtitle(Component $$0, float $$1, Vec3 $$2) {
/* 142 */     this.text = $$0;
/* 143 */     this.range = $$1;
/* 144 */     this.location = $$2;
/* 145 */     this.time = Util.getMillis();
/*     */   }
/*     */   
/*     */   public Component getText() {
/* 149 */     return this.text;
/*     */   }
/*     */   
/*     */   public long getTime() {
/* 153 */     return this.time;
/*     */   }
/*     */   
/*     */   public Vec3 getLocation() {
/* 157 */     return this.location;
/*     */   }
/*     */   
/*     */   public void refresh(Vec3 $$0) {
/* 161 */     this.location = $$0;
/* 162 */     this.time = Util.getMillis();
/*     */   }
/*     */   
/*     */   public boolean isAudibleFrom(Vec3 $$0) {
/* 166 */     return (Float.isInfinite(this.range) || $$0.closerThan((Position)this.location, this.range));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\SubtitleOverlay$Subtitle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */