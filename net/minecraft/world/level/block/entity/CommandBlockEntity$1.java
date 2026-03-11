/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import net.minecraft.commands.CommandSource;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.BaseCommandBlock;
/*     */ import net.minecraft.world.level.block.CommandBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
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
/*     */ 
/*     */ class null
/*     */   extends BaseCommandBlock
/*     */ {
/*     */   public void setCommand(String $$0) {
/* 136 */     super.setCommand($$0);
/* 137 */     CommandBlockEntity.this.setChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerLevel getLevel() {
/* 142 */     return (ServerLevel)CommandBlockEntity.this.level;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdated() {
/* 147 */     BlockState $$0 = CommandBlockEntity.this.level.getBlockState(CommandBlockEntity.this.worldPosition);
/* 148 */     getLevel().sendBlockUpdated(CommandBlockEntity.this.worldPosition, $$0, $$0, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getPosition() {
/* 153 */     return Vec3.atCenterOf((Vec3i)CommandBlockEntity.this.worldPosition);
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandSourceStack createCommandSourceStack() {
/* 158 */     Direction $$0 = (Direction)CommandBlockEntity.this.getBlockState().getValue((Property)CommandBlock.FACING);
/* 159 */     return new CommandSourceStack((CommandSource)this, Vec3.atCenterOf((Vec3i)CommandBlockEntity.this.worldPosition), new Vec2(0.0F, $$0.toYRot()), getLevel(), 2, getName().getString(), getName(), getLevel().getServer(), null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 164 */     return !CommandBlockEntity.this.isRemoved();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\CommandBlockEntity$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */