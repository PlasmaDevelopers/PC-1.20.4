/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ 
/*     */ public class DefaultVertexFormat
/*     */ {
/*   7 */   public static final VertexFormatElement ELEMENT_POSITION = new VertexFormatElement(0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.POSITION, 3);
/*   8 */   public static final VertexFormatElement ELEMENT_COLOR = new VertexFormatElement(0, VertexFormatElement.Type.UBYTE, VertexFormatElement.Usage.COLOR, 4);
/*   9 */   public static final VertexFormatElement ELEMENT_UV0 = new VertexFormatElement(0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.UV, 2);
/*  10 */   public static final VertexFormatElement ELEMENT_UV1 = new VertexFormatElement(1, VertexFormatElement.Type.SHORT, VertexFormatElement.Usage.UV, 2);
/*  11 */   public static final VertexFormatElement ELEMENT_UV2 = new VertexFormatElement(2, VertexFormatElement.Type.SHORT, VertexFormatElement.Usage.UV, 2);
/*  12 */   public static final VertexFormatElement ELEMENT_NORMAL = new VertexFormatElement(0, VertexFormatElement.Type.BYTE, VertexFormatElement.Usage.NORMAL, 3);
/*  13 */   public static final VertexFormatElement ELEMENT_PADDING = new VertexFormatElement(0, VertexFormatElement.Type.BYTE, VertexFormatElement.Usage.PADDING, 1);
/*     */   
/*  15 */   public static final VertexFormatElement ELEMENT_UV = ELEMENT_UV0;
/*     */   
/*  17 */   public static final VertexFormat BLIT_SCREEN = new VertexFormat(ImmutableMap.builder()
/*  18 */       .put("Position", ELEMENT_POSITION)
/*  19 */       .put("UV", ELEMENT_UV)
/*  20 */       .put("Color", ELEMENT_COLOR)
/*  21 */       .build());
/*     */   
/*  23 */   public static final VertexFormat BLOCK = new VertexFormat(ImmutableMap.builder()
/*  24 */       .put("Position", ELEMENT_POSITION)
/*  25 */       .put("Color", ELEMENT_COLOR)
/*  26 */       .put("UV0", ELEMENT_UV0)
/*  27 */       .put("UV2", ELEMENT_UV2)
/*  28 */       .put("Normal", ELEMENT_NORMAL)
/*  29 */       .put("Padding", ELEMENT_PADDING)
/*  30 */       .build());
/*     */   
/*  32 */   public static final VertexFormat NEW_ENTITY = new VertexFormat(ImmutableMap.builder()
/*  33 */       .put("Position", ELEMENT_POSITION)
/*  34 */       .put("Color", ELEMENT_COLOR)
/*  35 */       .put("UV0", ELEMENT_UV0)
/*  36 */       .put("UV1", ELEMENT_UV1)
/*  37 */       .put("UV2", ELEMENT_UV2)
/*  38 */       .put("Normal", ELEMENT_NORMAL)
/*  39 */       .put("Padding", ELEMENT_PADDING)
/*  40 */       .build());
/*     */   
/*  42 */   public static final VertexFormat PARTICLE = new VertexFormat(ImmutableMap.builder()
/*  43 */       .put("Position", ELEMENT_POSITION)
/*  44 */       .put("UV0", ELEMENT_UV0)
/*  45 */       .put("Color", ELEMENT_COLOR)
/*  46 */       .put("UV2", ELEMENT_UV2)
/*  47 */       .build());
/*     */   
/*  49 */   public static final VertexFormat POSITION = new VertexFormat(ImmutableMap.builder()
/*  50 */       .put("Position", ELEMENT_POSITION)
/*  51 */       .build());
/*     */   
/*  53 */   public static final VertexFormat POSITION_COLOR = new VertexFormat(ImmutableMap.builder()
/*  54 */       .put("Position", ELEMENT_POSITION)
/*  55 */       .put("Color", ELEMENT_COLOR)
/*  56 */       .build());
/*     */   
/*  58 */   public static final VertexFormat POSITION_COLOR_NORMAL = new VertexFormat(ImmutableMap.builder()
/*  59 */       .put("Position", ELEMENT_POSITION)
/*  60 */       .put("Color", ELEMENT_COLOR)
/*  61 */       .put("Normal", ELEMENT_NORMAL)
/*  62 */       .put("Padding", ELEMENT_PADDING)
/*  63 */       .build());
/*     */   
/*  65 */   public static final VertexFormat POSITION_COLOR_LIGHTMAP = new VertexFormat(ImmutableMap.builder()
/*  66 */       .put("Position", ELEMENT_POSITION)
/*  67 */       .put("Color", ELEMENT_COLOR)
/*  68 */       .put("UV2", ELEMENT_UV2)
/*  69 */       .build());
/*     */   
/*  71 */   public static final VertexFormat POSITION_TEX = new VertexFormat(ImmutableMap.builder()
/*  72 */       .put("Position", ELEMENT_POSITION)
/*  73 */       .put("UV0", ELEMENT_UV0)
/*  74 */       .build());
/*     */   
/*  76 */   public static final VertexFormat POSITION_COLOR_TEX = new VertexFormat(ImmutableMap.builder()
/*  77 */       .put("Position", ELEMENT_POSITION)
/*  78 */       .put("Color", ELEMENT_COLOR)
/*  79 */       .put("UV0", ELEMENT_UV0)
/*  80 */       .build());
/*     */   
/*  82 */   public static final VertexFormat POSITION_TEX_COLOR = new VertexFormat(ImmutableMap.builder()
/*  83 */       .put("Position", ELEMENT_POSITION)
/*  84 */       .put("UV0", ELEMENT_UV0)
/*  85 */       .put("Color", ELEMENT_COLOR)
/*  86 */       .build());
/*     */   
/*  88 */   public static final VertexFormat POSITION_COLOR_TEX_LIGHTMAP = new VertexFormat(ImmutableMap.builder()
/*  89 */       .put("Position", ELEMENT_POSITION)
/*  90 */       .put("Color", ELEMENT_COLOR)
/*  91 */       .put("UV0", ELEMENT_UV0)
/*  92 */       .put("UV2", ELEMENT_UV2)
/*  93 */       .build());
/*     */   
/*  95 */   public static final VertexFormat POSITION_TEX_LIGHTMAP_COLOR = new VertexFormat(ImmutableMap.builder()
/*  96 */       .put("Position", ELEMENT_POSITION)
/*  97 */       .put("UV0", ELEMENT_UV0)
/*  98 */       .put("UV2", ELEMENT_UV2)
/*  99 */       .put("Color", ELEMENT_COLOR)
/* 100 */       .build());
/*     */   
/* 102 */   public static final VertexFormat POSITION_TEX_COLOR_NORMAL = new VertexFormat(ImmutableMap.builder()
/* 103 */       .put("Position", ELEMENT_POSITION)
/* 104 */       .put("UV0", ELEMENT_UV0)
/* 105 */       .put("Color", ELEMENT_COLOR)
/* 106 */       .put("Normal", ELEMENT_NORMAL)
/* 107 */       .put("Padding", ELEMENT_PADDING)
/* 108 */       .build());
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\DefaultVertexFormat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */