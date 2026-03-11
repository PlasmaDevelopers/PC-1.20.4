/*     */ package net.minecraft.world.level.block.entity;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.UnaryOperator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSource;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.network.FilteredText;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SignBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SignBlockEntity extends BlockEntity {
/*  36 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int MAX_TEXT_LINE_WIDTH = 90;
/*     */   private static final int TEXT_LINE_HEIGHT = 10;
/*     */   @Nullable
/*     */   private UUID playerWhoMayEdit;
/*     */   private SignText frontText;
/*     */   private SignText backText;
/*     */   private boolean isWaxed;
/*     */   
/*     */   public SignBlockEntity(BlockPos $$0, BlockState $$1) {
/*  47 */     this(BlockEntityType.SIGN, $$0, $$1);
/*     */   }
/*     */   
/*     */   public SignBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
/*  51 */     super($$0, $$1, $$2);
/*  52 */     this.frontText = createDefaultSignText();
/*  53 */     this.backText = createDefaultSignText();
/*     */   }
/*     */   
/*     */   protected SignText createDefaultSignText() {
/*  57 */     return new SignText();
/*     */   }
/*     */   
/*     */   public boolean isFacingFrontText(Player $$0) {
/*  61 */     Block block = getBlockState().getBlock(); if (block instanceof SignBlock) { SignBlock $$1 = (SignBlock)block;
/*  62 */       Vec3 $$2 = $$1.getSignHitboxCenterPosition(getBlockState());
/*  63 */       double $$3 = $$0.getX() - getBlockPos().getX() + $$2.x;
/*  64 */       double $$4 = $$0.getZ() - getBlockPos().getZ() + $$2.z;
/*     */       
/*  66 */       float $$5 = $$1.getYRotationDegrees(getBlockState());
/*  67 */       float $$6 = (float)(Mth.atan2($$4, $$3) * 57.2957763671875D) - 90.0F;
/*  68 */       return (Mth.degreesDifferenceAbs($$5, $$6) <= 90.0F); }
/*     */     
/*  70 */     return false;
/*     */   }
/*     */   
/*     */   public SignText getText(boolean $$0) {
/*  74 */     return $$0 ? this.frontText : this.backText;
/*     */   }
/*     */   
/*     */   public SignText getFrontText() {
/*  78 */     return this.frontText;
/*     */   }
/*     */   
/*     */   public SignText getBackText() {
/*  82 */     return this.backText;
/*     */   }
/*     */   
/*     */   public int getTextLineHeight() {
/*  86 */     return 10;
/*     */   }
/*     */   
/*     */   public int getMaxTextLineWidth() {
/*  90 */     return 90;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  95 */     super.saveAdditional($$0);
/*     */     
/*  97 */     Objects.requireNonNull(LOGGER); SignText.DIRECT_CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.frontText).resultOrPartial(LOGGER::error)
/*  98 */       .ifPresent($$1 -> $$0.put("front_text", $$1));
/*     */ 
/*     */     
/* 101 */     Objects.requireNonNull(LOGGER); SignText.DIRECT_CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.backText).resultOrPartial(LOGGER::error)
/* 102 */       .ifPresent($$1 -> $$0.put("back_text", $$1));
/* 103 */     $$0.putBoolean("is_waxed", this.isWaxed);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 108 */     super.load($$0);
/*     */     
/* 110 */     if ($$0.contains("front_text")) {
/*     */       
/* 112 */       Objects.requireNonNull(LOGGER); SignText.DIRECT_CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$0.getCompound("front_text")).resultOrPartial(LOGGER::error)
/* 113 */         .ifPresent($$0 -> this.frontText = loadLines($$0));
/*     */     } 
/*     */     
/* 116 */     if ($$0.contains("back_text")) {
/*     */       
/* 118 */       Objects.requireNonNull(LOGGER); SignText.DIRECT_CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$0.getCompound("back_text")).resultOrPartial(LOGGER::error)
/* 119 */         .ifPresent($$0 -> this.backText = loadLines($$0));
/*     */     } 
/* 121 */     this.isWaxed = $$0.getBoolean("is_waxed");
/*     */   }
/*     */   
/*     */   private SignText loadLines(SignText $$0) {
/* 125 */     for (int $$1 = 0; $$1 < 4; $$1++) {
/* 126 */       Component $$2 = loadLine($$0.getMessage($$1, false));
/* 127 */       Component $$3 = loadLine($$0.getMessage($$1, true));
/* 128 */       $$0 = $$0.setMessage($$1, $$2, $$3);
/*     */     } 
/* 130 */     return $$0;
/*     */   }
/*     */   
/*     */   private Component loadLine(Component $$0) {
/* 134 */     Level level = this.level; if (level instanceof ServerLevel) { ServerLevel $$1 = (ServerLevel)level;
/*     */       try {
/* 136 */         return (Component)ComponentUtils.updateForEntity(createCommandSourceStack((Player)null, (Level)$$1, this.worldPosition), $$0, null, 0);
/* 137 */       } catch (CommandSyntaxException commandSyntaxException) {} }
/*     */ 
/*     */     
/* 140 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSignText(Player $$0, boolean $$1, List<FilteredText> $$2) {
/* 145 */     if (isWaxed() || !$$0.getUUID().equals(getPlayerWhoMayEdit()) || this.level == null) {
/* 146 */       LOGGER.warn("Player {} just tried to change non-editable sign", $$0.getName().getString());
/*     */       
/*     */       return;
/*     */     } 
/* 150 */     updateText($$2 -> setMessages($$0, $$1, $$2), $$1);
/* 151 */     setAllowedPlayerEditor((UUID)null);
/* 152 */     this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
/*     */   }
/*     */   
/*     */   public boolean updateText(UnaryOperator<SignText> $$0, boolean $$1) {
/* 156 */     SignText $$2 = getText($$1);
/* 157 */     return setText($$0.apply($$2), $$1);
/*     */   }
/*     */   
/*     */   private SignText setMessages(Player $$0, List<FilteredText> $$1, SignText $$2) {
/* 161 */     for (int $$3 = 0; $$3 < $$1.size(); $$3++) {
/* 162 */       FilteredText $$4 = $$1.get($$3);
/* 163 */       Style $$5 = $$2.getMessage($$3, $$0.isTextFilteringEnabled()).getStyle();
/* 164 */       if ($$0.isTextFilteringEnabled()) {
/*     */         
/* 166 */         $$2 = $$2.setMessage($$3, (Component)Component.literal($$4.filteredOrEmpty()).setStyle($$5));
/*     */       } else {
/* 168 */         $$2 = $$2.setMessage($$3, (Component)Component.literal($$4.raw()).setStyle($$5), (Component)Component.literal($$4.filteredOrEmpty()).setStyle($$5));
/*     */       } 
/*     */     } 
/* 171 */     return $$2;
/*     */   }
/*     */   
/*     */   public boolean setText(SignText $$0, boolean $$1) {
/* 175 */     return $$1 ? setFrontText($$0) : setBackText($$0);
/*     */   }
/*     */   
/*     */   private boolean setBackText(SignText $$0) {
/* 179 */     if ($$0 != this.backText) {
/* 180 */       this.backText = $$0;
/* 181 */       markUpdated();
/* 182 */       return true;
/*     */     } 
/* 184 */     return false;
/*     */   }
/*     */   
/*     */   private boolean setFrontText(SignText $$0) {
/* 188 */     if ($$0 != this.frontText) {
/* 189 */       this.frontText = $$0;
/* 190 */       markUpdated();
/* 191 */       return true;
/*     */     } 
/* 193 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canExecuteClickCommands(boolean $$0, Player $$1) {
/* 197 */     return (isWaxed() && getText($$0).hasAnyClickCommands($$1));
/*     */   }
/*     */   
/*     */   public boolean executeClickCommandsIfPresent(Player $$0, Level $$1, BlockPos $$2, boolean $$3) {
/* 201 */     boolean $$4 = false;
/* 202 */     for (Component $$5 : getText($$3).getMessages($$0.isTextFilteringEnabled())) {
/* 203 */       Style $$6 = $$5.getStyle();
/* 204 */       ClickEvent $$7 = $$6.getClickEvent();
/* 205 */       if ($$7 != null && $$7.getAction() == ClickEvent.Action.RUN_COMMAND) {
/* 206 */         $$0.getServer().getCommands().performPrefixedCommand(createCommandSourceStack($$0, $$1, $$2), $$7.getValue());
/* 207 */         $$4 = true;
/*     */       } 
/*     */     } 
/* 210 */     return $$4;
/*     */   }
/*     */   
/*     */   private static CommandSourceStack createCommandSourceStack(@Nullable Player $$0, Level $$1, BlockPos $$2) {
/* 214 */     String $$3 = ($$0 == null) ? "Sign" : $$0.getName().getString();
/* 215 */     Component $$4 = ($$0 == null) ? (Component)Component.literal("Sign") : $$0.getDisplayName();
/* 216 */     return new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf((Vec3i)$$2), Vec2.ZERO, (ServerLevel)$$1, 2, $$3, $$4, $$1.getServer(), (Entity)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 221 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/* 226 */     return saveWithoutMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onlyOpCanSetNbt() {
/* 231 */     return true;
/*     */   }
/*     */   
/*     */   public void setAllowedPlayerEditor(@Nullable UUID $$0) {
/* 235 */     this.playerWhoMayEdit = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public UUID getPlayerWhoMayEdit() {
/* 240 */     return this.playerWhoMayEdit;
/*     */   }
/*     */   
/*     */   private void markUpdated() {
/* 244 */     setChanged();
/* 245 */     this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
/*     */   }
/*     */   
/*     */   public boolean isWaxed() {
/* 249 */     return this.isWaxed;
/*     */   }
/*     */   
/*     */   public boolean setWaxed(boolean $$0) {
/* 253 */     if (this.isWaxed != $$0) {
/* 254 */       this.isWaxed = $$0;
/* 255 */       markUpdated();
/* 256 */       return true;
/*     */     } 
/* 258 */     return false;
/*     */   }
/*     */   
/*     */   public boolean playerIsTooFarAwayToEdit(UUID $$0) {
/* 262 */     Player $$1 = this.level.getPlayerByUUID($$0);
/* 263 */     return ($$1 == null || $$1.distanceToSqr(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()) > 64.0D);
/*     */   }
/*     */   
/*     */   public static void tick(Level $$0, BlockPos $$1, BlockState $$2, SignBlockEntity $$3) {
/* 267 */     UUID $$4 = $$3.getPlayerWhoMayEdit();
/* 268 */     if ($$4 != null) {
/* 269 */       $$3.clearInvalidPlayerWhoMayEdit($$3, $$0, $$4);
/*     */     }
/*     */   }
/*     */   
/*     */   private void clearInvalidPlayerWhoMayEdit(SignBlockEntity $$0, Level $$1, UUID $$2) {
/* 274 */     if ($$0.playerIsTooFarAwayToEdit($$2)) {
/* 275 */       $$0.setAllowedPlayerEditor((UUID)null);
/*     */     }
/*     */   }
/*     */   
/*     */   public SoundEvent getSignInteractionFailedSoundEvent() {
/* 280 */     return SoundEvents.WAXED_SIGN_INTERACT_FAIL;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SignBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */