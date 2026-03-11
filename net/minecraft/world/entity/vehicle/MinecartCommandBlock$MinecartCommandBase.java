/*     */ package net.minecraft.world.entity.vehicle;
/*     */ 
/*     */ import net.minecraft.commands.CommandSource;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.BaseCommandBlock;
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
/*     */ public class MinecartCommandBase
/*     */   extends BaseCommandBlock
/*     */ {
/*     */   public ServerLevel getLevel() {
/* 119 */     return (ServerLevel)MinecartCommandBlock.this.level();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdated() {
/* 124 */     MinecartCommandBlock.this.getEntityData().set(MinecartCommandBlock.DATA_ID_COMMAND_NAME, getCommand());
/* 125 */     MinecartCommandBlock.this.getEntityData().set(MinecartCommandBlock.DATA_ID_LAST_OUTPUT, getLastOutput());
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getPosition() {
/* 130 */     return MinecartCommandBlock.this.position();
/*     */   }
/*     */   
/*     */   public MinecartCommandBlock getMinecart() {
/* 134 */     return MinecartCommandBlock.this;
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandSourceStack createCommandSourceStack() {
/* 139 */     return new CommandSourceStack((CommandSource)this, MinecartCommandBlock.this.position(), MinecartCommandBlock.this.getRotationVector(), getLevel(), 2, getName().getString(), MinecartCommandBlock.this.getDisplayName(), getLevel().getServer(), MinecartCommandBlock.this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 144 */     return !MinecartCommandBlock.this.isRemoved();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\MinecartCommandBlock$MinecartCommandBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */