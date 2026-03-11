/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class Region<T extends Stitcher.Entry>
/*     */ {
/*     */   private final int originX;
/*     */   private final int originY;
/*     */   private final int width;
/*     */   private final int height;
/*     */   @Nullable
/*     */   private List<Region<T>> subSlots;
/*     */   @Nullable
/*     */   private Stitcher.Holder<T> holder;
/*     */   
/*     */   public Region(int $$0, int $$1, int $$2, int $$3) {
/* 155 */     this.originX = $$0;
/* 156 */     this.originY = $$1;
/* 157 */     this.width = $$2;
/* 158 */     this.height = $$3;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 162 */     return this.originX;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 166 */     return this.originY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Stitcher.Holder<T> $$0) {
/* 171 */     if (this.holder != null) {
/* 172 */       return false;
/*     */     }
/*     */     
/* 175 */     int $$1 = $$0.width;
/* 176 */     int $$2 = $$0.height;
/*     */ 
/*     */     
/* 179 */     if ($$1 > this.width || $$2 > this.height) {
/* 180 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 184 */     if ($$1 == this.width && $$2 == this.height) {
/*     */       
/* 186 */       this.holder = $$0;
/* 187 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 191 */     if (this.subSlots == null) {
/* 192 */       this.subSlots = new ArrayList<>(1);
/*     */ 
/*     */       
/* 195 */       this.subSlots.add(new Region(this.originX, this.originY, $$1, $$2));
/*     */       
/* 197 */       int $$3 = this.width - $$1;
/* 198 */       int $$4 = this.height - $$2;
/*     */       
/* 200 */       if ($$4 > 0 && $$3 > 0) {
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
/* 215 */         int $$5 = Math.max(this.height, $$3);
/* 216 */         int $$6 = Math.max(this.width, $$4);
/* 217 */         if ($$5 >= $$6) {
/* 218 */           this.subSlots.add(new Region(this.originX, this.originY + $$2, $$1, $$4));
/* 219 */           this.subSlots.add(new Region(this.originX + $$1, this.originY, $$3, this.height));
/*     */         } else {
/* 221 */           this.subSlots.add(new Region(this.originX + $$1, this.originY, $$3, $$2));
/* 222 */           this.subSlots.add(new Region(this.originX, this.originY + $$2, this.width, $$4));
/*     */         } 
/* 224 */       } else if ($$3 == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 234 */         this.subSlots.add(new Region(this.originX, this.originY + $$2, $$1, $$4));
/* 235 */       } else if ($$4 == 0) {
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
/* 246 */         this.subSlots.add(new Region(this.originX + $$1, this.originY, $$3, $$2));
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     for (Region<T> $$7 : this.subSlots) {
/* 251 */       if ($$7.add($$0)) {
/* 252 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 256 */     return false;
/*     */   }
/*     */   
/*     */   public void walk(Stitcher.SpriteLoader<T> $$0) {
/* 260 */     if (this.holder != null) {
/* 261 */       $$0.load(this.holder.entry, getX(), getY());
/* 262 */     } else if (this.subSlots != null) {
/* 263 */       for (Region<T> $$1 : this.subSlots) {
/* 264 */         $$1.walk($$0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 271 */     return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + "}";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\Stitcher$Region.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */