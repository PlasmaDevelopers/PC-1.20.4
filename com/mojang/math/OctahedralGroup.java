/*     */ package com.mojang.math;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.FrontAndTop;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix3fc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum OctahedralGroup
/*     */   implements StringRepresentable
/*     */ {
/*  24 */   IDENTITY("identity", SymmetricGroup3.P123, false, false, false),
/*     */ 
/*     */   
/*  27 */   ROT_180_FACE_XY("rot_180_face_xy", SymmetricGroup3.P123, true, true, false),
/*  28 */   ROT_180_FACE_XZ("rot_180_face_xz", SymmetricGroup3.P123, true, false, true),
/*  29 */   ROT_180_FACE_YZ("rot_180_face_yz", SymmetricGroup3.P123, false, true, true),
/*     */ 
/*     */   
/*  32 */   ROT_120_NNN("rot_120_nnn", SymmetricGroup3.P231, false, false, false),
/*  33 */   ROT_120_NNP("rot_120_nnp", SymmetricGroup3.P312, true, false, true),
/*  34 */   ROT_120_NPN("rot_120_npn", SymmetricGroup3.P312, false, true, true),
/*  35 */   ROT_120_NPP("rot_120_npp", SymmetricGroup3.P231, true, false, true),
/*  36 */   ROT_120_PNN("rot_120_pnn", SymmetricGroup3.P312, true, true, false),
/*  37 */   ROT_120_PNP("rot_120_pnp", SymmetricGroup3.P231, true, true, false),
/*  38 */   ROT_120_PPN("rot_120_ppn", SymmetricGroup3.P231, false, true, true),
/*  39 */   ROT_120_PPP("rot_120_ppp", SymmetricGroup3.P312, false, false, false),
/*     */ 
/*     */   
/*  42 */   ROT_180_EDGE_XY_NEG("rot_180_edge_xy_neg", SymmetricGroup3.P213, true, true, true),
/*  43 */   ROT_180_EDGE_XY_POS("rot_180_edge_xy_pos", SymmetricGroup3.P213, false, false, true),
/*  44 */   ROT_180_EDGE_XZ_NEG("rot_180_edge_xz_neg", SymmetricGroup3.P321, true, true, true),
/*  45 */   ROT_180_EDGE_XZ_POS("rot_180_edge_xz_pos", SymmetricGroup3.P321, false, true, false),
/*  46 */   ROT_180_EDGE_YZ_NEG("rot_180_edge_yz_neg", SymmetricGroup3.P132, true, true, true),
/*  47 */   ROT_180_EDGE_YZ_POS("rot_180_edge_yz_pos", SymmetricGroup3.P132, true, false, false),
/*     */ 
/*     */   
/*  50 */   ROT_90_X_NEG("rot_90_x_neg", SymmetricGroup3.P132, false, false, true),
/*  51 */   ROT_90_X_POS("rot_90_x_pos", SymmetricGroup3.P132, false, true, false),
/*  52 */   ROT_90_Y_NEG("rot_90_y_neg", SymmetricGroup3.P321, true, false, false),
/*  53 */   ROT_90_Y_POS("rot_90_y_pos", SymmetricGroup3.P321, false, false, true),
/*  54 */   ROT_90_Z_NEG("rot_90_z_neg", SymmetricGroup3.P213, false, true, false),
/*  55 */   ROT_90_Z_POS("rot_90_z_pos", SymmetricGroup3.P213, true, false, false),
/*     */ 
/*     */   
/*  58 */   INVERSION("inversion", SymmetricGroup3.P123, true, true, true),
/*     */ 
/*     */   
/*  61 */   INVERT_X("invert_x", SymmetricGroup3.P123, true, false, false),
/*  62 */   INVERT_Y("invert_y", SymmetricGroup3.P123, false, true, false),
/*  63 */   INVERT_Z("invert_z", SymmetricGroup3.P123, false, false, true),
/*     */ 
/*     */   
/*  66 */   ROT_60_REF_NNN("rot_60_ref_nnn", SymmetricGroup3.P312, true, true, true),
/*  67 */   ROT_60_REF_NNP("rot_60_ref_nnp", SymmetricGroup3.P231, true, false, false),
/*  68 */   ROT_60_REF_NPN("rot_60_ref_npn", SymmetricGroup3.P231, false, false, true),
/*  69 */   ROT_60_REF_NPP("rot_60_ref_npp", SymmetricGroup3.P312, false, false, true),
/*  70 */   ROT_60_REF_PNN("rot_60_ref_pnn", SymmetricGroup3.P231, false, true, false),
/*  71 */   ROT_60_REF_PNP("rot_60_ref_pnp", SymmetricGroup3.P312, true, false, false),
/*  72 */   ROT_60_REF_PPN("rot_60_ref_ppn", SymmetricGroup3.P312, false, true, false),
/*  73 */   ROT_60_REF_PPP("rot_60_ref_ppp", SymmetricGroup3.P231, true, true, true),
/*     */ 
/*     */   
/*  76 */   SWAP_XY("swap_xy", SymmetricGroup3.P213, false, false, false),
/*  77 */   SWAP_YZ("swap_yz", SymmetricGroup3.P132, false, false, false),
/*  78 */   SWAP_XZ("swap_xz", SymmetricGroup3.P321, false, false, false),
/*     */ 
/*     */   
/*  81 */   SWAP_NEG_XY("swap_neg_xy", SymmetricGroup3.P213, true, true, false),
/*  82 */   SWAP_NEG_YZ("swap_neg_yz", SymmetricGroup3.P132, false, true, true),
/*  83 */   SWAP_NEG_XZ("swap_neg_xz", SymmetricGroup3.P321, true, false, true),
/*     */ 
/*     */   
/*  86 */   ROT_90_REF_X_NEG("rot_90_ref_x_neg", SymmetricGroup3.P132, true, false, true),
/*  87 */   ROT_90_REF_X_POS("rot_90_ref_x_pos", SymmetricGroup3.P132, true, true, false),
/*  88 */   ROT_90_REF_Y_NEG("rot_90_ref_y_neg", SymmetricGroup3.P321, true, true, false),
/*  89 */   ROT_90_REF_Y_POS("rot_90_ref_y_pos", SymmetricGroup3.P321, false, true, true),
/*  90 */   ROT_90_REF_Z_NEG("rot_90_ref_z_neg", SymmetricGroup3.P213, false, true, true),
/*  91 */   ROT_90_REF_Z_POS("rot_90_ref_z_pos", SymmetricGroup3.P213, true, false, true);
/*     */   
/*     */   private final Matrix3f transformation;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   @Nullable
/*     */   private Map<Direction, Direction> rotatedDirections;
/*     */   private final boolean invertX;
/*     */   private final boolean invertY;
/*     */   private final boolean invertZ;
/*     */   private final SymmetricGroup3 permutation;
/*     */   private static final OctahedralGroup[][] cayleyTable;
/*     */   private static final OctahedralGroup[] inverseTable;
/*     */   
/*     */   OctahedralGroup(String $$0, SymmetricGroup3 $$1, boolean $$2, boolean $$3, boolean $$4) {
/* 107 */     this.name = $$0;
/* 108 */     this.invertX = $$2;
/* 109 */     this.invertY = $$3;
/* 110 */     this.invertZ = $$4;
/* 111 */     this.permutation = $$1;
/*     */     
/* 113 */     this.transformation = (new Matrix3f()).scaling($$2 ? -1.0F : 1.0F, $$3 ? -1.0F : 1.0F, $$4 ? -1.0F : 1.0F);
/* 114 */     this.transformation.mul((Matrix3fc)$$1.transformation());
/*     */   }
/*     */   
/*     */   private BooleanList packInversions() {
/* 118 */     return (BooleanList)new BooleanArrayList(new boolean[] { this.invertX, this.invertY, this.invertZ });
/*     */   }
/*     */   static {
/* 121 */     cayleyTable = (OctahedralGroup[][])Util.make(new OctahedralGroup[(values()).length][(values()).length], $$0 -> {
/*     */           Map<Pair<SymmetricGroup3, BooleanList>, OctahedralGroup> $$1 = (Map<Pair<SymmetricGroup3, BooleanList>, OctahedralGroup>)Arrays.<OctahedralGroup>stream(values()).collect(Collectors.toMap((), ()));
/*     */           
/*     */           for (OctahedralGroup $$2 : values()) {
/*     */             for (OctahedralGroup $$3 : values()) {
/*     */               BooleanList $$4 = $$2.packInversions();
/*     */               
/*     */               BooleanList $$5 = $$3.packInversions();
/*     */               
/*     */               SymmetricGroup3 $$6 = $$3.permutation.compose($$2.permutation);
/*     */               
/*     */               BooleanArrayList $$7 = new BooleanArrayList(3);
/*     */               
/*     */               for (int $$8 = 0; $$8 < 3; $$8++) {
/*     */                 $$7.add($$4.getBoolean($$8) ^ $$5.getBoolean($$2.permutation.permutation($$8)));
/*     */               }
/*     */               
/*     */               $$0[$$2.ordinal()][$$3.ordinal()] = $$1.get(Pair.of($$6, $$7));
/*     */             } 
/*     */           } 
/*     */         });
/* 142 */     inverseTable = (OctahedralGroup[])Arrays.<OctahedralGroup>stream(values()).map($$0 -> (OctahedralGroup)Arrays.<OctahedralGroup>stream(values()).filter(()).findAny().get()).toArray($$0 -> new OctahedralGroup[$$0]);
/*     */   }
/*     */   public OctahedralGroup compose(OctahedralGroup $$0) {
/* 145 */     return cayleyTable[ordinal()][$$0.ordinal()];
/*     */   }
/*     */   
/*     */   public OctahedralGroup inverse() {
/* 149 */     return inverseTable[ordinal()];
/*     */   }
/*     */   
/*     */   public Matrix3f transformation() {
/* 153 */     return new Matrix3f((Matrix3fc)this.transformation);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 158 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 163 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction rotate(Direction $$0) {
/* 168 */     if (this.rotatedDirections == null) {
/* 169 */       this.rotatedDirections = Maps.newEnumMap(Direction.class);
/*     */       
/* 171 */       Direction.Axis[] $$1 = Direction.Axis.values();
/* 172 */       for (Direction $$2 : Direction.values()) {
/* 173 */         Direction.Axis $$3 = $$2.getAxis();
/* 174 */         Direction.AxisDirection $$4 = $$2.getAxisDirection();
/*     */         
/* 176 */         Direction.Axis $$5 = $$1[this.permutation.permutation($$3.ordinal())];
/*     */         
/* 178 */         Direction.AxisDirection $$6 = inverts($$5) ? $$4.opposite() : $$4;
/*     */         
/* 180 */         Direction $$7 = Direction.fromAxisAndDirection($$5, $$6);
/*     */         
/* 182 */         this.rotatedDirections.put($$2, $$7);
/*     */       } 
/*     */     } 
/* 185 */     return this.rotatedDirections.get($$0);
/*     */   }
/*     */   
/*     */   public boolean inverts(Direction.Axis $$0) {
/* 189 */     switch ($$0) {
/*     */       case X:
/* 191 */         return this.invertX;
/*     */       case Y:
/* 193 */         return this.invertY;
/*     */     } 
/*     */     
/* 196 */     return this.invertZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public FrontAndTop rotate(FrontAndTop $$0) {
/* 201 */     return FrontAndTop.fromFrontAndTop(rotate($$0.front()), rotate($$0.top()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\math\OctahedralGroup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */