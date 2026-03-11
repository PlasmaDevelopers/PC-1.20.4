/*    */ package net.minecraft.client.model.geom.builders;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.EnumSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.core.Direction;
/*    */ 
/*    */ public class CubeListBuilder
/*    */ {
/* 12 */   private static final Set<Direction> ALL_VISIBLE = EnumSet.allOf(Direction.class);
/* 13 */   private final List<CubeDefinition> cubes = Lists.newArrayList();
/*    */   
/*    */   private int xTexOffs;
/*    */   private int yTexOffs;
/*    */   private boolean mirror;
/*    */   
/*    */   public CubeListBuilder texOffs(int $$0, int $$1) {
/* 20 */     this.xTexOffs = $$0;
/* 21 */     this.yTexOffs = $$1;
/* 22 */     return this;
/*    */   }
/*    */   
/*    */   public CubeListBuilder mirror() {
/* 26 */     return mirror(true);
/*    */   }
/*    */   
/*    */   public CubeListBuilder mirror(boolean $$0) {
/* 30 */     this.mirror = $$0;
/* 31 */     return this;
/*    */   }
/*    */   
/*    */   public CubeListBuilder addBox(String $$0, float $$1, float $$2, float $$3, int $$4, int $$5, int $$6, CubeDeformation $$7, int $$8, int $$9) {
/* 35 */     texOffs($$8, $$9);
/* 36 */     this.cubes.add(new CubeDefinition($$0, this.xTexOffs, this.yTexOffs, $$1, $$2, $$3, $$4, $$5, $$6, $$7, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
/* 37 */     return this;
/*    */   }
/*    */   
/*    */   public CubeListBuilder addBox(String $$0, float $$1, float $$2, float $$3, int $$4, int $$5, int $$6, int $$7, int $$8) {
/* 41 */     texOffs($$7, $$8);
/* 42 */     this.cubes.add(new CubeDefinition($$0, this.xTexOffs, this.yTexOffs, $$1, $$2, $$3, $$4, $$5, $$6, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
/* 43 */     return this;
/*    */   }
/*    */   
/*    */   public CubeListBuilder addBox(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 47 */     this.cubes.add(new CubeDefinition(null, this.xTexOffs, this.yTexOffs, $$0, $$1, $$2, $$3, $$4, $$5, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
/* 48 */     return this;
/*    */   }
/*    */   
/*    */   public CubeListBuilder addBox(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, Set<Direction> $$6) {
/* 52 */     this.cubes.add(new CubeDefinition(null, this.xTexOffs, this.yTexOffs, $$0, $$1, $$2, $$3, $$4, $$5, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F, $$6));
/* 53 */     return this;
/*    */   }
/*    */   
/*    */   public CubeListBuilder addBox(String $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6) {
/* 57 */     this.cubes.add(new CubeDefinition($$0, this.xTexOffs, this.yTexOffs, $$1, $$2, $$3, $$4, $$5, $$6, CubeDeformation.NONE, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public CubeListBuilder addBox(String $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, CubeDeformation $$7) {
/* 62 */     this.cubes.add(new CubeDefinition($$0, this.xTexOffs, this.yTexOffs, $$1, $$2, $$3, $$4, $$5, $$6, $$7, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public CubeListBuilder addBox(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, boolean $$6) {
/* 67 */     this.cubes.add(new CubeDefinition(null, this.xTexOffs, this.yTexOffs, $$0, $$1, $$2, $$3, $$4, $$5, CubeDeformation.NONE, $$6, 1.0F, 1.0F, ALL_VISIBLE));
/* 68 */     return this;
/*    */   }
/*    */   
/*    */   public CubeListBuilder addBox(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, CubeDeformation $$6, float $$7, float $$8) {
/* 72 */     this.cubes.add(new CubeDefinition(null, this.xTexOffs, this.yTexOffs, $$0, $$1, $$2, $$3, $$4, $$5, $$6, this.mirror, $$7, $$8, ALL_VISIBLE));
/* 73 */     return this;
/*    */   }
/*    */   
/*    */   public CubeListBuilder addBox(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, CubeDeformation $$6) {
/* 77 */     this.cubes.add(new CubeDefinition(null, this.xTexOffs, this.yTexOffs, $$0, $$1, $$2, $$3, $$4, $$5, $$6, this.mirror, 1.0F, 1.0F, ALL_VISIBLE));
/* 78 */     return this;
/*    */   }
/*    */   
/*    */   public List<CubeDefinition> getCubes() {
/* 82 */     return (List<CubeDefinition>)ImmutableList.copyOf(this.cubes);
/*    */   }
/*    */   
/*    */   public static CubeListBuilder create() {
/* 86 */     return new CubeListBuilder();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\builders\CubeListBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */