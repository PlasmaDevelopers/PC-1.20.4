/*     */ package net.minecraft.client.renderer.texture;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class Stitcher<T extends Stitcher.Entry> {
/*     */   private static final Comparator<Holder<?>> HOLDER_COMPARATOR;
/*     */   private final int mipLevel;
/*     */   
/*     */   static {
/*  13 */     HOLDER_COMPARATOR = Comparator.comparing($$0 -> Integer.valueOf(-$$0.height)).thenComparing($$0 -> Integer.valueOf(-$$0.width)).thenComparing($$0 -> $$0.entry.name());
/*     */   }
/*     */   
/*  16 */   private final List<Holder<T>> texturesToBeStitched = new ArrayList<>();
/*  17 */   private final List<Region<T>> storage = new ArrayList<>();
/*     */   
/*     */   private int storageX;
/*     */   private int storageY;
/*     */   private final int maxWidth;
/*     */   private final int maxHeight;
/*     */   
/*     */   public Stitcher(int $$0, int $$1, int $$2) {
/*  25 */     this.mipLevel = $$2;
/*  26 */     this.maxWidth = $$0;
/*  27 */     this.maxHeight = $$1;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  31 */     return this.storageX;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/*  35 */     return this.storageY;
/*     */   }
/*     */   
/*     */   public void registerSprite(T $$0) {
/*  39 */     Holder<T> $$1 = new Holder<>($$0, this.mipLevel);
/*  40 */     this.texturesToBeStitched.add($$1);
/*     */   }
/*     */   
/*     */   public void stitch() {
/*  44 */     List<Holder<T>> $$0 = new ArrayList<>(this.texturesToBeStitched);
/*  45 */     $$0.sort(HOLDER_COMPARATOR);
/*     */     
/*  47 */     for (Holder<T> $$1 : $$0) {
/*  48 */       if (!addToStorage($$1)) {
/*  49 */         throw new StitcherException($$1.entry, (Collection)$$0.stream().map($$0 -> $$0.entry).collect(ImmutableList.toImmutableList()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void gatherSprites(SpriteLoader<T> $$0) {
/*  59 */     for (Region<T> $$1 : this.storage) {
/*  60 */       $$1.walk($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   static int smallestFittingMinTexel(int $$0, int $$1) {
/*  65 */     return ($$0 >> $$1) + ((($$0 & (1 << $$1) - 1) == 0) ? 0 : 1) << $$1;
/*     */   }
/*     */   
/*     */   private boolean addToStorage(Holder<T> $$0) {
/*  69 */     for (Region<T> $$1 : this.storage) {
/*  70 */       if ($$1.add($$0)) {
/*  71 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  75 */     return expand($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean expand(Holder<T> $$0) {
/*     */     boolean $$10;
/*     */     Region<T> $$12;
/*  87 */     int $$1 = Mth.smallestEncompassingPowerOfTwo(this.storageX);
/*  88 */     int $$2 = Mth.smallestEncompassingPowerOfTwo(this.storageY);
/*  89 */     int $$3 = Mth.smallestEncompassingPowerOfTwo(this.storageX + $$0.width);
/*  90 */     int $$4 = Mth.smallestEncompassingPowerOfTwo(this.storageY + $$0.height);
/*     */     
/*  92 */     boolean $$5 = ($$3 <= this.maxWidth);
/*  93 */     boolean $$6 = ($$4 <= this.maxHeight);
/*     */     
/*  95 */     if (!$$5 && !$$6) {
/*  96 */       return false;
/*     */     }
/*     */     
/*  99 */     boolean $$7 = ($$5 && $$1 != $$3);
/* 100 */     boolean $$8 = ($$6 && $$2 != $$4);
/*     */     
/* 102 */     if ($$7 ^ $$8) {
/* 103 */       boolean $$9 = $$7;
/*     */     } else {
/*     */       
/* 106 */       $$10 = ($$5 && $$1 <= $$2);
/*     */     } 
/*     */ 
/*     */     
/* 110 */     if ($$10) {
/*     */       
/* 112 */       if (this.storageY == 0) {
/* 113 */         this.storageY = $$4;
/*     */       }
/*     */       
/* 116 */       Region<T> $$11 = new Region<>(this.storageX, 0, $$3 - this.storageX, this.storageY);
/* 117 */       this.storageX = $$3;
/*     */     } else {
/*     */       
/* 120 */       $$12 = new Region<>(0, this.storageY, this.storageX, $$4 - this.storageY);
/* 121 */       this.storageY = $$4;
/*     */     } 
/*     */     
/* 124 */     $$12.add($$0);
/* 125 */     this.storage.add($$12);
/*     */     
/* 127 */     return true;
/*     */   }
/*     */   private static final class Holder<T extends Entry> extends Record { final T entry; final int width; final int height;
/* 130 */     public int height() { return this.height; } public int width() { return this.width; } public T entry() { return this.entry; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/texture/Stitcher$Holder;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #130	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/renderer/texture/Stitcher$Holder;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 130 */       //   0	8	0	this	Lnet/minecraft/client/renderer/texture/Stitcher$Holder<TT;>; } private Holder(T $$0, int $$1, int $$2) { this.entry = $$0; this.width = $$1; this.height = $$2; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/texture/Stitcher$Holder;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #130	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/texture/Stitcher$Holder;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/texture/Stitcher$Holder<TT;>; }
/*     */     public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/texture/Stitcher$Holder;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #130	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/texture/Stitcher$Holder;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 132 */       //   0	7	0	this	Lnet/minecraft/client/renderer/texture/Stitcher$Holder<TT;>; } public Holder(T $$0, int $$1) { this($$0, 
/*     */           
/* 134 */           Stitcher.smallestFittingMinTexel($$0.width(), $$1), 
/* 135 */           Stitcher.smallestFittingMinTexel($$0.height(), $$1)); }
/*     */      }
/*     */ 
/*     */   
/*     */   public static interface Entry { int width();
/*     */     
/*     */     int height();
/*     */     
/*     */     ResourceLocation name(); }
/*     */   
/*     */   public static class Region<T extends Entry> { private final int originX;
/*     */     private final int originY;
/*     */     private final int width;
/*     */     private final int height;
/*     */     @Nullable
/*     */     private List<Region<T>> subSlots;
/*     */     @Nullable
/*     */     private Stitcher.Holder<T> holder;
/*     */     
/*     */     public Region(int $$0, int $$1, int $$2, int $$3) {
/* 155 */       this.originX = $$0;
/* 156 */       this.originY = $$1;
/* 157 */       this.width = $$2;
/* 158 */       this.height = $$3;
/*     */     }
/*     */     
/*     */     public int getX() {
/* 162 */       return this.originX;
/*     */     }
/*     */     
/*     */     public int getY() {
/* 166 */       return this.originY;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(Stitcher.Holder<T> $$0) {
/* 171 */       if (this.holder != null) {
/* 172 */         return false;
/*     */       }
/*     */       
/* 175 */       int $$1 = $$0.width;
/* 176 */       int $$2 = $$0.height;
/*     */ 
/*     */       
/* 179 */       if ($$1 > this.width || $$2 > this.height) {
/* 180 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 184 */       if ($$1 == this.width && $$2 == this.height) {
/*     */         
/* 186 */         this.holder = $$0;
/* 187 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 191 */       if (this.subSlots == null) {
/* 192 */         this.subSlots = new ArrayList<>(1);
/*     */ 
/*     */         
/* 195 */         this.subSlots.add(new Region(this.originX, this.originY, $$1, $$2));
/*     */         
/* 197 */         int $$3 = this.width - $$1;
/* 198 */         int $$4 = this.height - $$2;
/*     */         
/* 200 */         if ($$4 > 0 && $$3 > 0) {
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
/* 215 */           int $$5 = Math.max(this.height, $$3);
/* 216 */           int $$6 = Math.max(this.width, $$4);
/* 217 */           if ($$5 >= $$6) {
/* 218 */             this.subSlots.add(new Region(this.originX, this.originY + $$2, $$1, $$4));
/* 219 */             this.subSlots.add(new Region(this.originX + $$1, this.originY, $$3, this.height));
/*     */           } else {
/* 221 */             this.subSlots.add(new Region(this.originX + $$1, this.originY, $$3, $$2));
/* 222 */             this.subSlots.add(new Region(this.originX, this.originY + $$2, this.width, $$4));
/*     */           } 
/* 224 */         } else if ($$3 == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 234 */           this.subSlots.add(new Region(this.originX, this.originY + $$2, $$1, $$4));
/* 235 */         } else if ($$4 == 0) {
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
/* 246 */           this.subSlots.add(new Region(this.originX + $$1, this.originY, $$3, $$2));
/*     */         } 
/*     */       } 
/*     */       
/* 250 */       for (Region<T> $$7 : this.subSlots) {
/* 251 */         if ($$7.add($$0)) {
/* 252 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 256 */       return false;
/*     */     }
/*     */     
/*     */     public void walk(Stitcher.SpriteLoader<T> $$0) {
/* 260 */       if (this.holder != null) {
/* 261 */         $$0.load(this.holder.entry, getX(), getY());
/* 262 */       } else if (this.subSlots != null) {
/* 263 */         for (Region<T> $$1 : this.subSlots) {
/* 264 */           $$1.walk($$0);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 271 */       return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + "}";
/*     */     } }
/*     */ 
/*     */   
/*     */   public static interface SpriteLoader<T extends Entry> {
/*     */     void load(T param1T, int param1Int1, int param1Int2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\Stitcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */