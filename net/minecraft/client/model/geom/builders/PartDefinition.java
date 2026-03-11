/*    */ package net.minecraft.client.model.geom.builders;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Maps;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ 
/*    */ public class PartDefinition {
/*    */   private final List<CubeDefinition> cubes;
/*    */   private final PartPose partPose;
/* 16 */   private final Map<String, PartDefinition> children = Maps.newHashMap();
/*    */   
/*    */   PartDefinition(List<CubeDefinition> $$0, PartPose $$1) {
/* 19 */     this.cubes = $$0;
/* 20 */     this.partPose = $$1;
/*    */   }
/*    */   
/*    */   public PartDefinition addOrReplaceChild(String $$0, CubeListBuilder $$1, PartPose $$2) {
/* 24 */     PartDefinition $$3 = new PartDefinition($$1.getCubes(), $$2);
/* 25 */     PartDefinition $$4 = this.children.put($$0, $$3);
/* 26 */     if ($$4 != null) {
/* 27 */       $$3.children.putAll($$4.children);
/*    */     }
/* 29 */     return $$3;
/*    */   }
/*    */   
/*    */   public ModelPart bake(int $$0, int $$1) {
/* 33 */     Object2ObjectArrayMap<String, ModelPart> $$2 = (Object2ObjectArrayMap<String, ModelPart>)this.children.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, $$2 -> ((PartDefinition)$$2.getValue()).bake($$0, $$1), ($$0, $$1) -> $$0, Object2ObjectArrayMap::new));
/* 34 */     List<ModelPart.Cube> $$3 = (List<ModelPart.Cube>)this.cubes.stream().map($$2 -> $$2.bake($$0, $$1)).collect(ImmutableList.toImmutableList());
/*    */     
/* 36 */     ModelPart $$4 = new ModelPart($$3, (Map)$$2);
/* 37 */     $$4.setInitialPose(this.partPose);
/* 38 */     $$4.loadPose(this.partPose);
/* 39 */     return $$4;
/*    */   }
/*    */   
/*    */   public PartDefinition getChild(String $$0) {
/* 43 */     return this.children.get($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\builders\PartDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */