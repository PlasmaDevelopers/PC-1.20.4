/*    */ package net.minecraft.server.network.config;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.network.ConfigurationTask;
/*    */ 
/*    */ public class ServerResourcePackConfigurationTask
/*    */   implements ConfigurationTask {
/* 11 */   public static final ConfigurationTask.Type TYPE = new ConfigurationTask.Type("server_resource_pack");
/*    */   
/*    */   private final MinecraftServer.ServerResourcePackInfo info;
/*    */   
/*    */   public ServerResourcePackConfigurationTask(MinecraftServer.ServerResourcePackInfo $$0) {
/* 16 */     this.info = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start(Consumer<Packet<?>> $$0) {
/* 21 */     $$0.accept(new ClientboundResourcePackPushPacket(this.info.id(), this.info.url(), this.info.hash(), this.info.isRequired(), this.info.prompt()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ConfigurationTask.Type type() {
/* 26 */     return TYPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\config\ServerResourcePackConfigurationTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */