/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import net.minecraft.commands.arguments.blocks.BlockInput;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
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
/*     */ enum Mode
/*     */ {
/*     */   REPLACE, OUTLINE, HOLLOW, DESTROY;
/*     */   public final SetBlockCommand.Filter filter;
/*     */   
/*     */   static {
/* 130 */     REPLACE = new Mode("REPLACE", 0, ($$0, $$1, $$2, $$3) -> $$2);
/* 131 */     OUTLINE = new Mode("OUTLINE", 1, ($$0, $$1, $$2, $$3) -> 
/* 132 */         ($$1.getX() == $$0.minX() || $$1.getX() == $$0.maxX() || $$1.getY() == $$0.minY() || $$1.getY() == $$0.maxY() || $$1.getZ() == $$0.minZ() || $$1.getZ() == $$0.maxZ()) ? $$2 : null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     HOLLOW = new Mode("HOLLOW", 2, ($$0, $$1, $$2, $$3) -> 
/* 139 */         ($$1.getX() == $$0.minX() || $$1.getX() == $$0.maxX() || $$1.getY() == $$0.minY() || $$1.getY() == $$0.maxY() || $$1.getZ() == $$0.minZ() || $$1.getZ() == $$0.maxZ()) ? $$2 : FillCommand.HOLLOW_CORE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     DESTROY = new Mode("DESTROY", 3, ($$0, $$1, $$2, $$3) -> {
/*     */           $$3.destroyBlock($$1, true);
/*     */           return $$2;
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   Mode(SetBlockCommand.Filter $$0) {
/* 153 */     this.filter = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\FillCommand$Mode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */