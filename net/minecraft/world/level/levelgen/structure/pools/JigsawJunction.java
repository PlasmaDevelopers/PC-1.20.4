/*    */ package net.minecraft.world.level.levelgen.structure.pools;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class JigsawJunction
/*    */ {
/*    */   private final int sourceX;
/*    */   private final int sourceGroundY;
/*    */   
/*    */   public JigsawJunction(int $$0, int $$1, int $$2, int $$3, StructureTemplatePool.Projection $$4) {
/* 15 */     this.sourceX = $$0;
/* 16 */     this.sourceGroundY = $$1;
/* 17 */     this.sourceZ = $$2;
/* 18 */     this.deltaY = $$3;
/* 19 */     this.destProjection = $$4;
/*    */   }
/*    */   private final int sourceZ; private final int deltaY; private final StructureTemplatePool.Projection destProjection;
/*    */   public int getSourceX() {
/* 23 */     return this.sourceX;
/*    */   }
/*    */   
/*    */   public int getSourceGroundY() {
/* 27 */     return this.sourceGroundY;
/*    */   }
/*    */   
/*    */   public int getSourceZ() {
/* 31 */     return this.sourceZ;
/*    */   }
/*    */   
/*    */   public int getDeltaY() {
/* 35 */     return this.deltaY;
/*    */   }
/*    */   
/*    */   public StructureTemplatePool.Projection getDestProjection() {
/* 39 */     return this.destProjection;
/*    */   }
/*    */   
/*    */   public <T> Dynamic<T> serialize(DynamicOps<T> $$0) {
/* 43 */     ImmutableMap.Builder<T, T> $$1 = ImmutableMap.builder();
/* 44 */     $$1
/* 45 */       .put($$0.createString("source_x"), $$0.createInt(this.sourceX))
/* 46 */       .put($$0.createString("source_ground_y"), $$0.createInt(this.sourceGroundY))
/* 47 */       .put($$0.createString("source_z"), $$0.createInt(this.sourceZ))
/* 48 */       .put($$0.createString("delta_y"), $$0.createInt(this.deltaY))
/* 49 */       .put($$0.createString("dest_proj"), $$0.createString(this.destProjection.getName()));
/*    */     
/* 51 */     return new Dynamic($$0, $$0.createMap((Map)$$1.build()));
/*    */   }
/*    */   
/*    */   public static <T> JigsawJunction deserialize(Dynamic<T> $$0) {
/* 55 */     return new JigsawJunction($$0
/* 56 */         .get("source_x").asInt(0), $$0
/* 57 */         .get("source_ground_y").asInt(0), $$0
/* 58 */         .get("source_z").asInt(0), $$0
/* 59 */         .get("delta_y").asInt(0), 
/* 60 */         StructureTemplatePool.Projection.byName($$0.get("dest_proj").asString("")));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 66 */     if (this == $$0) {
/* 67 */       return true;
/*    */     }
/* 69 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 70 */       return false;
/*    */     }
/*    */     
/* 73 */     JigsawJunction $$1 = (JigsawJunction)$$0;
/*    */     
/* 75 */     if (this.sourceX != $$1.sourceX) {
/* 76 */       return false;
/*    */     }
/* 78 */     if (this.sourceZ != $$1.sourceZ) {
/* 79 */       return false;
/*    */     }
/* 81 */     if (this.deltaY != $$1.deltaY) {
/* 82 */       return false;
/*    */     }
/* 84 */     return (this.destProjection == $$1.destProjection);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     int $$0 = this.sourceX;
/* 90 */     $$0 = 31 * $$0 + this.sourceGroundY;
/* 91 */     $$0 = 31 * $$0 + this.sourceZ;
/* 92 */     $$0 = 31 * $$0 + this.deltaY;
/* 93 */     $$0 = 31 * $$0 + this.destProjection.hashCode();
/* 94 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return "JigsawJunction{sourceX=" + this.sourceX + ", sourceGroundY=" + this.sourceGroundY + ", sourceZ=" + this.sourceZ + ", deltaY=" + this.deltaY + ", destProjection=" + this.destProjection + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\JigsawJunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */