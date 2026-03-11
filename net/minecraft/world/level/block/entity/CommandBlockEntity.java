/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import net.minecraft.commands.CommandSource;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.BaseCommandBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.CommandBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class CommandBlockEntity extends BlockEntity {
/*     */   private boolean powered;
/*     */   
/*     */   public CommandBlockEntity(BlockPos $$0, BlockState $$1) {
/*  23 */     super(BlockEntityType.COMMAND_BLOCK, $$0, $$1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     this.commandBlock = new BaseCommandBlock()
/*     */       {
/*     */         public void setCommand(String $$0) {
/* 136 */           super.setCommand($$0);
/* 137 */           CommandBlockEntity.this.setChanged();
/*     */         }
/*     */ 
/*     */         
/*     */         public ServerLevel getLevel() {
/* 142 */           return (ServerLevel)CommandBlockEntity.this.level;
/*     */         }
/*     */ 
/*     */         
/*     */         public void onUpdated() {
/* 147 */           BlockState $$0 = CommandBlockEntity.this.level.getBlockState(CommandBlockEntity.this.worldPosition);
/* 148 */           getLevel().sendBlockUpdated(CommandBlockEntity.this.worldPosition, $$0, $$0, 3);
/*     */         }
/*     */ 
/*     */         
/*     */         public Vec3 getPosition() {
/* 153 */           return Vec3.atCenterOf((Vec3i)CommandBlockEntity.this.worldPosition);
/*     */         }
/*     */ 
/*     */         
/*     */         public CommandSourceStack createCommandSourceStack() {
/* 158 */           Direction $$0 = (Direction)CommandBlockEntity.this.getBlockState().getValue((Property)CommandBlock.FACING);
/* 159 */           return new CommandSourceStack((CommandSource)this, Vec3.atCenterOf((Vec3i)CommandBlockEntity.this.worldPosition), new Vec2(0.0F, $$0.toYRot()), getLevel(), 2, getName().getString(), getName(), getLevel().getServer(), null);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isValid() {
/* 164 */           return !CommandBlockEntity.this.isRemoved();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private boolean auto;
/*     */   private boolean conditionMet;
/*     */   private final BaseCommandBlock commandBlock;
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*     */     super.saveAdditional($$0);
/*     */     this.commandBlock.save($$0);
/*     */     $$0.putBoolean("powered", isPowered());
/*     */     $$0.putBoolean("conditionMet", wasConditionMet());
/*     */     $$0.putBoolean("auto", isAutomatic());
/*     */   }
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*     */     super.load($$0);
/*     */     this.commandBlock.load($$0);
/*     */     this.powered = $$0.getBoolean("powered");
/*     */     this.conditionMet = $$0.getBoolean("conditionMet");
/*     */     setAutomatic($$0.getBoolean("auto"));
/*     */   }
/*     */   
/*     */   public boolean onlyOpCanSetNbt() {
/*     */     return true;
/*     */   }
/*     */   
/*     */   public BaseCommandBlock getCommandBlock() {
/*     */     return this.commandBlock;
/*     */   }
/*     */   
/*     */   public void setPowered(boolean $$0) {
/*     */     this.powered = $$0;
/*     */   }
/*     */   
/*     */   public boolean isPowered() {
/*     */     return this.powered;
/*     */   }
/*     */   
/*     */   public boolean isAutomatic() {
/*     */     return this.auto;
/*     */   }
/*     */   
/*     */   public void setAutomatic(boolean $$0) {
/*     */     boolean $$1 = this.auto;
/*     */     this.auto = $$0;
/*     */     if (!$$1 && $$0 && !this.powered && this.level != null && getMode() != Mode.SEQUENCE)
/*     */       scheduleTick(); 
/*     */   }
/*     */   
/*     */   public void onModeSwitch() {
/*     */     Mode $$0 = getMode();
/*     */     if ($$0 == Mode.AUTO && (this.powered || this.auto) && this.level != null)
/*     */       scheduleTick(); 
/*     */   }
/*     */   
/*     */   private void scheduleTick() {
/*     */     Block $$0 = getBlockState().getBlock();
/*     */     if ($$0 instanceof CommandBlock) {
/*     */       markConditionMet();
/*     */       this.level.scheduleTick(this.worldPosition, $$0, 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean wasConditionMet() {
/*     */     return this.conditionMet;
/*     */   }
/*     */   
/*     */   public boolean markConditionMet() {
/*     */     this.conditionMet = true;
/*     */     if (isConditional()) {
/*     */       BlockPos $$0 = this.worldPosition.relative(((Direction)this.level.getBlockState(this.worldPosition).getValue((Property)CommandBlock.FACING)).getOpposite());
/*     */       if (this.level.getBlockState($$0).getBlock() instanceof CommandBlock) {
/*     */         BlockEntity $$1 = this.level.getBlockEntity($$0);
/*     */         this.conditionMet = ($$1 instanceof CommandBlockEntity && ((CommandBlockEntity)$$1).getCommandBlock().getSuccessCount() > 0);
/*     */       } else {
/*     */         this.conditionMet = false;
/*     */       } 
/*     */     } 
/*     */     return this.conditionMet;
/*     */   }
/*     */   
/*     */   public Mode getMode() {
/*     */     BlockState $$0 = getBlockState();
/*     */     if ($$0.is(Blocks.COMMAND_BLOCK))
/*     */       return Mode.REDSTONE; 
/*     */     if ($$0.is(Blocks.REPEATING_COMMAND_BLOCK))
/*     */       return Mode.AUTO; 
/*     */     if ($$0.is(Blocks.CHAIN_COMMAND_BLOCK))
/*     */       return Mode.SEQUENCE; 
/*     */     return Mode.REDSTONE;
/*     */   }
/*     */   
/*     */   public boolean isConditional() {
/*     */     BlockState $$0 = this.level.getBlockState(getBlockPos());
/*     */     if ($$0.getBlock() instanceof CommandBlock)
/*     */       return ((Boolean)$$0.getValue((Property)CommandBlock.CONDITIONAL)).booleanValue(); 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public enum Mode {
/*     */     SEQUENCE, AUTO, REDSTONE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\CommandBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */