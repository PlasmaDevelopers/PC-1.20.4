/*    */ package net.minecraft.server.network.config;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.configuration.ClientboundFinishConfigurationPacket;
/*    */ import net.minecraft.server.network.ConfigurationTask;
/*    */ 
/*    */ public class JoinWorldTask
/*    */   implements ConfigurationTask {
/* 10 */   public static final ConfigurationTask.Type TYPE = new ConfigurationTask.Type("join_world");
/*    */ 
/*    */   
/*    */   public void start(Consumer<Packet<?>> $$0) {
/* 14 */     $$0.accept(new ClientboundFinishConfigurationPacket());
/*    */   }
/*    */ 
/*    */   
/*    */   public ConfigurationTask.Type type() {
/* 19 */     return TYPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\config\JoinWorldTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */