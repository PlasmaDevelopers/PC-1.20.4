/*    */ package net.minecraft.commands.arguments.coordinates;
/*    */ 
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.world.phys.Vec2;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public interface Coordinates
/*    */ {
/*    */   Vec3 getPosition(CommandSourceStack paramCommandSourceStack);
/*    */   
/*    */   default BlockPos getBlockPos(CommandSourceStack $$0) {
/* 14 */     return BlockPos.containing((Position)getPosition($$0));
/*    */   }
/*    */   
/*    */   Vec2 getRotation(CommandSourceStack paramCommandSourceStack);
/*    */   
/*    */   boolean isXRelative();
/*    */   
/*    */   boolean isYRelative();
/*    */   
/*    */   boolean isZRelative();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\coordinates\Coordinates.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */