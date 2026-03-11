/*    */ package net.minecraft.server.rcon;
/*    */ 
/*    */ import net.minecraft.commands.CommandSource;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.phys.Vec2;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class RconConsoleSource implements CommandSource {
/*    */   private static final String RCON = "Rcon";
/* 14 */   private static final Component RCON_COMPONENT = (Component)Component.literal("Rcon");
/* 15 */   private final StringBuffer buffer = new StringBuffer();
/*    */   private final MinecraftServer server;
/*    */   
/*    */   public RconConsoleSource(MinecraftServer $$0) {
/* 19 */     this.server = $$0;
/*    */   }
/*    */   
/*    */   public void prepareForCommand() {
/* 23 */     this.buffer.setLength(0);
/*    */   }
/*    */   
/*    */   public String getCommandResponse() {
/* 27 */     return this.buffer.toString();
/*    */   }
/*    */   
/*    */   public CommandSourceStack createCommandSourceStack() {
/* 31 */     ServerLevel $$0 = this.server.overworld();
/* 32 */     return new CommandSourceStack(this, Vec3.atLowerCornerOf((Vec3i)$$0.getSharedSpawnPos()), Vec2.ZERO, $$0, 4, "Rcon", RCON_COMPONENT, this.server, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendSystemMessage(Component $$0) {
/* 37 */     this.buffer.append($$0.getString());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptsSuccess() {
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptsFailure() {
/* 47 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldInformAdmins() {
/* 52 */     return this.server.shouldRconBroadcast();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\rcon\RconConsoleSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */