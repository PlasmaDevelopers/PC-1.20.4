/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.client.renderer.texture.SpriteContents;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.Material;
/*     */ import net.minecraft.core.Direction;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ 
/*     */ public class ItemModelGenerator
/*     */ {
/*  18 */   public static final List<String> LAYERS = Lists.newArrayList((Object[])new String[] { "layer0", "layer1", "layer2", "layer3", "layer4" });
/*     */   
/*     */   private static final float MIN_Z = 7.5F;
/*     */   private static final float MAX_Z = 8.5F;
/*     */   
/*     */   public BlockModel generateBlockModel(Function<Material, TextureAtlasSprite> $$0, BlockModel $$1) {
/*  24 */     Map<String, Either<Material, String>> $$2 = Maps.newHashMap();
/*     */     
/*  26 */     List<BlockElement> $$3 = Lists.newArrayList();
/*  27 */     for (int $$4 = 0; $$4 < LAYERS.size(); $$4++) {
/*  28 */       String $$5 = LAYERS.get($$4);
/*  29 */       if (!$$1.hasTexture($$5)) {
/*     */         break;
/*     */       }
/*     */       
/*  33 */       Material $$6 = $$1.getMaterial($$5);
/*  34 */       $$2.put($$5, Either.left($$6));
/*     */       
/*  36 */       SpriteContents $$7 = ((TextureAtlasSprite)$$0.apply($$6)).contents();
/*  37 */       $$3.addAll(processFrames($$4, $$5, $$7));
/*     */     } 
/*     */     
/*  40 */     $$2.put("particle", $$1.hasTexture("particle") ? Either.left($$1.getMaterial("particle")) : $$2.get("layer0"));
/*     */     
/*  42 */     BlockModel $$8 = new BlockModel(null, $$3, $$2, Boolean.valueOf(false), $$1.getGuiLight(), $$1.getTransforms(), $$1.getOverrides());
/*  43 */     $$8.name = $$1.name;
/*  44 */     return $$8;
/*     */   }
/*     */   
/*     */   private List<BlockElement> processFrames(int $$0, String $$1, SpriteContents $$2) {
/*  48 */     Map<Direction, BlockElementFace> $$3 = Maps.newHashMap();
/*  49 */     $$3.put(Direction.SOUTH, new BlockElementFace(null, $$0, $$1, new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0)));
/*  50 */     $$3.put(Direction.NORTH, new BlockElementFace(null, $$0, $$1, new BlockFaceUV(new float[] { 16.0F, 0.0F, 0.0F, 16.0F }, 0)));
/*     */     
/*  52 */     List<BlockElement> $$4 = Lists.newArrayList();
/*  53 */     $$4.add(new BlockElement(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), $$3, null, true));
/*     */     
/*  55 */     $$4.addAll(createSideElements($$2, $$1, $$0));
/*     */     
/*  57 */     return $$4;
/*     */   }
/*     */   
/*     */   private List<BlockElement> createSideElements(SpriteContents $$0, String $$1, int $$2) {
/*  61 */     float $$3 = $$0.width();
/*  62 */     float $$4 = $$0.height();
/*  63 */     List<BlockElement> $$5 = Lists.newArrayList();
/*     */     
/*  65 */     for (Span $$6 : getSpans($$0)) {
/*  66 */       float $$7 = 0.0F;
/*  67 */       float $$8 = 0.0F;
/*  68 */       float $$9 = 0.0F;
/*  69 */       float $$10 = 0.0F;
/*  70 */       float $$11 = 0.0F;
/*  71 */       float $$12 = 0.0F;
/*  72 */       float $$13 = 0.0F;
/*  73 */       float $$14 = 0.0F;
/*  74 */       float $$15 = 16.0F / $$3;
/*  75 */       float $$16 = 16.0F / $$4;
/*     */       
/*  77 */       float $$17 = $$6.getMin();
/*  78 */       float $$18 = $$6.getMax();
/*  79 */       float $$19 = $$6.getAnchor();
/*     */       
/*  81 */       SpanFacing $$20 = $$6.getFacing();
/*  82 */       switch ($$20) {
/*     */         case UP:
/*  84 */           $$7 = $$11 = $$17;
/*  85 */           $$9 = $$12 = $$18 + 1.0F;
/*  86 */           $$8 = $$13 = $$19;
/*  87 */           $$10 = $$19;
/*  88 */           $$14 = $$19 + 1.0F;
/*     */           break;
/*     */         case DOWN:
/*  91 */           $$13 = $$19;
/*  92 */           $$14 = $$19 + 1.0F;
/*  93 */           $$7 = $$11 = $$17;
/*  94 */           $$9 = $$12 = $$18 + 1.0F;
/*  95 */           $$8 = $$19 + 1.0F;
/*  96 */           $$10 = $$19 + 1.0F;
/*     */           break;
/*     */         case LEFT:
/*  99 */           $$7 = $$11 = $$19;
/* 100 */           $$9 = $$19;
/* 101 */           $$12 = $$19 + 1.0F;
/* 102 */           $$8 = $$14 = $$17;
/* 103 */           $$10 = $$13 = $$18 + 1.0F;
/*     */           break;
/*     */         case RIGHT:
/* 106 */           $$11 = $$19;
/* 107 */           $$12 = $$19 + 1.0F;
/* 108 */           $$7 = $$19 + 1.0F;
/* 109 */           $$9 = $$19 + 1.0F;
/* 110 */           $$8 = $$14 = $$17;
/* 111 */           $$10 = $$13 = $$18 + 1.0F;
/*     */           break;
/*     */       } 
/*     */       
/* 115 */       $$7 *= $$15;
/* 116 */       $$9 *= $$15;
/* 117 */       $$8 *= $$16;
/* 118 */       $$10 *= $$16;
/*     */       
/* 120 */       $$8 = 16.0F - $$8;
/* 121 */       $$10 = 16.0F - $$10;
/*     */       
/* 123 */       $$11 *= $$15;
/* 124 */       $$12 *= $$15;
/* 125 */       $$13 *= $$16;
/* 126 */       $$14 *= $$16;
/*     */       
/* 128 */       Map<Direction, BlockElementFace> $$21 = Maps.newHashMap();
/* 129 */       $$21.put($$20.getDirection(), new BlockElementFace(null, $$2, $$1, new BlockFaceUV(new float[] { $$11, $$13, $$12, $$14 }, 0)));
/*     */       
/* 131 */       switch ($$20) {
/*     */         case UP:
/* 133 */           $$5.add(new BlockElement(new Vector3f($$7, $$8, 7.5F), new Vector3f($$9, $$8, 8.5F), $$21, null, true));
/*     */         
/*     */         case DOWN:
/* 136 */           $$5.add(new BlockElement(new Vector3f($$7, $$10, 7.5F), new Vector3f($$9, $$10, 8.5F), $$21, null, true));
/*     */         
/*     */         case LEFT:
/* 139 */           $$5.add(new BlockElement(new Vector3f($$7, $$8, 7.5F), new Vector3f($$7, $$10, 8.5F), $$21, null, true));
/*     */         
/*     */         case RIGHT:
/* 142 */           $$5.add(new BlockElement(new Vector3f($$9, $$8, 7.5F), new Vector3f($$9, $$10, 8.5F), $$21, null, true));
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 147 */     return $$5;
/*     */   }
/*     */   
/*     */   private List<Span> getSpans(SpriteContents $$0) {
/* 151 */     int $$1 = $$0.width();
/* 152 */     int $$2 = $$0.height();
/*     */     
/* 154 */     List<Span> $$3 = Lists.newArrayList();
/* 155 */     $$0.getUniqueFrames().forEach($$4 -> {
/*     */           for (int $$5 = 0; $$5 < $$0; $$5++) {
/*     */             for (int $$6 = 0; $$6 < $$1; $$6++) {
/*     */               boolean $$7 = !isTransparent($$2, $$4, $$6, $$5, $$1, $$0);
/*     */               
/*     */               checkTransition(SpanFacing.UP, $$3, $$2, $$4, $$6, $$5, $$1, $$0, $$7);
/*     */               checkTransition(SpanFacing.DOWN, $$3, $$2, $$4, $$6, $$5, $$1, $$0, $$7);
/*     */               checkTransition(SpanFacing.LEFT, $$3, $$2, $$4, $$6, $$5, $$1, $$0, $$7);
/*     */               checkTransition(SpanFacing.RIGHT, $$3, $$2, $$4, $$6, $$5, $$1, $$0, $$7);
/*     */             } 
/*     */           } 
/*     */         });
/* 167 */     return $$3;
/*     */   }
/*     */   
/*     */   private void checkTransition(SpanFacing $$0, List<Span> $$1, SpriteContents $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8) {
/* 171 */     boolean $$9 = (isTransparent($$2, $$3, $$4 + $$0.getXOffset(), $$5 + $$0.getYOffset(), $$6, $$7) && $$8);
/* 172 */     if ($$9) {
/* 173 */       createOrExpandSpan($$1, $$0, $$4, $$5);
/*     */     }
/*     */   }
/*     */   
/*     */   private void createOrExpandSpan(List<Span> $$0, SpanFacing $$1, int $$2, int $$3) {
/* 178 */     Span $$4 = null;
/* 179 */     for (Span $$5 : $$0) {
/* 180 */       if ($$5.getFacing() != $$1) {
/*     */         continue;
/*     */       }
/*     */       
/* 184 */       int $$6 = $$1.isHorizontal() ? $$3 : $$2;
/*     */       
/* 186 */       if ($$5.getAnchor() == $$6) {
/* 187 */         $$4 = $$5;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 192 */     int $$7 = $$1.isHorizontal() ? $$3 : $$2;
/* 193 */     int $$8 = $$1.isHorizontal() ? $$2 : $$3;
/* 194 */     if ($$4 == null) {
/* 195 */       $$0.add(new Span($$1, $$8, $$7));
/*     */     } else {
/* 197 */       $$4.expand($$8);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isTransparent(SpriteContents $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 202 */     if ($$2 < 0 || $$3 < 0 || $$2 >= $$4 || $$3 >= $$5) {
/* 203 */       return true;
/*     */     }
/* 205 */     return $$0.isTransparent($$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private enum SpanFacing
/*     */   {
/* 210 */     UP((String)Direction.UP, 0, -1),
/* 211 */     DOWN((String)Direction.DOWN, 0, 1),
/* 212 */     LEFT((String)Direction.EAST, -1, 0),
/* 213 */     RIGHT((String)Direction.WEST, 1, 0);
/*     */     
/*     */     private final Direction direction;
/*     */     private final int xOffset;
/*     */     private final int yOffset;
/*     */     
/*     */     SpanFacing(Direction $$0, int $$1, int $$2) {
/* 220 */       this.direction = $$0;
/* 221 */       this.xOffset = $$1;
/* 222 */       this.yOffset = $$2;
/*     */     }
/*     */     
/*     */     public Direction getDirection() {
/* 226 */       return this.direction;
/*     */     }
/*     */     
/*     */     public int getXOffset() {
/* 230 */       return this.xOffset;
/*     */     }
/*     */     
/*     */     public int getYOffset() {
/* 234 */       return this.yOffset;
/*     */     }
/*     */     
/*     */     boolean isHorizontal() {
/* 238 */       return (this == DOWN || this == UP);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Span
/*     */   {
/*     */     private final ItemModelGenerator.SpanFacing facing;
/*     */     private int min;
/*     */     private int max;
/*     */     private final int anchor;
/*     */     
/*     */     public Span(ItemModelGenerator.SpanFacing $$0, int $$1, int $$2) {
/* 250 */       this.facing = $$0;
/* 251 */       this.min = $$1;
/* 252 */       this.max = $$1;
/* 253 */       this.anchor = $$2;
/*     */     }
/*     */     
/*     */     public void expand(int $$0) {
/* 257 */       if ($$0 < this.min) {
/* 258 */         this.min = $$0;
/* 259 */       } else if ($$0 > this.max) {
/* 260 */         this.max = $$0;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ItemModelGenerator.SpanFacing getFacing() {
/* 265 */       return this.facing;
/*     */     }
/*     */     
/*     */     public int getMin() {
/* 269 */       return this.min;
/*     */     }
/*     */     
/*     */     public int getMax() {
/* 273 */       return this.max;
/*     */     }
/*     */     
/*     */     public int getAnchor() {
/* 277 */       return this.anchor;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\ItemModelGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */