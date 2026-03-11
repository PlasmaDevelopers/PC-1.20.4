/*    */ package net.minecraft.world.level.levelgen.structure.pools;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.GravityProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Projection
/*    */   implements StringRepresentable
/*    */ {
/*    */   public static final StringRepresentable.EnumCodec<Projection> CODEC;
/* 44 */   TERRAIN_MATCHING("terrain_matching", 
/*    */     
/* 46 */     ImmutableList.of(new GravityProcessor(Heightmap.Types.WORLD_SURFACE_WG, -1))),
/*    */   
/* 48 */   RIGID("rigid", 
/*    */     
/* 50 */     ImmutableList.of());
/*    */   
/*    */   static {
/* 53 */     CODEC = StringRepresentable.fromEnum(Projection::values);
/*    */   }
/*    */   
/*    */   private final String name;
/*    */   
/*    */   Projection(String $$0, ImmutableList<StructureProcessor> $$1) {
/* 59 */     this.name = $$0;
/* 60 */     this.processors = $$1;
/*    */   }
/*    */   private final ImmutableList<StructureProcessor> processors;
/*    */   public String getName() {
/* 64 */     return this.name;
/*    */   }
/*    */   
/*    */   public static Projection byName(String $$0) {
/* 68 */     return (Projection)CODEC.byName($$0);
/*    */   }
/*    */   
/*    */   public ImmutableList<StructureProcessor> getProcessors() {
/* 72 */     return this.processors;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 77 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\StructureTemplatePool$Projection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */