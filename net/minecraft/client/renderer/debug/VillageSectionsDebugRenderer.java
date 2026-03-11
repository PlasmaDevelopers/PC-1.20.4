/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VillageSectionsDebugRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer
/*    */ {
/*    */   private static final int MAX_RENDER_DIST_FOR_VILLAGE_SECTIONS = 60;
/* 17 */   private final Set<SectionPos> villageSections = Sets.newHashSet();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 24 */     this.villageSections.clear();
/*    */   }
/*    */   
/*    */   public void setVillageSection(SectionPos $$0) {
/* 28 */     this.villageSections.add($$0);
/*    */   }
/*    */   
/*    */   public void setNotVillageSection(SectionPos $$0) {
/* 32 */     this.villageSections.remove($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 37 */     BlockPos $$5 = BlockPos.containing($$2, $$3, $$4);
/*    */     
/* 39 */     this.villageSections.forEach($$3 -> {
/*    */           if ($$0.closerThan((Vec3i)$$3.center(), 60.0D)) {
/*    */             highlightVillageSection($$1, $$2, $$3);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   private static void highlightVillageSection(PoseStack $$0, MultiBufferSource $$1, SectionPos $$2) {
/* 47 */     DebugRenderer.renderFilledUnitCube($$0, $$1, $$2.center(), 0.2F, 1.0F, 0.2F, 0.15F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\VillageSectionsDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */